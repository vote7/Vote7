package main.api.data.groups;

import main.api.data.users.UserResponse;
import main.database.dto.GroupData;
import main.database.dto.UserData;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupResponse {
    private int id;
    private String name;
    private String description;
    private Set<UserResponse> users;

    public GroupResponse(GroupData data) {
        this.id = data.getId();
        this.name = data.getName();
        this.description = data.getDescription();
        prepareUsersResponse(data.getMembers());
    }

    private void prepareUsersResponse(Set<UserData> members) {
        this.users = members.stream().map(UserResponse::new).collect(Collectors.toSet());
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

    public Set<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(Set<UserResponse> users) {
        this.users = users;
    }
}
