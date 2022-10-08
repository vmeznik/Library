package library.controller;

import com.google.gson.Gson;
import library.model.Book;
import library.logger.Logger;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;
import library.repository.BookRepository;

import javax.persistence.PostUpdate;
import java.util.ArrayList;
import java.util.List;

@RestController()
public class ApiController {
    private final BookRepository bookRepository;
    private String status;
    private final Gson gson = new Gson();

    public ApiController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PutMapping("/add")
    public void addBook(@RequestBody Book book) {
        if (book == null) {
            Logger.getInstance().log("Something went wrong while adding book , value is null");
        } else {
            bookRepository.addBook(book.getTitle(), book.getGenre(),
                    book.getAuthorsFirstName(), book.getAuthorsLastName(),
                    book.getStatus());
        }
    }

    @DeleteMapping("/remove")
    public void deleteBook(@RequestBody Book book) {
        if (book == null) {
            Logger.getInstance().log("Something went wrong while deleting book , value is null");
        } else {
            bookRepository.deleteById(book.getId());
        }
    }

    @PostMapping("/update")
    public void updateBook(@RequestBody Book book,@RequestParam(value = "id",defaultValue = "")String id) {
        if (book == null) {
            Logger.getInstance().log("Something went wrong while updating book , value is null");
        } else {
            bookRepository.updateStatus(book.getStatus(), book.getId());
        }
    }

    @GetMapping("/search")
    public String searchBook(@RequestBody Book book,
                             @RequestParam(value = "onlyOpen", defaultValue = "false") String onlyOpen) {
        if (Boolean.parseBoolean(onlyOpen)) {
            status = "open";
        } else {
            status = "o";
        }
        List<Book> books = bookRepository.find(book.getTitle(), book.getGenre(),
                book.getAuthorsFirstName(), book.getAuthorsLastName(),this.status);
        return  convertToJson(books);
    }

    @GetMapping("/searchAll")
    public List<Book> searchAllBooks(@RequestParam(value = "onlyOpen", defaultValue = "false") String onlyOpen) {
        if (Boolean.parseBoolean(onlyOpen)) {
            status = "open";
        } else {
           status = "o";
        }
        List<Book> books = bookRepository.findAll(this.status);
        return books; //convertToJson(books);
    }

    private String convertToJson(List<Book> list) {
        if ( list != null) {
            ArrayList<String> convertedList = new ArrayList<>();
            for (Book value : list) {
                convertedList.add(gson.toJson(value));
            }
            return gson.toJson(new JSONArray(convertedList));
        }else throw new NullPointerException("Book request is null");
    }

}
