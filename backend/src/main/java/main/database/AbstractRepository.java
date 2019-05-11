package main.database;

import main.api.utils.ApplicationException;
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
    public DATA getItem(int id) throws ApplicationException {
        return (DATA) sessionFactory.getCurrentSession().get(data,id);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<DATA> getAllItems() throws ApplicationException{
        return  (List<DATA>) sessionFactory.getCurrentSession().createCriteria(data).list();
    }

    @Transactional
    public void createItem(DATA data) throws ApplicationException{
        sessionFactory.getCurrentSession().persist(data);
    }
    
    @Transactional
    public void removeItem(DATA data) throws ApplicationException{
        sessionFactory.getCurrentSession().delete(data);
    }

    @Transactional
    public void removeItem(int id) throws ApplicationException{
        sessionFactory.getCurrentSession().delete(getItem(id));
    }
}

