package ui;

import model.NES;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NameTableViewer extends Window implements KeyListener {
    private static final int FPS                = 10;
    private static final int NUMBER_OF_PALETTES = 8;

    private static final int KEY_CHANGE_PALETTE = KeyEvent.VK_SPACE;

    private int currentPalette;

    public NameTableViewer(NES nes) {
        super(nes, 1, 1, 32 * 8 * 2, 32 * 8 * 2, FPS,"NameTables Viewer");

        currentPalette = 0;
        addKeyListener(this);

        pack();
        setVisible(true);
    }

    @Override
    public void repaint() {
        nes.renderNameTables(getPixels(), currentPalette);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KEY_CHANGE_PALETTE) {
            incrementPalette();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void incrementPalette() {
        currentPalette = (currentPalette + 1) % NUMBER_OF_PALETTES;
    }
}
