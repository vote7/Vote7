package main.api.resources;

import main.api.data.UserRequest;
import main.api.data.UserResponse;
import main.api.utils.ApplicationException;
import main.api.utils.ApplicationFilter;
import main.api.utils.ExceptionCode;
import main.database.dao.UserRepository;
import main.database.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/register",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    TokenResponse register(@RequestBody UserRequest request) {
        UserData data = new UserData(request);
        userRepository.createItem(data);
        return new TokenResponse(ApplicationFilter.generateToken(data.getId()));
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    TokenResponse login(@RequestBody UserRequest request){
        UserData data = userRepository.getItem(request.getEmail());
        if(data == null)
            throw new ApplicationException(ExceptionCode.BAD_CREDENTIALS);
        return new TokenResponse(ApplicationFilter.generateToken(data.getId()));
    }

    @RequestMapping(value = "/me",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    UserResponse me(HttpServletRequest request){
        int id = (int) request.getAttribute("userIdToken");
        return new UserResponse(userRepository.getItem(id));
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String logout(HttpServletRequest request){
        ApplicationFilter.removeToken((Integer) request.getAttribute("userIdToken"));
        return "User successfully logged out";
    }

    @RequestMapping(value = "",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<UserResponse> all(){
       List<UserResponse> users = new LinkedList<>();
       for(UserData data : userRepository.getAllItems())
           users.add(new UserResponse(data));
       return users;
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
