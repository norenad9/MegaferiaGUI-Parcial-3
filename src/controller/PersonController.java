package controller;

import repository.PersonRepository;
import util.Response;
import core.Author;
import core.Manager;
import core.Narrator;

public class PersonController {

    private final PersonRepository repo;

    public PersonController(PersonRepository repo) {
        this.repo = repo;
    }

    public Response<Void> createAuthor(long id, String firstname, String lastname) {
        if (id < 0 || firstname == null || firstname.isBlank() || lastname == null || lastname.isBlank()) {
            return new Response<>(Response.Status.ERROR, "ID o nombre inválidos", null);
        }
        if (repo.findById(id) != null) {
            return new Response<>(Response.Status.ERROR, "Ya existe una persona con ese ID", null);
        }
        Author a = new Author(id, firstname, lastname);
        repo.add(a);
        return new Response<>(Response.Status.CREATED, "Autor creado", null);
    }

    public Response<Void> createManager(long id, String firstname, String lastname) {
        if (id < 0 || firstname == null || firstname.isBlank() || lastname == null || lastname.isBlank()) {
            return new Response<>(Response.Status.ERROR, "ID o nombre inválidos", null);
        }
        if (repo.findById(id) != null) {
            return new Response<>(Response.Status.ERROR, "Ya existe una persona con ese ID", null);
        }
        Manager m = new Manager(id, firstname, lastname);
        repo.add(m);
        return new Response<>(Response.Status.CREATED, "Gerente creado", null);
    }

    public Response<Void> createNarrator(long id, String firstname, String lastname) {
        if (id < 0 || firstname == null || firstname.isBlank() || lastname == null || lastname.isBlank()) {
            return new Response<>(Response.Status.ERROR, "ID o nombre inválidos", null);
        }
        if (repo.findById(id) != null) {
            return new Response<>(Response.Status.ERROR, "Ya existe una persona con ese ID", null);
        }
        Narrator n = new Narrator(id, firstname, lastname);
        repo.add(n);
        return new Response<>(Response.Status.CREATED, "Narrador creado", null);
    }
}
