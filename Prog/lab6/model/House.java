package model;

import util.Validation;

import java.io.Serializable;


public final class House implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private long year;
    private long numberOfFloors;
    private long numberOfFlatsOnFloor;
    private long numberOfLifts;


    public House(String name, long year, long numberOfFloors, long numberOfFlatsOnFloor, long numberOfLifts) {
        setName(name);
        setYear(year);
        setNumberOfFloors(numberOfFloors);
        setNumberOfFlatsOnFloor(numberOfFlatsOnFloor);
        setNumberOfLifts(numberOfLifts);
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public long getYear() {
        return year;
    }


    public void setYear(long year) {
        this.year = Validation.requireGreaterThan(year, 0, "house.year должен быть больше 0");
    }


    public long getNumberOfFloors() {
        return numberOfFloors;
    }


    public void setNumberOfFloors(long numberOfFloors) {
        this.numberOfFloors = Validation.requireGreaterThan(
                numberOfFloors,
                0,
                "house.numberOfFloors должен быть больше 0"
        );
    }


    public long getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }


    public void setNumberOfFlatsOnFloor(long numberOfFlatsOnFloor) {
        this.numberOfFlatsOnFloor = Validation.requireGreaterThan(
                numberOfFlatsOnFloor,
                0,
                "house.numberOfFlatsOnFloor должен быть больше 0"
        );
    }


    public long getNumberOfLifts() {
        return numberOfLifts;
    }


    public void setNumberOfLifts(long numberOfLifts) {
        this.numberOfLifts = Validation.requireGreaterThan(
                numberOfLifts,
                0,
                "house.numberOfLifts должен быть больше 0"
        );
    }

    @Override
    public String toString() {
        return "House{"
                + "name=" + (name == null ? "null" : "'" + name + "'")
                + ", year=" + year
                + ", numberOfFloors=" + numberOfFloors
                + ", numberOfFlatsOnFloor=" + numberOfFlatsOnFloor
                + ", numberOfLifts=" + numberOfLifts
                + '}';
    }
}
