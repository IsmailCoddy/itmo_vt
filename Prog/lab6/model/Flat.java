package model;

import util.Validation;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;


public final class Flat implements Comparable<Flat>, Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private Integer area;
    private Integer numberOfRooms;
    private boolean centralHeating;
    private Furnish furnish;
    private View view;
    private House house;


    public Flat(
            long id,
            String name,
            Coordinates coordinates,
            ZonedDateTime creationDate,
            Integer area,
            Integer numberOfRooms,
            boolean centralHeating,
            Furnish furnish,
            View view,
            House house
    ) {
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        setCreationDate(creationDate);
        setArea(area);
        setNumberOfRooms(numberOfRooms);
        setCentralHeating(centralHeating);
        setFurnish(furnish);
        setView(view);
        setHouse(house);
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = Validation.requireGreaterThan(id, 0, "id должен быть больше 0");
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = Validation.requireNotBlank(name, "name не может быть null или пустым");
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }


    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = Validation.requireNotNull(coordinates, "coordinates не может быть null");
    }


    public ZonedDateTime getCreationDate() {
        return creationDate;
    }


    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = Validation.requireNotNull(creationDate, "creationDate не может быть null");
    }


    public Integer getArea() {
        return area;
    }


    public void setArea(Integer area) {
        this.area = Validation.requireIntegerGreaterThan(area, 0, "area должен быть больше 0 и не null");
    }


    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }


    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = Validation.requireNullableIntegerGreaterThan(
                numberOfRooms,
                0,
                "numberOfRooms должен быть больше 0 или null"
        );
    }


    public boolean isCentralHeating() {
        return centralHeating;
    }


    public void setCentralHeating(boolean centralHeating) {
        this.centralHeating = centralHeating;
    }


    public Furnish getFurnish() {
        return furnish;
    }


    public void setFurnish(Furnish furnish) {
        this.furnish = Validation.requireNotNull(furnish, "furnish не может быть null");
    }


    public View getView() {
        return view;
    }


    public void setView(View view) {
        this.view = Validation.requireNotNull(view, "view не может быть null");
    }


    public House getHouse() {
        return house;
    }


    public void setHouse(House house) {
        this.house = Validation.requireNotNull(house, "house не может быть null");
    }

    @Override
    public int compareTo(Flat other) {
        return Long.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Flat)) {
            return false;
        }
        Flat flat = (Flat) object;
        return id == flat.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Flat{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", coordinates=" + coordinates
                + ", creationDate=" + creationDate
                + ", area=" + area
                + ", numberOfRooms=" + numberOfRooms
                + ", centralHeating=" + centralHeating
                + ", furnish=" + furnish
                + ", view=" + view
                + ", house=" + house
                + '}';
    }
}
