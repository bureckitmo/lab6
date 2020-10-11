package managers;

import exceptions.EnumParseException;
import exceptions.ExecutionException;
import exceptions.InvalidValueException;
import models.*;
import utils.NumUtil;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Консольный менеджер
 */
public class ConsoleManager {

    private Scanner scanner;
    private boolean isScript;
    private Writer writer;
    private Reader reader;

    public ConsoleManager(Reader reader, Writer writer, boolean isScript)
    {
        this.reader = reader;
        this.writer = writer;
        scanner = new Scanner(reader);
        this.isScript = isScript;
    }

    public void writeln(String message) {
        write(message + "\n");
    }

    public void write(String message) {
        try {
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean getIsScript(){ return isScript; }

    public String read() {
        return scanner.nextLine();
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * парсит boolean значение из сотроки
     * @param s
     * @return
     */
    public static boolean parseBoolean(String s) {
        if ("true".equals(s.toLowerCase())) {
            return true;
        } else if ("false".equals(s.toLowerCase())) {
            return false;
        }else{
            throw new NumberFormatException("Wrong format");
        }
    }


    /**
     * выводит сообщение с вводом от пользователя
     * @param message
     * @param canNull
     * @return
     */
    public String readWithMessage(String message, boolean canNull) {
        String output = "";

        do {
            if (output == null) {
                writeln("Try again");
            }

            if(!isScript) {
                writeln(message);
            }

            output = scanner.nextLine();
            output = output.isEmpty() ? null : output;
        }while (!isScript && !canNull && output == null);
        if(isScript && output == null)
            throw new InvalidValueException("Not-null input");

        return output;
    }

    /**
     * выводит сообщение с вводом от пользователя
     * @param message
     * @param canNull
     * @return
     */
    public String readWithMessageLength(String message, boolean canNull, int minLength) {
        String output = "";

        do {
            output = readWithMessage(message, canNull);
        }while (output.length() < minLength );

        return output;
    }

    public Number readWithParse(String msg, boolean canNull){
        Number out = null;

        while (true){
            try{
                String num = readWithMessage(msg, canNull);
                if(num == null && canNull) break;

                NumberFormat format = NumberFormat.getInstance();
                ParsePosition pos = new ParsePosition(0);
                out = format.parse(num, pos);
                if (pos.getIndex() != num.length() || pos.getErrorIndex() != -1) throw new NumberFormatException("Wrong type");

                break;
            }catch (NumberFormatException ex){
                writeln(ex.getMessage());
            }
        }

        return out;
    }

    public Number readWithParseMinMax(String msg, BigDecimal min, BigDecimal max, boolean canNull){
        Number out = null;

        do {
            out = readWithParse(msg, canNull);
            if(out == null && canNull)
                break;
        }while (!NumUtil.isInRange(out, min, max));

        return out;
    }




    /**
     * получает введенные данные объектом
     * @return
     */
    public Movie getMovie(){
        boolean capital = false;

        String name = readWithMessage("Enter movie name: ", false);
        Coordinates coord = getCoord();

        Number noscars = readWithParseMinMax("Enter oscars count (Integer, [0; INT_MAX]): ", new BigDecimal(0), NumUtil.INTEGER_MAX, true);
        Integer oscars = noscars == null ? null : noscars.intValue();

        MovieGenre genre = getGenre();
        MpaaRating rating = getRating();
        Person person = getPerson();


        return new Movie(-1, name, coord, LocalDate.now(), oscars, genre, rating, person);
    }


    /**
     * получает координаты
     * @return
     */
    public Coordinates getCoord(){
        long x = readWithParseMinMax("Enter X (Long, [-772; LONG_MAX]): ", new BigDecimal(-772), NumUtil.LONG_MAX, false).longValue();
        Number ny = readWithParseMinMax("Enter Y (Integer, [INT_MIN; 969]): ", NumUtil.INTEGER_MIN, new BigDecimal(969), true);
        Integer y = ny == null ? null : ny.intValue();

        return new Coordinates(x, y);
    }

    /**
     * получает тип жанра
     * @return
     */
    public MovieGenre getGenre() {
        MovieGenre out = null;
        StringBuilder sb = new StringBuilder();
        for (MovieGenre value : MovieGenre.values()) {
            sb.append("\n\t").append(value.toString());
        }

        while (true) {
            try {
                String inp = readWithMessage("Enter 'Genre'(" + sb.toString() + "\n):", false);
                if (inp == null) {
                    return null;
                }
                out = MovieGenre.parse(inp);
                break;
            } catch (ClassCastException | EnumParseException ex) {
                writeln(ex.getMessage());
                if(isScript) throw new ExecutionException("Cast error");
            }
        }

        return out;
    }

    /**
     * получает тип рейтинга
     * @return
     */
    public MpaaRating getRating() {
        MpaaRating out = null;
        StringBuilder sb = new StringBuilder();
        for (MpaaRating value : MpaaRating.values()) {
            sb.append("\n\t").append(value.toString());
        }

        while (true) {
            try {
                String inp = readWithMessage("Enter 'MpaaRating'(" + sb.toString() + "\n): ", false);
                if (inp == null) {
                    return null;
                }
                out = MpaaRating.parse(inp);
                break;
            } catch (ClassCastException | EnumParseException ex) {
                writeln(ex.getMessage());
                if(isScript) throw new ExecutionException("Cast error");
            }
        }

        return out;
    }


    /**
     * получает персону
     * @return
     */
    public Person getPerson(){
        String name = readWithMessage("Enter person name: ", false);
        String passID = readWithMessageLength("Enter PassportID (minlength=5): ", false, 5).toUpperCase();
        Color color = getColor();
        Location location = getLocation();

        return new Person(name, passID, color, location);
    }

    /**
     * получает цвет
     * @return
     */
    public Color getColor() {
        Color out = null;
        StringBuilder sb = new StringBuilder();
        for (Color value : Color.values()) {
            sb.append("\n\t").append(value.toString());
        }

        while (true) {
            try {
                String inp = readWithMessage("Enter 'Color'(" + sb.toString() + "\n): ", false);
                if (inp == null) {
                    return null;
                }
                out = Color.parse(inp);
                break;
            } catch (ClassCastException | EnumParseException ex) {
                writeln(ex.getMessage());
                if(isScript) throw new ExecutionException("Cast error");
            }
        }

        return out;
    }


    /**
     * получает позицию
     * @return
     */
    public Location getLocation(){
        Number nx = readWithParseMinMax("Enter X (Long, [LONG_MIN; LONG_MAX]): ", NumUtil.LONG_MIN, NumUtil.LONG_MAX, true);
        Long x = nx == null ? null : nx.longValue();

        Number ny = readWithParseMinMax("Enter Y (Double, [DOUBLE_MIN; DOUBLE_MAX]): ", NumUtil.DOUBLE_MIN, NumUtil.DOUBLE_MAX, true);
        Double y = ny == null ? null : ny.doubleValue();

        Number nz = readWithParseMinMax("Enter Z (Long, [LONG_MIN; LONG_MAX]): ", NumUtil.LONG_MIN, NumUtil.LONG_MAX, true);
        Long z = nz == null ? null : nz.longValue();

        return new Location(x, y, z);
    }
}
