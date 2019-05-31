package main.database.dao;

import main.api.utils.ApplicationException;
import main.api.utils.ExceptionCode;
import main.database.AbstractRepository;
import main.database.dto.GroupData;
import main.database.dto.PollData;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class GroupRepository extends AbstractRepository<GroupData>  {
    protected GroupRepository() {
        super(GroupData.class);
    }

    @Transactional
    public List<GroupData> getUserGroups(int uid){
        TypedQuery<GroupData> query = getSessionFactory().getCurrentSession().createNativeQuery("select  * from groups where group_id in " +
                "(select group_id from group_members where user_id = :uid)", GroupData.class).setParameter("uid", uid);
        return query.getResultList();
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<GroupData> getAdminGroups(int uid){
        return getSessionFactory()
                .getCurrentSession()
                .createCriteria(GroupData.class, "g")
                .createAlias("g.admin", "a")
                .add(Restrictions.eq("a.id",uid))
                .list();
    }

    @Transactional
    public GroupData getGroupByQuestionId(int qid) throws ApplicationException {
        NativeQuery<GroupData> query = getSessionFactory().getCurrentSession().createNativeQuery("select * from groups \n" +
                "where group_id = (select group_id from Polls \n" +
                "\twhere poll_id = (select poll_id from questions\n" +
                "\t\twhere question_id = :qid))", GroupData.class).setParameter("qid", qid);
        GroupData data;
        if((data = query.uniqueResult()) == null)
            throw new ApplicationException(ExceptionCode.ITEM_NOT_FOUND,qid);
        return data;
    }
}
