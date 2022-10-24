package library.controller;

import com.google.gson.Gson;
import library.model.Book;
import library.logger.Logger;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;
import library.repository.BookRepository;

import java.util.ArrayList;

@RestController()
public class ApiController {
    private final BookRepository bookRepository;
    private final Gson gson = new Gson();

    public ApiController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/add")
    public void addBook(@RequestBody Book book) {
        if (book == null) {
            Logger.getInstance().log("Something went wrong while adding book , value is null");
        } else {
            Logger.getInstance().log(book + "- added");
            bookRepository.addBook(book.getTitle(), book.getGenre(),
                    book.getAuthorsFirstName(), book.getAuthorsLastName(),
                    book.getStatus());
        }
    }

    @DeleteMapping("/remove")
    public void deleteBook(@RequestParam(value = "id", defaultValue = "") String id) {
        if (id.equalsIgnoreCase("")) {
            Logger.getInstance().log("Something went wrong while deleting book , value is empty");
        } else {
            Logger.getInstance().log("Book with id: " + id + " was removed");
            bookRepository.deleteById(Integer.parseInt(id));
        }
    }

    @PutMapping("/update")
    public void updateBook(@RequestBody Book book) {
        if (book == null) {
            Logger.getInstance().log("Something went wrong while updating book , value is null");
        } else {
            Logger.getInstance().log(book + "- was updated to : " + book.getStatus());
            bookRepository.updateStatus(book.getStatus(), book.getId());
        }
    }

    @PostMapping("/search")
    public ArrayList<Book> searchBook(@RequestBody Book book,
                                      @RequestParam(value = "onlyOpen", defaultValue = "false") String onlyOpen) {
        ArrayList<Book> books;
        if (Boolean.parseBoolean(onlyOpen)) {
            books = bookRepository.findOnlyOpen(book.getTitle(), book.getGenre(),
                    book.getAuthorsFirstName(), book.getAuthorsLastName(),"open");
        } else {
            books = bookRepository.find(book.getTitle(), book.getGenre(),
                    book.getAuthorsFirstName(), book.getAuthorsLastName());
        }
        return books;
    }

    @PostMapping("/searchAll")
    public ArrayList<Book> searchAllBooks(@RequestParam(value = "onlyOpen", defaultValue = "false") String onlyOpen) {
        ArrayList<Book> books;
        if (Boolean.parseBoolean(onlyOpen)) {
            books = bookRepository.findAllOnlyOpen("open");
        } else {
            books = bookRepository.findAll();
        }
        return books;
    }


}
