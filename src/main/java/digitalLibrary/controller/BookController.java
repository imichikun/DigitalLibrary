package digitalLibrary.controller;

import digitalLibrary.dao.BookDao;
import digitalLibrary.dao.ReaderDao;
import digitalLibrary.entity.Book;
import digitalLibrary.entity.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private BookDao bookDao;
    private ReaderDao readerDao;

    @Autowired
    public BookController(BookDao bookDao, ReaderDao readerDao) {
        this.bookDao = bookDao;
        this.readerDao = readerDao;
    }

    @GetMapping("/")
    public String getAllBooks(Model model){
        model.addAttribute("allBooks", bookDao.getAllBooks());
        return "3books_list";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") int id, Model model, @ModelAttribute("reader") Reader reader){
        model.addAttribute("book", bookDao.getBookById(id));

        Optional<Reader> readerOfBook = bookDao.getReaderOfBook(id);
        if(readerOfBook.isPresent())
            model.addAttribute("readerOfBook", readerOfBook.get());
        else
            model.addAttribute("allReaders", readerDao.getAllReaders());

        return "6one_book";
    }

    @GetMapping("/new")
    public String getBookById(Model model){
        model.addAttribute("book", new Book());
        return "7book_create_update";
    }

    @PostMapping("/")
    public String saveBook(@ModelAttribute("book") Book book){
        if( book.getId() != 0) {
            Optional<Reader> readerOfBook = Optional.ofNullable(bookDao.getBookById(book.getId()).getReader());
            book.setReader(readerOfBook.get()); // небольшой костыль чтобы получить Читателя для книги, после её апдейта
        }                                       // но зато одна view create_update вместо двух разных

        bookDao.save(book);
        return "redirect:/books/";
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDao.getBookById(id));

        return "7book_create_update";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDao.deleteBook(id);
        return "redirect:/books/";
    }

/////////////////////////
    @PostMapping("/setBook/{id}")
    public String setBook2Reader(@ModelAttribute("reader") Reader reader, @PathVariable("id") int id){
        bookDao.setBook(reader, id);
        return "redirect:/books/" + id;
    }

    @PostMapping("/removeBook/{id}")
    public String removeBook(@PathVariable("id") int id){
        bookDao.removeBook(id);
        return "redirect:/books/"+id;
    }
}