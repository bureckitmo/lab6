package models;

import exceptions.EnumParseException;

import java.io.Serializable;
import java.util.*;

public enum Color implements Serializable {
    BLACK,
    WHITE,
    BROWN;

    public static Map<Color, Integer> getMap = new HashMap<Color, Integer>() {
        {
            put(BLACK, 1);
            put(WHITE, 2);
            put(BROWN, 3);
        }
    };

    public static Color parse(String str) {

        switch (str.toUpperCase()) {

            case "BLACK":
                return BLACK;
            case "WHITE":
                return WHITE;
            case "BROWN":
                return BROWN;
            default:
                throw new EnumParseException(str);
        }
    }
}