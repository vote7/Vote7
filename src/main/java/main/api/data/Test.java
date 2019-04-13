package main.api.data;

public class Test {

    private String testString;
    private int testInt;

    public Test(){}

    public Test(String testString,int testInt){
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
}
