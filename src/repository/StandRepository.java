package repository;

import java.util.*;

public class StandRepository {

    private List<core.Stand> stands = new ArrayList<>();

    public void add(core.Stand s) {
        stands.add(s);
    }

    public List<core.Stand> getAll() {
        return new ArrayList<>(stands);
    }
}
