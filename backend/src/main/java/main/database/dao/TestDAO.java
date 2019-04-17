package main.database.dao;

import main.database.AbstractDAO;
import main.database.dto.TestData;
import org.springframework.stereotype.Repository;

@Repository
public class TestDAO extends AbstractDAO<TestData> {

    public TestDAO(){ super(TestData.class); }

    @Override
    public int save(TestData testData) {
        return super.save(testData);
    }
}
