package com.sfbest.finance;

public class KvDataModel implements  Comparable{

    String key;
    String value;

    public KvDataModel(String key, String value) {
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
        if(other instanceof KvDataModel){
            KvDataModel otherModel = (KvDataModel)other;
            return key.compareTo(otherModel.getKey());
        }
        return 1;
    }




    public static KvDataModel convert(String data,String kvSplitorText) {
        String[] kv = data.split(kvSplitorText);
        return new KvDataModel(kv[0],kv[1]);
    }


    public static String formatter(KvDataModel dataModel,String kvSplitorText) {
        return  dataModel.getKey()+kvSplitorText+dataModel.getValue();
    }

    @Override
    public String toString() {
        return "KvDataModel{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
