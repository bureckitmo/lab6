package models;

import java.io.Serializable;

public class Person implements Comparable, Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportID; //Строка не может быть пустой, Значение этого поля должно быть уникальным, Длина строки должна быть не меньше 5, Поле может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле может быть null

    public Person(){}

    public Person(String name, String passportID, Color hairColor, Location location){
        this.name = name;
        this.passportID = passportID;
        this.hairColor = hairColor;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\n");
        sb.append("\t\t").append("name: ").append(this.name).append("\n");
        sb.append("\t\t").append("passportID: ").append(this.passportID).append("\n");
        sb.append("\t\t").append("hairColor: ").append(this.hairColor).append("\n");
        sb.append("\t\t").append("location: ").append(this.location).append("\n");

        sb.append("\t}").append("\n");
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {

        Person person = (Person) o;
        return this.name.compareTo(person.getName());
    }
}