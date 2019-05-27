package main.database.dao;

import main.database.AbstractRepository;
import main.database.dto.GroupData;
import main.database.dto.PollData;
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

}
