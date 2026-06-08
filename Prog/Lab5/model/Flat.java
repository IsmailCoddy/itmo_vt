package model;

import util.Validation;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Квартира, которая хранится в коллекции.
 *
 * <p>Класс реализует {@link Comparable}, потому что так требует задание. Сравнение
 * идет по `id`, потому что он уникальный и программа сама его выдает.</p>
 */
public class Flat implements Comparable<Flat> {
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

    /**
     * Создает квартиру со всеми полями.
     *
     * @param id уникальный положительный идентификатор
     * @param name название, не {@code null} и не пустое
     * @param coordinates координаты, не {@code null}
     * @param creationDate дата создания, не {@code null}
     * @param area площадь, должна быть больше 0
     * @param numberOfRooms количество комнат, может быть {@code null}, если задано - больше 0
     * @param centralHeating наличие центрального отопления
     * @param furnish отделка, не {@code null}
     * @param view вид, не {@code null}
     * @param house дом, не {@code null}
     */
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

    /**
     * Возвращает идентификатор квартиры.
     *
     * @return идентификатор
     */
    public long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор квартиры.
     *
     * @param id идентификатор, должен быть больше 0
     */
    public void setId(long id) {
        this.id = Validation.requireGreaterThan(id, 0, "id должен быть больше 0");
    }

    /**
     * Возвращает название квартиры.
     *
     * @return название квартиры
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название квартиры.
     *
     * @param name название, не {@code null} и не пустое
     */
    public void setName(String name) {
        this.name = Validation.requireNotBlank(name, "name не может быть null или пустым");
    }

    /**
     * Возвращает координаты квартиры.
     *
     * @return координаты
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Устанавливает координаты квартиры.
     *
     * @param coordinates координаты, не {@code null}
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = Validation.requireNotNull(coordinates, "coordinates не может быть null");
    }

    /**
     * Возвращает дату создания квартиры.
     *
     * @return дата создания
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Устанавливает дату создания квартиры.
     *
     * @param creationDate дата создания, не {@code null}
     */
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = Validation.requireNotNull(creationDate, "creationDate не может быть null");
    }

    /**
     * Возвращает площадь квартиры.
     *
     * @return площадь
     */
    public Integer getArea() {
        return area;
    }

    /**
     * Устанавливает площадь квартиры.
     *
     * @param area площадь, должна быть больше 0
     */
    public void setArea(Integer area) {
        this.area = Validation.requireIntegerGreaterThan(area, 0, "area должен быть больше 0 и не null");
    }

    /**
     * Возвращает количество комнат.
     *
     * @return количество комнат или {@code null}
     */
    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Устанавливает количество комнат.
     *
     * @param numberOfRooms количество комнат, может быть {@code null}; если задано, должно быть больше 0
     */
    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = Validation.requireNullableIntegerGreaterThan(
                numberOfRooms,
                0,
                "numberOfRooms должен быть больше 0 или null"
        );
    }

    /**
     * Проверяет наличие центрального отопления.
     *
     * @return есть ли центральное отопление
     */
    public boolean isCentralHeating() {
        return centralHeating;
    }

    /**
     * Устанавливает наличие центрального отопления.
     *
     * @param centralHeating есть ли центральное отопление
     */
    public void setCentralHeating(boolean centralHeating) {
        this.centralHeating = centralHeating;
    }

    /**
     * Возвращает отделку квартиры.
     *
     * @return отделка
     */
    public Furnish getFurnish() {
        return furnish;
    }

    /**
     * Устанавливает отделку квартиры.
     *
     * @param furnish отделка, не {@code null}
     */
    public void setFurnish(Furnish furnish) {
        this.furnish = Validation.requireNotNull(furnish, "furnish не может быть null");
    }

    /**
     * Возвращает вид из квартиры.
     *
     * @return вид
     */
    public View getView() {
        return view;
    }

    /**
     * Устанавливает вид из квартиры.
     *
     * @param view вид, не {@code null}
     */
    public void setView(View view) {
        this.view = Validation.requireNotNull(view, "view не может быть null");
    }

    /**
     * Возвращает дом квартиры.
     *
     * @return дом
     */
    public House getHouse() {
        return house;
    }

    /**
     * Устанавливает дом квартиры.
     *
     * @param house дом, не {@code null}
     */
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
