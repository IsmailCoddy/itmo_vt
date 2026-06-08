package model;

import util.Validation;

/**
 * Дом, в котором находится квартира.
 *
 * <p>Название дома может быть `null`, а все числовые поля должны быть больше 0.</p>
 */
public class House {
    private String name;
    private long year;
    private long numberOfFloors;
    private long numberOfFlatsOnFloor;
    private long numberOfLifts;

    /**
     * Создает дом.
     *
     * @param name название дома, может быть {@code null}
     * @param year год, должен быть больше 0
     * @param numberOfFloors количество этажей, должно быть больше 0
     * @param numberOfFlatsOnFloor количество квартир на этаже, должно быть больше 0
     * @param numberOfLifts количество лифтов, должно быть больше 0
     */
    public House(String name, long year, long numberOfFloors, long numberOfFlatsOnFloor, long numberOfLifts) {
        setName(name);
        setYear(year);
        setNumberOfFloors(numberOfFloors);
        setNumberOfFlatsOnFloor(numberOfFlatsOnFloor);
        setNumberOfLifts(numberOfLifts);
    }

    /**
     * Возвращает название дома.
     *
     * @return название дома или {@code null}
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название дома.
     *
     * @param name название дома, может быть {@code null}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает год дома.
     *
     * @return год
     */
    public long getYear() {
        return year;
    }

    /**
     * Устанавливает год дома.
     *
     * @param year год, должен быть больше 0
     */
    public void setYear(long year) {
        this.year = Validation.requireGreaterThan(year, 0, "house.year должен быть больше 0");
    }

    /**
     * Возвращает количество этажей.
     *
     * @return количество этажей
     */
    public long getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Устанавливает количество этажей.
     *
     * @param numberOfFloors количество этажей, должно быть больше 0
     */
    public void setNumberOfFloors(long numberOfFloors) {
        this.numberOfFloors = Validation.requireGreaterThan(
                numberOfFloors,
                0,
                "house.numberOfFloors должен быть больше 0"
        );
    }

    /**
     * Возвращает количество квартир на этаже.
     *
     * @return количество квартир на этаже
     */
    public long getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    /**
     * Устанавливает количество квартир на этаже.
     *
     * @param numberOfFlatsOnFloor количество квартир на этаже, должно быть больше 0
     */
    public void setNumberOfFlatsOnFloor(long numberOfFlatsOnFloor) {
        this.numberOfFlatsOnFloor = Validation.requireGreaterThan(
                numberOfFlatsOnFloor,
                0,
                "house.numberOfFlatsOnFloor должен быть больше 0"
        );
    }

    /**
     * Возвращает количество лифтов.
     *
     * @return количество лифтов
     */
    public long getNumberOfLifts() {
        return numberOfLifts;
    }

    /**
     * Устанавливает количество лифтов.
     *
     * @param numberOfLifts количество лифтов, должно быть больше 0
     */
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
