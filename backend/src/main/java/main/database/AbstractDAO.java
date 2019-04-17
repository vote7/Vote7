package main.database;

import main.database.dto.TestData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Transactional
public class AbstractDAO<DATA> {

    @Autowired
    private SessionFactory sessionFactory;
    private Class<? extends DATA> clazz;

    public AbstractDAO(Class<? extends DATA> clazz){
        this.clazz = clazz;
    }

    public DATA getItem(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(clazz,id);
    }

    @SuppressWarnings("unchecked")
    public List<DATA> getAllItems(){
        Session session = sessionFactory.getCurrentSession();
        return (List<DATA>) session.createCriteria(clazz).list();
    }

    public int save(DATA data){
        Session session = sessionFactory.getCurrentSession();
        return (int) session.save(data);
    }
}

