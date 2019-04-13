package main.api.resources;

import main.api.data.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestResource {

    @RequestMapping(value = "/test",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Test test(){
        return new Test("Hello",15);
    }
}
