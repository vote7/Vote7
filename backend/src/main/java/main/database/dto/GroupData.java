package main.database.dto;


import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "GROUPS")
public class GroupData {
    @Id
    @Column(name = "GROUP_ID")
    @SequenceGenerator(name="groups_seq", sequenceName="groups_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "groups_seq")
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name="ADMIN_ID")
    private UserData admin;

    @ManyToMany
    @JoinTable(
            name="GROUP_MEMBERS",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id")})
    private Set<UserData> members = new HashSet<>();

    @OneToMany(mappedBy = "group")
    private Set<PollData> polls = new HashSet<>();

    public GroupData() {

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

    public UserData getAdmin() {
        return admin;
    }

    public void setAdmin(UserData admin) {
        this.admin = admin;
    }

    public Set<UserData> getMembers() {
        return members;
    }

    public void setMembers(Set<UserData> members) {
        this.members = members;
    }

    public boolean addMember(UserData member) {
        return this.members.add(member);
    }

    public boolean removeMember(UserData member) {
        return this.members.remove(member);
    }

    public Set<PollData> getPolls() {
        return polls;
    }

    public void setPolls(Set<PollData> polls) {
        this.polls = polls;
    }
}
