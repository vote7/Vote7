package main.api.resources;

import main.api.data.Test;
import main.database.dao.TestDAO;
import main.database.dto.TestData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestResource {

    @Autowired
    private TestDAO testDAO;

    @RequestMapping(value = "/test",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Test test(){
        testDAO.save(new TestData("String",30));
        System.out.println(testDAO.getAllItems().size());
        return new Test("Hello",15);
    }
}
