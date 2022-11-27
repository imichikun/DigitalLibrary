package digitalLibrary.controller;

import digitalLibrary.dao.BookDao;
import digitalLibrary.dao.ReaderDao;
import digitalLibrary.entity.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/readers")
public class ReaderController {
    private ReaderDao readerDao;
    private BookDao bookDao;

    @Autowired
    public ReaderController(ReaderDao readerDao, BookDao bookDao) {
        this.readerDao = readerDao;
        this.bookDao = bookDao;
    }

    @GetMapping("/")
    public String getAllPeople(Model model){
        model.addAttribute("allReaders", readerDao.getAllReaders());
        return "2readers_list";
    }

    @GetMapping("/{id}")
    public String getReaderById(@PathVariable("id") int id, Model model){
        model.addAttribute("reader", readerDao.getReaderById(id));
        model.addAttribute("readerBooks", bookDao.getBooksGotByReader(id)); // добавляем список книг, взятых читателем
        return "4one_reader";
    }

    @GetMapping("/new")
    public String getReaderById(Model model){
        model.addAttribute("reader", new Reader());
        return "5reader_create_update";
    }

    @PostMapping("/")
    public String saveReader(@ModelAttribute("reader") Reader reader){
        readerDao.save(reader);
        return "redirect:/readers/";
    }

    @PutMapping("/{id}")
    public String updateReader(@PathVariable("id") int id, Model model){
        model.addAttribute("reader", readerDao.getReaderById(id));
        return "5reader_create_update";
    }

    @DeleteMapping("/{id}")
    public String deleteReader(@PathVariable("id") int id){
        readerDao.deleteReader(id);
        return "redirect:/readers/";
    }
}