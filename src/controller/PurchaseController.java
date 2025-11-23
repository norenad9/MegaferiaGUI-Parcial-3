package controller;

import repository.StandRepository;
import repository.EditorialRepository;
import util.Response;
import java.util.*;
import core.*;

public class PurchaseController {

    private final StandRepository standRepo;
    private final EditorialRepository editorialRepo;

    public PurchaseController(StandRepository standRepo, EditorialRepository editorialRepo) {
        this.standRepo = standRepo;
        this.editorialRepo = editorialRepo;
    }

    public Response<Void> purchaseStands(List<Long> standIds, List<String> publisherNits) {
        if (standIds == null || standIds.isEmpty() || publisherNits == null || publisherNits.isEmpty()) {
            return new Response<>(Response.Status.ERROR, "Lista vacía", null);
        }
        // ensure no duplicates
        Set<Long> sset = new HashSet<>(standIds);
        if (sset.size() != standIds.size()) return new Response<>(Response.Status.ERROR, "Stands repetidos", null);
        Set<String> pset = new HashSet<>(publisherNits);
        if (pset.size() != publisherNits.size()) return new Response<>(Response.Status.ERROR, "Editoriales repetidas", null);

        // find stands and publishers
        List<Stand> stands = new ArrayList<>();
        for (Long id : standIds) {
            Stand st = null;
            for (Stand s : standRepo.getAll()) {
                if (s.getId() == id) { st = s; break; }
            }
            if (st == null) return new Response<>(Response.Status.ERROR, "Stand no existe: " + id, null);
            stands.add(st);
        }

        List<Publisher> publishers = new ArrayList<>();
        for (String nit : publisherNits) {
            var p = editorialRepo.findByNit(nit);
            if (p == null) return new Response<>(Response.Status.ERROR, "Editorial no existe: " + nit, null);
            publishers.add(p);
        }

        // assign each stand to each publisher (link)
        for (Stand s : stands) {
            for (Publisher p : publishers) {
                s.addPublisher(p);
            }
        }

        return new Response<>(Response.Status.CREATED, "Compra realizada", null);
    }
}
