package main.database.dto;

import main.api.data.questions.QuestionRequest;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "QUESTIONS")
public class QuestionData {
    @Id
    @Column(name = "QUESTION_ID")
    @SequenceGenerator(name="questions_seq", sequenceName="questions_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "questions_seq")
    private int id;

    @Column
    private String content;

    @Column
    private String image;

    @Column
    private Boolean open;

    @Column
    private Boolean underway;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private Integer orders;

    @ManyToOne
    @JoinColumn(name="POLL_ID")
    private PollData poll;

    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER)
    private Set<AnswerData> answers = new HashSet<>();

    public QuestionData() {}

    public QuestionData(QuestionRequest request) {
        this.content = request.getContent();
        this.image = request.getImage();
        this.open = request.getOpen();
    }

    public void handle(QuestionRequest request) {
        if (request.getContent() != null) this.content = request.getContent();
        if (request.getImage() != null) this.image = request.getImage();
        if (request.getOpen() != null) this.open = request.getOpen();
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
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

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public PollData getPoll() {
        return poll;
    }

    public void setPoll(PollData poll) {
        this.poll = poll;
    }

    public Set<AnswerData> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<AnswerData> answers) {
        this.answers = answers;
    }

    public boolean addAnswer(AnswerData answer) {
        return this.answers.add(answer);
    }

    public boolean removeAnswer(AnswerData answer) {
        return this.answers.remove(answer);
    }

    public Boolean getUnderway() {
        return underway;
    }

    public void setUnderway(Boolean underway) {
        this.underway = underway;
    }
}
