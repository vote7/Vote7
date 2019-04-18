package main.database.dto;

import javax.persistence.*;

@Entity
@Table(name = "TEST")
public class TestData {

    @Id
    @Column
    @SequenceGenerator(name="test_seq", sequenceName="test_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "test_seq")
    private int id;
    @Column
    private String testString;
    @Column
    private int testInt;

    public TestData(){
        this.testInt = 10;
        this.testString = "Test";
    }

    public TestData(String testString,int testInt){
        this.testString = testString;
        this.testInt = testInt;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public int getTestInt() {
        return testInt;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return testString;
    }
}