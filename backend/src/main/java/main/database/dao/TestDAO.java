package main.database.dao;

import main.database.AbstractDAO;
import main.database.dto.TestData;

public class TestDAO extends AbstractDAO<TestData> {

    private static TestDAO dao = new TestDAO();

    private TestDAO(){ super(TestData.class); }

    public static TestDAO getInstance(){
        return dao;
    }
}
