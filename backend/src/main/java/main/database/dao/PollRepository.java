package main.database.dao;

import main.api.utils.ApplicationException;
import main.api.utils.ExceptionCode;
import main.database.AbstractRepository;
import main.database.dto.GroupData;
import main.database.dto.PollData;
import org.apache.tomcat.jni.Poll;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
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

    @Transactional
    public PollData getPollByQuestionId(int qid) throws ApplicationException {
        NativeQuery<PollData> query = getSessionFactory().getCurrentSession().createNativeQuery("select * from Polls \n" +
                "\twhere poll_id = (select poll_id from questions\n" +
                "\t\twhere question_id = :qid)", PollData.class).setParameter("qid", qid);
        PollData data;
        if((data = query.uniqueResult()) == null)
            throw new ApplicationException(ExceptionCode.ITEM_NOT_FOUND,qid);
        return data;
    }

}
