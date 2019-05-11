package main.database.dto;

import main.api.data.UserRequest;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class UserData {

    @Id
    @Column(name = "USER_ID")
    @SequenceGenerator(name="users_seq", sequenceName="users_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "users_seq")
    private int id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private Date lastLogin;

    @Column
    private boolean active;

    @ManyToMany(mappedBy = "members")
    private Set<GroupData> groups = new HashSet<>();

    @ManyToMany(mappedBy = "usersWhoAnswered")
    private Set<AnswerData> userAnswers = new HashSet<>();

    public UserData(){}

    public UserData(UserRequest request){
        this.name = request.getName();
        this.surname = request.getSurname();
        this.password = request.getPassword();
        this.email = request.getEmail();
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<GroupData> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupData> groups) {
        this.groups = groups;
    }

    public Set<AnswerData> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Set<AnswerData> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
