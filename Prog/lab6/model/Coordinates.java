package model;

import util.Validation;

import java.io.Serializable;


public final class Coordinates implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long x;
    private Double y;


    public Coordinates(Long x, Double y) {
        setX(x);
        setY(y);
    }


    public Long getX() {
        return x;
    }


    public void setX(Long x) {
        this.x = Validation.requireLongGreaterThan(x, -740, "coordinates.x должен быть больше -740 и не null");
    }


    public Double getY() {
        return y;
    }


    public void setY(Double y) {
        this.y = Validation.requireNotNull(y, "coordinates.y не может быть null");
    }

    @Override
    public String toString() {
        return "Coordinates{x=" + x + ", y=" + y + '}';
    }
}
