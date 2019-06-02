package main.database;

import main.api.utils.ApplicationException;
import main.api.utils.ExceptionCode;
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
import java.util.stream.Collectors;

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
        DATA d = (DATA) sessionFactory.getCurrentSession().get(data,id);
        if(d == null)
            throw new ApplicationException(ExceptionCode.ITEM_NOT_FOUND,id);
        return d;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<DATA> getAllItems() throws ApplicationException{
        return  (List<DATA>) sessionFactory.getCurrentSession().createCriteria(data)
                .list().stream().distinct().collect(Collectors.toList());
    }

    @Transactional
    public void createItem(DATA data) throws ApplicationException{
        sessionFactory.getCurrentSession().persist(data);
    }

    @Transactional
    public void modifyItem(DATA data){
        sessionFactory.getCurrentSession().update(data);
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

