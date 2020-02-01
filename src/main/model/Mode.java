package model;

import java.util.HashMap;

public class Mode extends HashMap<String, Mode.ModeAction> {
    private static Mode modes;

    public interface ModeAction {
        int run(int argument, CPU cpu);
    }

    public static ModeAction getImplicit = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    public static ModeAction getAccumulator = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    public static ModeAction getImmediate = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    public static ModeAction getZeroPage = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    public static ModeAction getAbsolute = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    public static ModeAction getRelative = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    public static ModeAction getIndirect = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    public static ModeAction getZeroPageIndexedX = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    public static ModeAction getZeroPageIndexedY = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    public static ModeAction getAbsoluteIndexedX = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    public static ModeAction getAbsoluteIndexedY = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    public static ModeAction getIndexedIndirect = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    public static ModeAction getIndirectIndexed = (int argument, CPU cpu) -> {
        return 0; // stub
    };

    static {
        modes = new Mode();
        modes.put("IMPLICIT",            getImplicit);
        modes.put("ACCUMULATOR",         getAccumulator);
        modes.put("IMMEDIATE",           getImmediate);
        modes.put("ZERO_PAGE",           getZeroPage);
        modes.put("ABSOLUTE",            getAbsolute);
        modes.put("RELATIVE",            getRelative);
        modes.put("INDIRECT",            getIndirect);
        modes.put("ZERO_PAGE_INDEXED_X", getZeroPageIndexedX);
        modes.put("ZERO_PAGE_INDEXED_Y", getZeroPageIndexedY);
        modes.put("ABSOLUTE_INDEXED_X",  getAbsoluteIndexedX);
        modes.put("ABSOLUTE_INDEXED_Y",  getAbsoluteIndexedY);
        modes.put("INDEXED_INDIRECT",    getIndexedIndirect);
        modes.put("INDIRECT_INDEXED",    getIndirectIndexed);
    }
}






