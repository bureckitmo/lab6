package models;

import exceptions.EnumParseException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum MpaaRating implements Serializable {
    G,
    PG,
    PG_13,
    R,
    NC_17;

    public static Map<MpaaRating, Integer> getMap = new HashMap<MpaaRating, Integer>() {
        {
            put(G, 1);
            put(PG, 2);
            put(PG_13, 3);
            put(R, 4);
            put(NC_17, 5);
        }
    };

    public static MpaaRating parse(String str) {

        switch (str.toUpperCase()) {

            case "G":
                return G;
            case "PG":
                return PG;
            case "PG_13":
                return PG_13;
            case "R":
                return R;
            case "NC_17":
                return NC_17;
            default:
                throw new EnumParseException(str);
        }
    }
}
