package com.sfbest.finance;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultCheckDataSerializer implements CheckDataKvSerializer<Map<String,Object>> {

    List<String> keyPropertyNames = null;
    List<String> valuePropertyNames = null;

    String propertySplitor = ",";
    public static final String KV_SPLIT = "#!@@&&&#";

    ConversionService conversionService;

    public DefaultCheckDataSerializer(List<String> keyPropertyNames, List<String> valuePropertyNames) {
        this.keyPropertyNames = keyPropertyNames;
        this.valuePropertyNames = valuePropertyNames;
        this.propertySplitor="#&&&#";
        conversionService = DefaultConversionService.getSharedInstance();
    }

    @Override
    public String serialize(Map<String,Object> data) {
        String main = keyPropertyNames.stream().map(column->conversionService.convert(data.get(column),String.class)).collect(Collectors.joining(propertySplitor));
        String value = valuePropertyNames.stream().map(column->conversionService.convert(data.get(column),String.class)).collect(Collectors.joining(propertySplitor));
        return  main+KV_SPLIT+value;
    }

    @Override
    public Map<String,Object> deSerialiization(String data) {
        String[] kv = data.split(KV_SPLIT);
        Map<String,Object> map = new HashMap<>();
        if(kv.length  >= 1){
            String[] keys = kv[0].split(propertySplitor);
            for (int i = 0; i < keyPropertyNames.size(); i++) {
                if(keys.length >= i){
                    map.put(keyPropertyNames.get(i),keys[i]);
                }else{
                    break;
                }
            }
        }
        if(kv.length >= 2){
            String[] values = kv[1].split(propertySplitor);
            for (int i = 0; i < valuePropertyNames.size(); i++) {
                if(values.length >= i){
                    map.put(valuePropertyNames.get(i),values[i]);
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
