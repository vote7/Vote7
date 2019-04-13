package main.server;

import main.api.data.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/website")
public class TestController {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        return "test";  // <-- .jsp file name in webapp/WEB-INF/view
    }
}
