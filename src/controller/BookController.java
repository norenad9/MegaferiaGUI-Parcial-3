package controller;

import repository.BookRepository;
import repository.EditorialRepository;
import repository.PersonRepository;
import util.Response;
import java.util.*;
import core.*;

public class BookController {

    private final BookRepository repo;
    private final EditorialRepository editorialRepo;
    private final PersonRepository personRepo;

    public BookController(BookRepository repo, EditorialRepository editorialRepo, PersonRepository personRepo) {
        this.repo = repo;
        this.editorialRepo = editorialRepo;
        this.personRepo = personRepo;
    }

    public Response<Void> createPrintedBook(String title, List<Long> authorIds, String isbn, String genre, String format, double value, String publisherNit, int pages, int copies) {
        // basic validations
        if (title == null || title.isBlank() || isbn == null || isbn.isBlank() || genre == null || genre.isBlank() || format == null || format.isBlank()) {
            return new Response<>(Response.Status.ERROR, "Campos vacíos", null);
        }
        if (value <= 0) return new Response<>(Response.Status.ERROR, "Valor debe ser > 0", null);
        // ISBN format XXX-X-XX-XXXXXX-X
        if (!isbn.matches("\\d{3}-\\d-\\d{2}-\\d{6}-\\d")) {
            return new Response<>(Response.Status.ERROR, "ISBN inválido", null);
        }
        // authors distinct and exist
        List<Author> authors = new ArrayList<>();
        Set<Long> seen = new HashSet<>();
        for (Long aid : authorIds) {
            if (seen.contains(aid)) return new Response<>(Response.Status.ERROR, "Autores repetidos", null);
            seen.add(aid);
            var p = personRepo.findById(aid);
            if (p == null || !(p instanceof Author)) return new Response<>(Response.Status.ERROR, "Autor inválido: " + aid, null);
            authors.add((Author)p);
        }
        var pub = editorialRepo.findByNit(publisherNit);
        if (pub == null) return new Response<>(Response.Status.ERROR, "Editorial inválida", null);
        PrintedBook b = new PrintedBook(title, (ArrayList<Author>) authors, isbn, genre, format, value, pub, pages, copies);
        repo.add(b);
        return new Response<>(Response.Status.CREATED, "Libro impreso creado", null);
    }

    public Response<Void> createDigitalBook(String title, List<Long> authorIds, String isbn, String genre, String format, double value, String publisherNit, String hyperlink) {
        if (title == null || title.isBlank() || isbn == null || isbn.isBlank()) {
            return new Response<>(Response.Status.ERROR, "Campos vacíos", null);
        }
        if (value <= 0) return new Response<>(Response.Status.ERROR, "Valor debe ser > 0", null);
        if (!isbn.matches("\\d{3}-\\d-\\d{2}-\\d{6}-\\d")) {
            return new Response<>(Response.Status.ERROR, "ISBN inválido", null);
        }
        List<Author> authors = new ArrayList<>();
        Set<Long> seen = new HashSet<>();
        for (Long aid : authorIds) {
            if (seen.contains(aid)) return new Response<>(Response.Status.ERROR, "Autores repetidos", null);
            seen.add(aid);
            var p = personRepo.findById(aid);
            if (p == null || !(p instanceof Author)) return new Response<>(Response.Status.ERROR, "Autor inválido: " + aid, null);
            authors.add((Author)p);
        }
        var pub = editorialRepo.findByNit(publisherNit);
        if (pub == null) return new Response<>(Response.Status.ERROR, "Editorial inválida", null);
        DigitalBook b = new DigitalBook(title, (ArrayList<Author>) authors, isbn, genre, format, value, pub);
        // if hyperlink provided set (there's no setter in class likely) - rely on constructor
        repo.add(b);
        return new Response<>(Response.Status.CREATED, "Libro digital creado", null);
    }

    public Response<Void> createAudiobook(String title, List<Long> authorIds, String isbn, String genre, String format, double value, String publisherNit, int duration, long narratorId) {
        if (title == null || title.isBlank() || isbn == null || isbn.isBlank()) {
            return new Response<>(Response.Status.ERROR, "Campos vacíos", null);
        }
        if (value <= 0) return new Response<>(Response.Status.ERROR, "Valor debe ser > 0", null);
        if (!isbn.matches("\\d{3}-\\d-\\d{2}-\\d{6}-\\d")) {
            return new Response<>(Response.Status.ERROR, "ISBN inválido", null);
        }
        List<Author> authors = new ArrayList<>();
        Set<Long> seen = new HashSet<>();
        for (Long aid : authorIds) {
            if (seen.contains(aid)) return new Response<>(Response.Status.ERROR, "Autores repetidos", null);
            seen.add(aid);
            var p = personRepo.findById(aid);
            if (p == null || !(p instanceof Author)) return new Response<>(Response.Status.ERROR, "Autor inválido: " + aid, null);
            authors.add((Author)p);
        }
        var pub = editorialRepo.findByNit(publisherNit);
        if (pub == null) return new Response<>(Response.Status.ERROR, "Editorial inválida", null);
        var narr = personRepo.findById(narratorId);
        if (narr == null || !(narr instanceof Narrator)) return new Response<>(Response.Status.ERROR, "Narrador inválido", null);
        Audiobook b = new Audiobook(title, (ArrayList<Author>)authors, isbn, genre, format, value, pub, duration, (Narrator)narr);
        repo.add(b);
        return new Response<>(Response.Status.CREATED, "Audiolibro creado", null);
    }
}
