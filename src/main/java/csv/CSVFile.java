package csv;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVFile {

    /**
     * array of fields' names
     */
    protected String[] header;

    /**
     * List of arrays of values' names
     */
    protected List<String[]> body;

    /**
     * path to a needed .csv file
     */
    private final String path;

    /**
     * type of delimiter to a .csv file
     */
    private final Delimiter delimiter;

    /**
     * Constructor
     *
     * @param path      path to a needed .csv file
     * @throws          IOException if problem with input or output with a file
     */
    public CSVFile(String path) throws IOException {

        this.path = path;
        this.delimiter = Delimiter.COMMA;
        this.setFields(path, Delimiter.COMMA);
    }

    /**
     * Constructor
     *
     * @param path      path to a needed .csv file
     * @param delimiter type of delimiter to a .csv file
     * @throws          IOException if problem with input or output with a file
     */
    public CSVFile(String path, Delimiter delimiter) throws IOException {

        this.path = path;
        this.delimiter = delimiter;
        this.setFields(path, delimiter);
    }

    /**
     * The {@code setFields()} method installs value of fields
     *
     * @param path      path to a needed .csv file
     * @param delimiter type of delimiter to a .csv file
     * @throws          IOException if problem with input or output with a file
     */
    private void setFields(String path, Delimiter delimiter) throws IOException {

        List<String> file = Reader.readAsList(path);

        String[] line = null;
        List<String[]> lines = new ArrayList<>();

        if (file.size() != 0) {
            line = cutLine(file.get(0), delimiter);
                           file.remove(0);
        }

        for (String s : file) lines.add(cutLine(s, delimiter));

        this.header = line;
        this.body = lines;

    }

    /**
     * The {@code cutLine()} method cuts line to a words
     *
     * @param line          object of {@code String} class
     * @param delimiter     type of delimiter to a .csv file
     * @return              array of {@code String} class objects
     */
    private String[] cutLine(String line, Delimiter delimiter) {

        String[] columns = line.split(Delimiter.getDelimiter(delimiter));

        for (int i = 0; i < columns.length; i ++)
            columns[i] = columns[i].trim();

        return columns;
    }

    /**
     * Getter
     *
     * @return      path to a needed .csv file
     */
    public String getPath() { return path; }

    /**
     * Getter
     *
     * @return      type of delimiter to a .csv file
     */
    public Delimiter getDelimiter() { return delimiter; }

    /**
     * The {@code Reader} static class reads file as a List
     */
    static class Reader {

        /**
         * The {@code readAsList()} reads file as a list
         *
         * @param path      path to a needed .csv file
         * @return          List of Strings
         * @throws          IOException if problem with input or output with a file
         */
        private static List<String> readAsList(String path) throws IOException {

            List<String> list = new ArrayList<>();

            Scanner stream = new Scanner(Paths.get(path));

            String line;
            while (stream.hasNextLine()) {

                if(!(line = stream.nextLine().trim()).equals(""))
                    list.add(line);
            }

            return list;
        }
    }
}