package models;

import javax.script.ScriptEngine;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * Модель фильма
 */
public class Movie implements Comparable, Serializable {
    private Integer id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer oscarsCount; //Значение поля должно быть больше 0
    private MovieGenre genre; //Поле не может быть null
    private MpaaRating mpaaRating; //Поле не может быть null
    private Person operator; //Поле не может быть null

    public Movie(){}

    public Movie(Integer id, String name, Coordinates coordinates, LocalDate creationDate, Integer oscarsCount, MovieGenre genre, MpaaRating mpaaRating, Person operator){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.operator = operator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(Integer oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public Person getOperator() {
        return operator;
    }

    public void setOperator(Person operator) {
        this.operator = operator;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\n");
        sb.append("\t").append("id: ").append(this.id).append("\n");
        sb.append("\t").append("name: ").append(this.name).append("\n");
        sb.append("\t").append("coordinates: ").append(this.coordinates).append("\n");
        sb.append("\t").append("oscarsCount: ").append(this.oscarsCount).append("\n");
        sb.append("\t").append("movieGenre: ").append(this.genre).append("\n");
        sb.append("\t").append("mpaaRating: ").append(this.mpaaRating).append("\n");
        sb.append("\t").append("person: ").append(this.operator).append("\n");

        sb.append("}").append("\n");
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (!(o instanceof Movie)) {
            throw new ClassCastException();
        }
        Movie fo = (Movie) o;
        if (this.getOscarsCount().equals(fo.getOscarsCount())) {
            return 0;
        } else {
            return this.getOscarsCount() < fo.getOscarsCount() ? -1 : 1;
        }
    }
}
