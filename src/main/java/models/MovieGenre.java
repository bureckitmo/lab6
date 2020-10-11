package models;

import exceptions.EnumParseException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum MovieGenre implements Serializable {
    WESTERN,
    DRAMA,
    TRAGEDY,
    FANTASY,
    SCIENCE_FICTION;

    public static Map<MovieGenre, Integer> getMap = new HashMap<MovieGenre, Integer>() {
        {
            put(WESTERN, 1);
            put(DRAMA, 2);
            put(TRAGEDY, 3);
            put(FANTASY, 4);
            put(SCIENCE_FICTION, 5);
        }
    };

    public static MovieGenre parse(String str) {

        switch (str.toUpperCase()) {
            case "WESTERN":
                return WESTERN;
            case "DRAMA":
                return DRAMA;
            case "TRAGEDY":
                return TRAGEDY;
            case "FANTASY":
                return FANTASY;
            case "SCIENCE_FICTION":
                return SCIENCE_FICTION;
            default:
                throw new EnumParseException(str);
        }
    }
}