package input;

import model.Flat;

import java.time.ZonedDateTime;

/**
 * Интерфейс для чтения объекта {@link Flat}.
 *
 * <p>Команде `add` не важно, как именно читаются поля. Она просто просит
 * прочитать квартиру.</p>
 */
public interface FlatReader {
    /**
     * Считывает квартиру с уже заданными служебными полями.
     *
     * @param input источник ввода
     * @param id идентификатор квартиры
     * @param creationDate дата создания квартиры
     * @return считанная квартира
     * @throws InputReadException если ввод закончился раньше времени
     */
    Flat readFlat(InputContext input, long id, ZonedDateTime creationDate) throws InputReadException;
}
