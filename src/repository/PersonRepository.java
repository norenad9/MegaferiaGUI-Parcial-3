package repository;

import java.util.*;
import core.Person;

public class PersonRepository {

    private List<Person> persons = new ArrayList<>();

    public void add(Person p) {
        persons.add(p);
    }

    public List<Person> getAll() {
        return new ArrayList<>(persons);
    }

    public Person findById(long id) {
        for (Person p : persons) {
            if (p.getId() == id) return p;
        }
        return null;
    }
}
