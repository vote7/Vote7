package main.api.resources;

import main.api.data.SimpleResponse;
import main.api.data.users.UserRequest;
import main.api.data.users.UserResponse;
import main.api.data.groups.GroupResponse;
import main.api.data.polls.PollResponse;
import main.api.utils.ApplicationException;
import main.api.utils.ApplicationFilter;
import main.api.utils.ExceptionCode;
import main.database.dao.GroupRepository;
import main.database.dao.PollRepository;
import main.database.dao.UserRepository;
import main.database.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PollRepository pollRepository;

    @RequestMapping(value = "/register",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    TokenResponse register(@RequestBody UserRequest request) throws ApplicationException {
        UserData data = new UserData(request);
        userRepository.createItem(data);
        return new TokenResponse(ApplicationFilter.generateToken(data.getId()));
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    TokenResponse login(@RequestBody UserRequest request) throws ApplicationException {
        UserData data = userRepository.loginUser(request.getEmail(),request.getPassword());
        if(data == null)
            throw new ApplicationException(ExceptionCode.BAD_CREDENTIALS);
        return new TokenResponse(ApplicationFilter.generateToken(data.getId()));
    }

    @RequestMapping(value = "/me",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    UserResponse me(HttpServletRequest request) throws ApplicationException {
        int id = (int) request.getAttribute("userIdToken");
        return new UserResponse(userRepository.getItem(id));
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse logout(HttpServletRequest request){
        ApplicationFilter.removeToken((Integer) request.getAttribute("userIdToken"));
        return new SimpleResponse("User successfully logged out");
    }

    @RequestMapping(value = "",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<UserResponse> all() throws ApplicationException {
       List<UserResponse> users = new LinkedList<>();
       for(UserData data : userRepository.getAllItems())
           users.add(new UserResponse(data));
       return users;
    }

    @RequestMapping(value = "/{uid}/polls",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<PollResponse> userPolls(@PathVariable("uid") int userId){
        return pollRepository.getUserPolls(userId).stream().map(PollResponse::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{uid}/groups",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<GroupResponse> userGroups(@PathVariable("uid") int uid){
        return groupRepository.getUserGroups(uid).stream().map(GroupResponse::new).collect(Collectors.toList());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationException> exception(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body((ApplicationException)e);
    }

    private class TokenResponse {
        private String token;

        public TokenResponse(String token){
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
