package csv;


import exceptions.EmptyFileException;
import managers.ConsoleManager;
import models.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class CSVManager {

    /**
     * Needed .csv file
     */
    private CSVFile file;

    /**
     * Constructor
     *
     * @param file      .csv file
     */
    public CSVManager(CSVFile file) { this.file = file; }

    /**
     * The {@code getLaboratories()} method gets collections
     *
     * @return      collection of objects {@code Laboratory} class
     * @throws EmptyFileException if file id empty
     */
    public ArrayList<Movie> getMovies() throws EmptyFileException, ParseException {

        ArrayList<Movie> movies = new ArrayList<Movie>();

        if (file.header == null) throw new EmptyFileException();
        if (file.body == null) return movies;

        for (String[] body : file.body)
            movies.add(this.getObject(file.header, body));

        return movies;
    }

    private Movie getObject(String[] header, String[] body) throws ParseException {

        Movie movie = new Movie();
        Coordinates coordinates = new Coordinates();
        Person person = new Person();
        Location location = new Location();

        for (int i = 0, j = 0; i < header.length && j < body.length; i ++, j ++) {

            if (body[j].equals("")) continue;

            switch (header[i]) {

                case "id":                        movie.setId(Integer.parseInt(body[j]));
                    break;
                case "name":                      movie.setName(String.valueOf(body[j]));
                    break;
                case "coordinates_x":             coordinates.setX(Long.parseLong(body[j]));
                    break;
                case "coordinates_y":             coordinates.setY(Integer.parseInt(body[j]));
                    break;
                case "creationDate":              movie.setCreationDate(LocalDate.parse(body[j]));
                    break;
                case "oscarsCount":               movie.setOscarsCount(Integer.parseInt(body[j]));
                    break;
                case "genre":                     movie.setGenre(MovieGenre.parse(body[j]));
                    break;
                case "rating":                    movie.setMpaaRating(MpaaRating.parse(body[j]));
                    break;
                case "person_name":               person.setName(String.valueOf(body[j]));
                    break;
                case "person_passID":             person.setPassportID(String.valueOf(body[j]));
                    break;
                case "person_hairColor":          person.setHairColor(Color.parse(body[j]));
                    break;
                case "location_x":                location.setX(Long.parseLong(body[j]));
                    break;
                case "location_y":                location.setY(Double.parseDouble(body[j]));
                    break;
                case "location_z":                location.setZ(Long.parseLong(body[j]));
                    break;
            }
        }

        person.setLocation(location);
        movie.setCoordinates(coordinates);
        movie.setOperator(person);

        return movie;
    }

    public void saveList(ArrayList<Movie> movies, ConsoleManager consoleManager){
        String header = "id,name,coordinates_x,coordinates_y,creationDate,oscarsCount,genre,rating,person_name,person_passID,person_hairColor,location_x,location_y,location_z";
        String path = this.getFile().getPath();
        String delimiter = Delimiter.getDelimiter(this.getFile().getDelimiter());

        StringBuilder builder = new StringBuilder(header).append("\n");
        for (Movie movie : movies) {
            builder.append(movie.getId()).append(delimiter);
            builder.append(movie.getName()).append(delimiter);
            builder.append(movie.getCoordinates().getX()).append(delimiter);

            if (movie.getCoordinates().getY() == null) builder.append(delimiter);
            else builder.append(movie.getCoordinates().getY()).append(delimiter);

            builder.append(movie.getCreationDate()).append(delimiter);

            if (movie.getOscarsCount() == null) builder.append(delimiter);
            else builder.append(movie.getOscarsCount()).append(delimiter);


            if (movie.getGenre() == null) builder.append(delimiter);
            else builder.append(movie.getGenre()).append(delimiter);

            if (movie.getMpaaRating() == null) builder.append(delimiter);
            else builder.append(movie.getMpaaRating()).append(delimiter);

            builder.append(movie.getOperator().getName()).append(delimiter);
            builder.append(movie.getOperator().getPassportID()).append(delimiter);

            if (movie.getOperator().getHairColor() == null) builder.append(delimiter);
            else builder.append(movie.getOperator().getHairColor()).append(delimiter);

            if (movie.getOperator().getLocation().getX() == null) builder.append(delimiter);
            else builder.append(movie.getOperator().getLocation().getX()).append(delimiter);

            if (movie.getOperator().getLocation().getY() == null) builder.append(delimiter);
            else builder.append(movie.getOperator().getLocation().getY()).append(delimiter);

            if (movie.getOperator().getLocation().getZ() == null) builder.append(delimiter);
            else builder.append(movie.getOperator().getLocation().getZ()).append(delimiter);

            builder.append("\n");
        }

        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
            stream.write(builder.toString().getBytes());
            stream.close();
            consoleManager.writeln("Collection successfully saved to the file.");
        } catch (IOException e) {
            consoleManager.writeln("Here a problem with saving collection to the file!");
        }

    }

    public CSVFile getFile() { return file; }
}
