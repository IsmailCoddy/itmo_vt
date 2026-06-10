package input;

import model.Flat;

import java.time.ZonedDateTime;


public interface FlatReader {


    Flat readFlat(InputContext input, long id, ZonedDateTime creationDate) throws InputReadException;
}
