package controller;

import repository.StandRepository;
import util.Response;

public class StandController {

    private final StandRepository repo;

    public StandController(StandRepository repo) {
        this.repo = repo;
    }

    public Response<Void> createStand(long id, double precio, String ubicacion) {
        if (id < 0 || precio <= 0 || ubicacion == null || ubicacion.isEmpty()) {
            return new Response<>(Response.Status.ERROR, "Datos inválidos para el Stand", null);
        }

        core.Stand s = new core.Stand(id, precio);
        repo.add(s);

        return new Response<>(Response.Status.CREATED, "Stand creado con éxito", null);
    }
}
