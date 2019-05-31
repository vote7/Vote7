package main.database.dto;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ANSWERS")
public class AnswerData {
    @Id
    @Column(name = "ANSWER_ID")
    @SequenceGenerator(name="answers_seq", sequenceName="answers_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "answers_seq")
    private int id;

    @Column
    private String content;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name="QUESTION_ID")
    private QuestionData question;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="USER_ANSWERS",
            joinColumns = { @JoinColumn(name = "answer_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id")})
    private Set<UserData> usersWhoAnswered = new HashSet<>();

    public AnswerData() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public QuestionData getQuestion() {
        return question;
    }

    public void setQuestion(QuestionData question) {
        this.question = question;
    }

    public Set<UserData> getUsersWhoAnswered() {
        return usersWhoAnswered;
    }

    public void setUsersWhoAnswered(Set<UserData> usersWhoAnswered) {
        this.usersWhoAnswered = usersWhoAnswered;
    }

    public void addUserWhoAnswered(UserData user) {
        this.usersWhoAnswered.add(user);
    }

    public boolean hasUserAnswered(UserData user) {
        return this.usersWhoAnswered.contains(user);
    }
}
