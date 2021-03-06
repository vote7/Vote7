package main.api.resources;

import main.api.data.SimpleResponse;
import main.api.data.groups.GroupRequest;
import main.api.data.groups.GroupResponse;
import main.api.data.polls.PollRequest;
import main.api.data.polls.PollResponse;
import main.api.data.questions.QuestionResponse;
import main.api.utils.ApplicationException;
import main.api.utils.ExceptionCode;
import main.api.utils.SecurityUtil;
import main.database.dao.GroupRepository;
import main.database.dao.PollRepository;
import main.database.dao.UserRepository;
import main.database.dto.GroupData;
import main.database.dto.PollData;
import main.database.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/groups")
public class GroupResource {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse create(@RequestBody GroupRequest request, HttpServletRequest request_http) throws ApplicationException {
        GroupData data = new GroupData(request);
        UserData loggedInUser = securityUtil.getLoggedInUser(request_http);
        data.setAdmin(userRepository.getItem(loggedInUser.getId()));
        groupRepository.createItem(data);
        String response = String.format("Group successfully created");
        return new SimpleResponse(response);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<GroupResponse> all() throws ApplicationException {
        List<GroupResponse> groups = new LinkedList<>();
        for(GroupData data : groupRepository.getAllItems())
            groups.add(new GroupResponse(data));
        return groups;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse delete(@PathVariable("id") int id) throws ApplicationException {
        groupRepository.removeItem(id);
        String response = String.format("Group %d successfully deleted", id);
        return new SimpleResponse(response);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    GroupResponse get(@PathVariable("id") int id) throws ApplicationException {
        return new GroupResponse(groupRepository.getItem(id));
    }

    @RequestMapping(value = "{gid}/add/{uid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse addUser(@PathVariable("gid") int gid, @PathVariable("uid") int uid) throws ApplicationException {
        UserData user = userRepository.getItem(uid);
        GroupData group = groupRepository.getItem(gid);

        if(false) //TODO warunek czy uzytkonwnik juz jest dodany do grupy
            throw new ApplicationException(ExceptionCode.USER_EXISTING);

        group.addMember(user);
        groupRepository.modifyItem(group);
        String response = String.format("User %d successfully added to group %d", uid, gid);
        return new SimpleResponse(response);
    }

    @RequestMapping(value = "{gid}/remove/{uid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse removeUser(@PathVariable("gid") int gid, @PathVariable("uid") int uid) throws ApplicationException {
        UserData user = userRepository.getItem(uid);
        GroupData group = groupRepository.getItem(gid);
        String response = String.format("User %d successfully removed from group %d", uid, gid);
        group.removeMember(user);
        groupRepository.modifyItem(group);
        return new SimpleResponse(response);
    }

    // CAUTION - poll without s
    @RequestMapping(value = "/{gid}/poll",method = RequestMethod.POST
            ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse createPoll(@PathVariable("gid") int groupId,@RequestBody PollRequest request) throws ApplicationException {
        UserData chairman = userRepository.getItem(request.getChairmanId());
        UserData secretary = userRepository.getItem(request.getSecretaryId());
        GroupData group = groupRepository.getItem(groupId);
        PollData poll = new PollData(request,secretary,chairman,group);
        pollRepository.createItem(poll);
        return new SimpleResponse("Poll successfully created");
    }

    // CAUTION - polls with s
    @RequestMapping(value = "/{gid}/polls",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<PollResponse> getPolls(@PathVariable("gid") int groupId) throws ApplicationException {
        GroupData data = groupRepository.getItem(groupId);
        return data.getPolls().stream().map(PollResponse::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/admin/{uid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<GroupResponse> getAdminGroups(@PathVariable("uid") int userId) throws ApplicationException {
        return groupRepository
                .getAdminGroups(userId)
                .stream()
                .map(GroupResponse::new)
                .collect(Collectors.toList());
    }
}
