package common.dto;

import model.Coordinates;
import model.Flat;
import model.Furnish;
import model.House;
import model.View;
import util.Validation;

import java.io.Serializable;
import java.time.ZonedDateTime;


public class FlatData implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final Coordinates coordinates;
    private final Integer area;
    private final Integer numberOfRooms;
    private final boolean centralHeating;
    private final Furnish furnish;
    private final View view;
    private final House house;


    public FlatData(
            String name,
            Coordinates coordinates,
            Integer area,
            Integer numberOfRooms,
            boolean centralHeating,
            Furnish furnish,
            View view,
            House house
    ) {
        this.name = Validation.requireNotBlank(name, "name не может быть null или пустым");
        this.coordinates = Validation.requireNotNull(coordinates, "coordinates не может быть null");
        this.area = Validation.requireIntegerGreaterThan(area, 0, "area должен быть больше 0 и не null");
        this.numberOfRooms = Validation.requireNullableIntegerGreaterThan(
                numberOfRooms,
                0,
                "numberOfRooms должен быть больше 0 или null"
        );
        this.centralHeating = centralHeating;
        this.furnish = Validation.requireNotNull(furnish, "furnish не может быть null");
        this.view = Validation.requireNotNull(view, "view не может быть null");
        this.house = Validation.requireNotNull(house, "house не может быть null");
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Integer getArea() {
        return area;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public boolean isCentralHeating() {
        return centralHeating;
    }

    public Furnish getFurnish() {
        return furnish;
    }

    public View getView() {
        return view;
    }

    public House getHouse() {
        return house;
    }


    public Flat toFlat(long id, ZonedDateTime creationDate) {
        return new Flat(
                id,
                name,
                coordinates,
                creationDate,
                area,
                numberOfRooms,
                centralHeating,
                furnish,
                view,
                house
        );
    }
}
