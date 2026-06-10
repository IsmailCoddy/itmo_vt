package collection;

import model.Flat;

import java.time.ZonedDateTime;
import java.util.List;


public interface FlatCollection {


    String getCollectionType();


    ZonedDateTime getInitializationDate();


    int size();


    boolean isEmpty();


    long generateId();


    void add(Flat flat);


    boolean update(long id, Flat updated);


    Flat removeById(long id);


    void clear();


    Flat head();


    Flat removeHead();


    Flat getById(long id);


    List<Flat> sortedElements();


    List<Flat> sortedByName();


    Flat minByCentralHeating();


    long countLessThanCentralHeating(boolean centralHeating);


    List<Flat> filterContainsName(String name);
}
