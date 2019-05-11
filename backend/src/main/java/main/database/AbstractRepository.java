package main.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;

public abstract  class AbstractRepository<DATA> {

    @Autowired
    private SessionFactory sessionFactory;
    private Class<DATA> data;

    protected AbstractRepository(Class<DATA> data){
        this.data = data;
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public DATA getItem(int id){
        Session session = sessionFactory.openSession();
        DATA o = (DATA) session.get(data,id);
        return o;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<DATA> getAllItems(){
        Session session = sessionFactory.openSession();
        List<DATA> o = (List<DATA>) session.createCriteria(data).list();
        return o;
    }

    @Transactional
    public void createItem(DATA data){
        Session session = sessionFactory.getCurrentSession();
        session.persist(data);
    }
    

}

