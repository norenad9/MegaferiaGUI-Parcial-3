package repository;

import java.util.*;
import core.Publisher;

public class EditorialRepository {

    private List<Publisher> editorials = new ArrayList<>();

    public void add(Publisher p) {
        editorials.add(p);
    }

    public List<Publisher> getAll() {
        List<Publisher> copy = new ArrayList<>(editorials);
        // sort by NIT lexicographically
        copy.sort(Comparator.comparing(Publisher::getNit));
        return copy;
    }

    public Publisher findByNit(String nit) {
        for (Publisher p : editorials) {
            if (p.getNit().equals(nit)) return p;
        }
        return null;
    }
}
