package model;

import util.Validation;

/**
 * Координаты квартиры.
 *
 * <p>Тут всего два поля, но их тоже надо проверять по условию.</p>
 */
public class Coordinates {
    private Long x;
    private Double y;

    /**
     * Создает координаты.
     *
     * @param x координата x, должна быть больше -740 и не {@code null}
     * @param y координата y, не {@code null}
     */
    public Coordinates(Long x, Double y) {
        setX(x);
        setY(y);
    }

    /**
     * Возвращает координату x.
     *
     * @return координата x
     */
    public Long getX() {
        return x;
    }

    /**
     * Устанавливает координату x.
     *
     * @param x координата x, должна быть больше -740 и не {@code null}
     */
    public void setX(Long x) {
        this.x = Validation.requireLongGreaterThan(x, -740, "coordinates.x должен быть больше -740 и не null");
    }

    /**
     * Возвращает координату y.
     *
     * @return координата y
     */
    public Double getY() {
        return y;
    }

    /**
     * Устанавливает координату y.
     *
     * @param y координата y, не {@code null}
     */
    public void setY(Double y) {
        this.y = Validation.requireNotNull(y, "coordinates.y не может быть null");
    }

    @Override
    public String toString() {
        return "Coordinates{x=" + x + ", y=" + y + '}';
    }
}
