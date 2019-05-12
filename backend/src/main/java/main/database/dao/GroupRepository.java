package main.database.dao;

import main.database.AbstractRepository;
import main.database.dto.GroupData;
import org.springframework.stereotype.Repository;

@Repository
public class GroupRepository extends AbstractRepository<GroupData>  {
    protected GroupRepository() {
        super(GroupData.class);
    }

}
