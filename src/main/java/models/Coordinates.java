package models;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Long x; //Значение поля должно быть больше -772, Поле не может быть null
    private Integer y; //Максимальное значение поля: 969

    public Coordinates(){}

    public Coordinates(Long x, Integer y){
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\n");
        sb.append("\t\t").append("x: ").append(this.x).append("\n");
        sb.append("\t\t").append("y: ").append(this.y).append("\n");

        sb.append("\t}").append("\n");
        return sb.toString();
    }
}