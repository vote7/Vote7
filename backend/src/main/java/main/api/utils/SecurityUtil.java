package main.api.utils;

import main.database.dao.UserRepository;
import main.database.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityUtil {
    @Autowired
    UserRepository userRepository;

    public UserData getLoggedInUser(HttpServletRequest request) throws ApplicationException {
        Integer userId = (Integer) request.getAttribute("userIdToken");
        return userRepository.getItem(userId);
    }
}
