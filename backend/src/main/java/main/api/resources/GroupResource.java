package main.api.resources;

import main.api.data.SimpleResponse;
import main.api.data.groups.GroupRequest;
import main.api.data.groups.GroupResponse;
import main.api.utils.ApplicationException;
import main.api.utils.ApplicationFilter;
import main.database.dao.GroupRepository;
import main.database.dao.UserRepository;
import main.database.dto.GroupData;
import main.database.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupResource {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    void create(@RequestBody GroupRequest request) throws ApplicationException {
        GroupData data = new GroupData(request);
        groupRepository.createItem(data);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<GroupResponse> all() throws ApplicationException {
        List<GroupResponse> groups = new LinkedList<>();
        for(GroupData data : groupRepository.getAllItems())
            groups.add(new GroupResponse(data));
        return groups;
    }

    @RequestMapping(value = "", params = "id", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse delete(@RequestParam("id") int id) throws ApplicationException {
        groupRepository.removeItem(id);
        String response = String.format("Group %d successfully deleted", id);
        return new SimpleResponse(response);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    GroupResponse get(@PathVariable("id") int id) throws ApplicationException {
        groupRepository.getItem(id);
        return new GroupResponse(groupRepository.getItem(id));
    }

    @RequestMapping(value = "{gid}/add/{uid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse addUser(@PathVariable("gid") int gid, @PathVariable("uid") int uid) throws ApplicationException {
        UserData user = userRepository.getItem(uid);
        GroupData group = groupRepository.getItem(gid);
        group.addMember(user);
        //TODO /\ na pewno działa?
        String response = String.format("User %d successfully added to group %d", uid, gid);
        return new SimpleResponse(response);
    }

    @RequestMapping(value = "{gid}/remove/{uid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse removeUser(@PathVariable("gid") int gid, @PathVariable("uid") int uid) throws ApplicationException {
        UserData user = userRepository.getItem(uid);
        GroupData group = groupRepository.getItem(gid);
        group.removeMember(user);
        //TODO /\ na pewno działa?
        String response = String.format("User %d successfully removed from group %d", uid, gid);
        return new SimpleResponse(response);
    }
}
