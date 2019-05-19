package main.database.dao;

import main.database.AbstractRepository;
import main.database.dto.PollData;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Repository
public class PollRepository extends AbstractRepository<PollData>  {
    protected PollRepository() {
        super(PollData.class);
    }

    @Transactional
    public List<PollData> getUserPolls(int userId){
        TypedQuery<PollData> query = getSessionFactory().getCurrentSession().createNativeQuery("select  * from polls where group_id in " +
                "(select group_id from group_members where user_id = :userId)",PollData.class).setParameter("userId",userId);
        return query.getResultList();
    }

}
