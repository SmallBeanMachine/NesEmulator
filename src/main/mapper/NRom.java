package mapper;

import model.Address;

import java.util.Scanner;

// Class NROM:
//     NROM models an NROM Mapper. Can read through an NROM NES file, both NROM-128 and NROM-256.
//     See for more details: https://wiki.nesdev.com/w/index.php/NROM

public class NRom extends Mapper {
    public  static final int PRG_ROM_128_SIZE      = Integer.parseInt("4000", 16);

    public  static final int INITIAL_PRG_RAM_STATE = Integer.parseInt("00",   16);
    public  static final int PRG_RAM_SIZE          = Integer.parseInt("2000", 16);

    private static final int ID                    = 000;

    private boolean isNRom128;

    public NRom() {
        super(ID);
    }

    // EFFECTS: initialzies header, trainer, chrRom, and prgRom as empty arrays, and sets the NROM type to NROM-256.
    // fills the prgRam with the initial state.
    public NRom(Address[] prgRom, Address[] chrRom) {
        super(prgRom, chrRom, ID);

        prgRam  = new Address[PRG_RAM_SIZE];
        for (int i = 0; i < PRG_RAM_SIZE; i++) {
            prgRam[i] = new Address(INITIAL_PRG_RAM_STATE);
        }
        isNRom128 = prgRom.length <= PRG_ROM_128_SIZE;
        enable();
    }

    // REQUIRES: address is in between 0x6000 and 0xFFFF, inclusive.
    // EFFECTS: returns the value of the memory at the given address.
    // see the table below for a detailed description of what is stored at which address.
    @Override
    public Address readMemoryCpu(int address) {
        if (!getEnabled()) {
            return new Address(0);
        }

        // https://wiki.nesdev.com/w/index.php/NROM
        // ADDRESS RANGE | SIZE  | DEVICE
        // $6000 - $7FFF | $2000 | PRG RAM, mirrored as necessary to fill entire 8 KiB window
        // $8000 - $BFFF | $4000 | First 16 KB of ROM
        // $C000 - $FFFF | $4000 | Last 16 KB of ROM (for NROM-256). Else, this is a mirror of the first 16 KB.
        if (address < Integer.parseInt("6000", 16)) {            // Out of bounds
            return new Address(0, address);
        } else if (address <= Integer.parseInt("7FFF", 16)) {    // PRG RAM
            return prgRam[(address - Integer.parseInt("6000", 16))];
        } else {                                                           // PRG ROM
            if (isNRom128) {
                return prgRom[(address - Integer.parseInt("8000", 16)) % PRG_ROM_128_SIZE];
            } else {
                return prgRom[address - Integer.parseInt("8000", 16)];
            }
        }
    }

    // REQUIRES: address is in between 0x6000 and 0xFFFF, inclusive.
    // EFFECTS: returns the value of the memory at the given address.
    // see the table below for a detailed description of what is stored at which address.
    @Override
    public Address readMemoryPpu(int address) {
        return chrRom[address];
    }

    // REQUIRES: address is in between 0x6000 and 0xFFFF, inclusive.
    // MODIFIES: prgRam, prgRom
    // EFFECTS: check the table below for a detailed explanation of what is affected and how.
    @Override
    public void writeMemory(int address, int rawValue) {
        if (!getEnabled()) {
            return;
        }

        // https://wiki.nesdev.com/w/index.php/NROM
        // ADDRESS RANGE | SIZE  | DEVICE
        // $6000 - $7FFF | $2000 | PRG RAM, mirrored as necessary to fill entire 8 KiB window
        // $8000 - $BFFF | $4000 | First 16 KB of ROM
        // $C000 - $FFFF | $4000 | Last 16 KB of ROM (for NROM-256). Else, this is a mirror of the first 16 KB.

        if        (address < Integer.parseInt("6000", 16)) {    // Out of Bounds
            throw new ArrayIndexOutOfBoundsException("Address out of bounds! NROM only supports addresses >= 0x6000");
        } else if (address <= Integer.parseInt("7FFF", 16)) {   // PRG RAM
            prgRam[address - Integer.parseInt("6000", 16)].setValue(rawValue);
        } else {                                                         // PRG ROM. mirrored for NROM-128.
            throw new ArrayIndexOutOfBoundsException("Cannot write to a Read-Only Address!");
        }
    }

    @Override
    public String serialize(String delimiter) {
        StringBuilder output = new StringBuilder();
        output.append(prgRom.length + delimiter);
        for (Address address : prgRom) {
            output.append(address.serialize(delimiter));
        }
        output.append(prgRam.length + delimiter);
        for (Address address : prgRam) {
            output.append(address.serialize(delimiter));
        }
        output.append(chrRom.length + delimiter);
        for (Address address : chrRom) {
            output.append(address.serialize(delimiter));
        }
        output.append(isNRom128 ? 1 : 0 + delimiter);
        return output.toString();
    }

    @Override
    public void deserialize(Scanner scanner) {
        prgRom = new Address[Integer.parseInt(scanner.next())];
        for (int i = 0; i < prgRom.length; i++) {
            prgRom[i] = new Address(0, 0);
            prgRom[i].deserialize(scanner);
        }
        prgRam = new Address[Integer.parseInt(scanner.next())];
        for (int i = 0; i < prgRam.length; i++) {
            prgRam[i] = new Address(0, 0);
            prgRam[i].deserialize(scanner);
        }
        chrRom = new Address[Integer.parseInt(scanner.next())];
        for (int i = 0; i < chrRom.length; i++) {
            chrRom[i] = new Address(0, 0);
            chrRom[i].deserialize(scanner);
        }
        isNRom128 = scanner.next().equals("1");
        enable();
    }
}
