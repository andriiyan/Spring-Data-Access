package com.github.andriiyan.spring_data_access.impl.utils.file.serializer;

import java.io.Serializable;

class DummyObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private int testInt;
    private String testString;
    private boolean testBoolean;

    public DummyObject(int testInt, String testString, boolean testBoolean) {
        this.testInt = testInt;
        this.testString = testString;
        this.testBoolean = testBoolean;
    }

    public int getTestInt() {
        return testInt;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public boolean isTestBoolean() {
        return testBoolean;
    }

    public void setTestBoolean(boolean testBoolean) {
        this.testBoolean = testBoolean;
    }
}
