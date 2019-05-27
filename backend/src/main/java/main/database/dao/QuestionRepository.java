package main.database.dao;

import main.api.utils.ApplicationException;
import main.database.AbstractRepository;
import main.database.dto.PollData;
import main.database.dto.QuestionData;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class QuestionRepository extends AbstractRepository<QuestionData> {
    protected QuestionRepository() {
        super(QuestionData.class);
    }

    @Transactional
    @Override
    public void createItem(QuestionData data) throws ApplicationException {
        data.setOrders(this.getOrdersForNewQuestion(data.getPoll()));
        this.getSessionFactory().getCurrentSession().persist(data);
    }

    private int getOrdersForNewQuestion(PollData poll) {
        Criteria criteria = this.getSessionFactory().getCurrentSession()
                .createCriteria(QuestionData.class)
                .add(Restrictions.eq("poll", poll))
                .setProjection(Projections.max("orders"));
        Integer newOrder = (Integer) criteria.uniqueResult();
        return newOrder == null ? 0 : newOrder + 1;
    }

    public void putOn(QuestionData sourceQuestion, int newOrders) {
        int oldOrders = sourceQuestion.getOrders();

        sourceQuestion.setOrders(newOrders);
        this.modifyItem(sourceQuestion);

        PollData poll = sourceQuestion.getPoll();
        if (oldOrders > newOrders) {
            poll.getQuestions().stream()
                    .filter((question) -> question.getOrders() < oldOrders && question.getOrders() >= newOrders)
                    .forEach((question -> {
                        question.setOrders(question.getOrders() + 1);
                        this.modifyItem(question);
                    }));
        } else {
            poll.getQuestions().stream()
                    .filter((question) -> question.getOrders() > oldOrders && question.getOrders() <= newOrders)
                    .forEach((question -> {
                        question.setOrders(question.getOrders() - 1);
                        this.modifyItem(question);
                    }));
        }

    }

}

