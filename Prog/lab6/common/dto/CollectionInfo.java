package common.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;


public class CollectionInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String collectionType;
    private final ZonedDateTime initializationDate;
    private final int size;
    private final String storageFileName;


    public CollectionInfo(String collectionType, ZonedDateTime initializationDate, int size, String storageFileName) {
        this.collectionType = collectionType;
        this.initializationDate = initializationDate;
        this.size = size;
        this.storageFileName = storageFileName;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public ZonedDateTime getInitializationDate() {
        return initializationDate;
    }

    public int getSize() {
        return size;
    }

    public String getStorageFileName() {
        return storageFileName;
    }
}
