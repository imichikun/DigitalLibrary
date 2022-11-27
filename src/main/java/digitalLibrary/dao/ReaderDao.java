package digitalLibrary.dao;

import digitalLibrary.entity.Reader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class ReaderDao {
     private final SessionFactory sessionFactory;

    @Autowired
    public ReaderDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Reader> getAllReaders() {
        Session session = sessionFactory.getCurrentSession();
        List<Reader> readers = session.createQuery("SELECT r FROM Reader r", Reader.class).getResultList();
        return readers;
    }

    public Reader getReaderById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Reader.class, id);
    }

    public void save(Reader reader) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(reader);
    }

    public void deleteReader(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(getReaderById(id));
    }


}