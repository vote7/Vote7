package main.database.dao;

import main.api.utils.ApplicationException;
import main.api.utils.ExceptionCode;
import main.database.AbstractRepository;
import main.database.dto.UserData;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class UserRepository extends AbstractRepository<UserData> {

    protected UserRepository() {
        super(UserData.class);
    }

    @Override
    @Transactional
    public void createItem(UserData userData) {
        if(getItem(userData.getEmail()) != null)
            throw new ApplicationException(ExceptionCode.USER_ALREADY_REGISTERED,userData.getEmail());
        super.createItem(userData);
    }

    @Transactional
    public UserData getItem(String email){
        Session session = getSessionFactory().getCurrentSession();
        return (UserData) session.createQuery("from UserData u where u.email = :email")
                .setParameter("email",email).uniqueResult();
    }

    @Transactional
    public UserData loginUser(String email,String password){
        Session session = getSessionFactory().getCurrentSession();
        return (UserData) session.createQuery("from UserData u where u.email = :email and u.password = :password")
                .setParameter("email",email).setParameter("password",password).uniqueResult();
    }
}
