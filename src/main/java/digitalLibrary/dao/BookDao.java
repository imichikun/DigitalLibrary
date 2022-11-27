package digitalLibrary.dao;

import digitalLibrary.entity.Book;
import digitalLibrary.entity.Reader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class BookDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Book> getAllBooks() {
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        return books;
    }

    public Book getBookById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Book.class, id);
    }

    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(book);
    }

    public void deleteBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(getBookById(id));
    }

/////////////
    public void setBook(Reader reader, int id) {
        Session session = sessionFactory.getCurrentSession();
        Book setBook = session.get(Book.class, id);
        reader.setBooks(new ArrayList<Book>(Collections.singleton(setBook)));
        setBook.setReader(reader);
    }

    public List<Book> getBooksGotByReader(int id) {
        Session session = sessionFactory.getCurrentSession();
        List<Book> readerBooks = session.createQuery("SELECT b FROM Book b " +
                "WHERE human_id = " + id, Book.class).getResultList();
//        List<String> readerBooksUpdated = readerBooks.stream().map(book -> book.getTitle()+", "+book.getYear()).toList();

        return readerBooks;
    }

    public Optional<Reader> getReaderOfBook(int id) {                   // получаем Читателя у книги через Optional
        Session session = sessionFactory.getCurrentSession();
        Book requiredBook = session.find(Book.class, id);
                                                                // Optional.of нельзя применять, если внутри может быть
        return Optional.ofNullable(requiredBook.getReader());   // null'вый объект, поэтому применяем Optional.ofNullable,
    }                                                           // который в таком случае вернёт Optional.empty

    public void removeBook(int id) {                                // просто убираем Читателя у книги, устанавливая null
        Session session = sessionFactory.getCurrentSession();
        Book requiredBook = session.find(Book.class, id);
        requiredBook.setReader(null);
    }
}