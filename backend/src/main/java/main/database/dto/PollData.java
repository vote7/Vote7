package main.database.dto;

import main.api.data.polls.PollRequest;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "POLLS")
public class PollData {
    @Id
    @Column(name = "POLL_ID")
    @SequenceGenerator(name="polls_seq", sequenceName="polls_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "polls_seq")
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Date date;

    @Column
    private Time startTime;

    @Column
    private Time endTime;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name="SECRETARY_ID")
    private UserData secretary;

    @ManyToOne
    @JoinColumn(name="CHAIRMAN_ID")
    private UserData chairman;

    @ManyToOne
    @JoinColumn(name="GROUP_ID")
    private GroupData group;

    @OneToMany(mappedBy = "poll",fetch = FetchType.EAGER)
    private Set<QuestionData> questions = new HashSet<>();

    public PollData() {}

    public PollData(PollRequest request, UserData secretary, UserData chairman, GroupData group){
        this.name = request.getName();
        this.description = request.getDescription();
        this.chairman = chairman;
        this.secretary = secretary;
        this.group = group;
        this.createdAt = new Date(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
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

    public UserData getSecretary() {
        return secretary;
    }

    public void setSecretary(UserData secretary) {
        this.secretary = secretary;
    }

    public UserData getChairman() {
        return chairman;
    }

    public void setChairman(UserData chairman) {
        this.chairman = chairman;
    }

    public GroupData getGroup() {
        return group;
    }

    public void setGroup(GroupData group) {
        this.group = group;
    }

    public Set<QuestionData> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionData> questions) {
        this.questions = questions;
    }

    public boolean addQuestion(QuestionData question) {
        return this.questions.add(question);
    }

    public boolean removeQuestion(QuestionData question) {
        return this.questions.remove(question);
    }
}
