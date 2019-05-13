package main.database.dao;

import main.database.AbstractRepository;
import main.database.dto.QuestionData;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepository extends AbstractRepository<QuestionData> {
    protected QuestionRepository() {
        super(QuestionData.class);
    }
}

