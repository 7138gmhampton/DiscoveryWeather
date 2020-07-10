package com.discover.weather;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Cardinal
{
    private static final TreeMap<Integer,String> direction_ranges = new TreeMap<>();
    static {
        direction_ranges.put(0,"N");
        direction_ranges.put(22,"NE");
        direction_ranges.put(67,"E");
        direction_ranges.put(113,"SE");
        direction_ranges.put(158,"S");
        direction_ranges.put(202,"SW");
        direction_ranges.put(247,"W");
        direction_ranges.put(293,"NW");
        direction_ranges.put(338,"N");
    }
    private static final HashMap<String,Integer> exact_directions = new HashMap<>();
    static {
        exact_directions.put("N", 0);
        exact_directions.put("NE", 45);
        exact_directions.put("E", 90);
        exact_directions.put("SE", 135);
        exact_directions.put("S", 180);
        exact_directions.put("SW", 225);
        exact_directions.put("W", 270);
        exact_directions.put("NW", 315);
    }

    @SuppressWarnings("ConstantConditions")
    static String getCardinal(Integer absolute)
    {
        Map.Entry<Integer,String> entry = direction_ranges.floorEntry(absolute);

        return entry .getValue();
    }

    @SuppressWarnings("ConstantConditions")
    static int getAbsoluteDirection(String cardinal)
    {
        return exact_directions.get(cardinal);
    }
}
