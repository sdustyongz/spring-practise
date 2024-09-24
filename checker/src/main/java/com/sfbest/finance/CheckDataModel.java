package com.sfbest.finance;

public class CheckDataModel implements  Comparable{

    String key;
    String value;

    public CheckDataModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }


    @Override
    public int compareTo(Object other) {
        if(other == null){
            return 1;
        }
        if(other instanceof CheckDataModel){
            CheckDataModel otherModel = (CheckDataModel)other;
            return key.compareTo(otherModel.getKey());
        }
        return 1;
    }
}
