package controller;

import repository.EditorialRepository;
import repository.PersonRepository;
import util.Response;
import core.Publisher;
import core.Manager;

public class EditorialController {

    private final EditorialRepository repo;
    private final PersonRepository personRepo;

    public EditorialController(EditorialRepository repo, PersonRepository personRepo) {
        this.repo = repo;
        this.personRepo = personRepo;
    }

    public Response<Void> createEditorial(String nit, String name, String address, long managerId) {
        if (nit == null || name == null || address == null || nit.isBlank() || name.isBlank() || address.isBlank()) {
            return new Response<>(Response.Status.ERROR, "Campos vacíos", null);
        }
        // NIT format XXX.XXX.XXX-X
        if (!nit.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d")) {
            return new Response<>(Response.Status.ERROR, "Formato de NIT inválido", null);
        }
        if (repo.findByNit(nit) != null) {
            return new Response<>(Response.Status.ERROR, "Ya existe editorial con ese NIT", null);
        }
        // manager must exist
        var man = personRepo.findById(managerId);
        if (man == null || !(man instanceof Manager)) {
            return new Response<>(Response.Status.ERROR, "Gerente inválido", null);
        }
        Publisher p = new Publisher(nit, name, address, (Manager)man);
        repo.add(p);
        return new Response<>(Response.Status.CREATED, "Editorial creada", null);
    }
}
