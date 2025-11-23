package repository;

import java.util.*;
import core.Book;

public class BookRepository {

    private List<Book> books = new ArrayList<>();

    public void add(Book b) {
        books.add(b);
    }

    public List<Book> getAll() {
        List<Book> copy = new ArrayList<>(books);
        copy.sort(Comparator.comparing(Book::getIsbn));
        return copy;
    }
}
