package main.database.dao;

import main.database.AbstractRepository;
import main.database.dto.PollData;
import main.database.dto.QuestionData;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class QuestionRepository extends AbstractRepository<QuestionData> {
    protected QuestionRepository() {
        super(QuestionData.class);
    }

    @Transactional
    public List<QuestionData> getPollQuestions(int pid){
        TypedQuery<QuestionData> query = getSessionFactory().getCurrentSession().createNativeQuery(
                "(select * from questions where poll_id = :pid)", QuestionData.class).setParameter("pid", pid);
        return query.getResultList();
    }
}

