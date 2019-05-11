package main.database.dao;

import main.database.AbstractRepository;
import main.database.dto.AnswerData;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerRepository extends AbstractRepository<AnswerData>  {
    protected AnswerRepository() {
        super(AnswerData.class);
    }

}
