package storage;

import model.Flat;

import java.util.Collection;
import java.util.List;


public interface FlatStorage {


    String getFileName();


    List<Flat> load();


    void save(Collection<Flat> flats) throws StorageException;
}
