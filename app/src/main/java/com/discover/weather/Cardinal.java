package com.discover.weather;

import java.util.HashMap;
import java.util.TreeMap;

public class Cardinal
{
    private static final TreeMap<Integer,String> RANGES = new TreeMap<>();
    static {
        RANGES.put(0,"N");
        RANGES.put(22,"NE");
        RANGES.put(67,"E");
        RANGES.put(113,"SE");
        RANGES.put(158,"S");
        RANGES.put(202,"SW");
        RANGES.put(247,"W");
        RANGES.put(293,"NW");
        RANGES.put(338,"N");
    }
    private static final HashMap<String,Integer> EXACT_DIRECTIONS = new HashMap<>();
    static {
        EXACT_DIRECTIONS.put("N", 0);
        EXACT_DIRECTIONS.put("NE", 45);
        EXACT_DIRECTIONS.put("E", 90);
        EXACT_DIRECTIONS.put("SE", 135);
        EXACT_DIRECTIONS.put("S", 180);
        EXACT_DIRECTIONS.put("SW", 225);
        EXACT_DIRECTIONS.put("W", 270);
        EXACT_DIRECTIONS.put("NW", 315);
    }

    @SuppressWarnings("ConstantConditions")
    static String getCardinal(Integer absolute) { return RANGES.floorEntry(absolute).getValue(); }

    @SuppressWarnings("ConstantConditions")
    static int getAbsoluteDirection(String cardinal) { return EXACT_DIRECTIONS.get(cardinal); }
}
