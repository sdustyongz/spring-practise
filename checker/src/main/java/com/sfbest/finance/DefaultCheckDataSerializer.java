package com.sfbest.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultCheckDataSerializer implements CheckDataKvSerializer<Map<String,Object>> {

    List<String> keyPropertyNames = null;
    List<String> valuePropertyNames = null;

    String propertySplitor = ",";
    public static final String KV_SPLIT = "#!@@&&&#";

    public DefaultCheckDataSerializer(List<String> keyPropertyNames, List<String> valuePropertyNames) {
        this.keyPropertyNames = keyPropertyNames;
        this.valuePropertyNames = valuePropertyNames;
        this.propertySplitor="#&&&#";
    }

    @Override
    public String serialize(Map<String,Object> data) {
        String main = keyPropertyNames.stream().map(column->data.get(column).toString()).collect(Collectors.joining(propertySplitor));
        String value = valuePropertyNames.stream().map(column->data.get(column).toString()).collect(Collectors.joining(propertySplitor));
        return  main+KV_SPLIT+value;
    }

    @Override
    public Map<String,Object> deSerialiization(String data) {
        String[] kv = data.split(KV_SPLIT);
        Map<String,Object> map = new HashMap<>();
        if(kv.length  >= 2){
            String[] keys = kv[0].split(propertySplitor);
            String[] values = kv[1].split(propertySplitor);
            for (int i = 0; i < keyPropertyNames.size(); i++) {
                if(keys.length >= i && values.length>=i){
                    map.put(keys[i],values[i]);
                }else{
                    break;
                }
            }
        }
        return map ;
    }

    @Override
    public String getKvSplitor() {
        return KV_SPLIT;
    }


}
