package main.database.dao;

import main.database.AbstractRepository;
import main.database.dto.PollData;
import org.springframework.stereotype.Repository;

@Repository
public class PollRepository extends AbstractRepository<PollData>  {
    protected PollRepository() {
        super(PollData.class);
    }

}
