package main.database.dao;

import main.database.AbstractRepository;
import main.database.dto.AnswerData;
import main.database.dto.QuestionData;
import main.database.dto.UserData;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Repository
public class AnswerRepository extends AbstractRepository<AnswerData>  {
    protected AnswerRepository() {
        super(AnswerData.class);
    }

    @Transactional
    public List<AnswerData> getUserAnswers(int uid, int qid){
        TypedQuery<AnswerData> query = getSessionFactory()
                .getCurrentSession()
                .createNativeQuery("select * from answers where question_id = :qid and answer_id in " +
                       "(select answer_id from user_answers where user_id = :uid)", AnswerData.class)
                .setParameter("uid", uid)
                .setParameter("qid", qid);
        return query.getResultList();
    }

    @Transactional
    public AnswerData getOrGenerateAnswer(QuestionData question, String answerContent){
        AnswerData answer = getAnswer(question,answerContent);
        if (answer == null) {
            answer = new AnswerData();
            answer.setQuestion(question);
            answer.setContent(answerContent);
            getSessionFactory().getCurrentSession().persist(answer);
        }

        return answer;
    }

    @Transactional
    public AnswerData getAnswer(QuestionData question, String answerContent){
        return (AnswerData) getSessionFactory()
                .getCurrentSession()
                .createCriteria(AnswerData.class, "a")
                .createAlias("a.question", "q")
                .add(Restrictions.eq("q.id", question.getId()))
                .add(Restrictions.eq("a.content", answerContent))
                .uniqueResult();
    }

    @Transactional
    public List<AnswerData> getQuestionAnswers(int qid){
        TypedQuery<AnswerData> query = getSessionFactory()
                .getCurrentSession()
                .createNativeQuery("select * from Answers where question_id = :qid", AnswerData.class)
                .setParameter("qid", qid);
        return query.getResultList();
    }

    @Transactional
    public BigInteger countAnswerAnswers(int answerId){
        return (BigInteger) getSessionFactory()
                .getCurrentSession()
                .createNativeQuery("select count(*) from user_answers \n" +
                        "where answer_id = :aid")
                .setParameter("aid", answerId).uniqueResult();
    }

}
