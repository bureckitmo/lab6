package models;

import java.io.Serializable;

public class Location implements Serializable {
    private Long x;
    private Double y;
    private Long z;

    public Location(){}

    public Location(Long x, Double y, Long z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Long getZ() {
        return z;
    }

    public void setZ(Long z) {
        this.z = z;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\n");
        sb.append("\t\t\t").append("x: ").append(this.x).append("\n");
        sb.append("\t\t\t").append("y: ").append(this.y).append("\n");
        sb.append("\t\t\t").append("z: ").append(this.z).append("\n");

        sb.append("\t\t}");
        return sb.toString();
    }
}