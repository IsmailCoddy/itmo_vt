package common.dto;

import model.Flat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CommandResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final boolean success;
    private final String message;
    private final CollectionInfo collectionInfo;
    private final Flat flat;
    private final ArrayList<Flat> flats;

    private CommandResponse(
            boolean success,
            String message,
            CollectionInfo collectionInfo,
            Flat flat,
            List<Flat> flats
    ) {
        this.success = success;
        this.message = message;
        this.collectionInfo = collectionInfo;
        this.flat = flat;
        this.flats = flats == null ? new ArrayList<>() : new ArrayList<>(flats);
    }

    public static CommandResponse message(boolean success, String message) {
        return new CommandResponse(success, message, null, null, null);
    }

    public static CommandResponse info(CollectionInfo collectionInfo) {
        return new CommandResponse(true, null, collectionInfo, null, null);
    }

    public static CommandResponse flat(String emptyMessage, Flat flat) {
        return new CommandResponse(true, emptyMessage, null, flat, null);
    }

    public static CommandResponse flats(String emptyMessage, List<Flat> flats) {
        return new CommandResponse(true, emptyMessage, null, null, flats);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public CollectionInfo getCollectionInfo() {
        return collectionInfo;
    }

    public Flat getFlat() {
        return flat;
    }

    public List<Flat> getFlats() {
        return Collections.unmodifiableList(flats);
    }
}
