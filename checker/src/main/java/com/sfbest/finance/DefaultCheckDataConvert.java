package com.sfbest.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  将Map类型的对象 根据 keyPropertyNames  和 valuePropertyNames的配置 处理成一行数据
 */
public class DefaultCheckDataConvert implements CheckDataConvert<Map<String,?>> {

    List<String> keyPropertyNames = null;
    List<String> valuePropertyNames = null;

    String propertySplitor;
    static final String KV_SPLIT = "!###@@&&&";

    public DefaultCheckDataConvert(List<String> mainColumns, List<String> valueColumns, String propertySplitor) {
        this.keyPropertyNames = mainColumns;
        this.valuePropertyNames = valueColumns;
        this.propertySplitor = propertySplitor;
    }


    @Override
    public String serialization(Map<String, ?> data) {
        String main = keyPropertyNames.stream().map(column->data.get(column).toString()).collect(Collectors.joining(propertySplitor));
        String value = valuePropertyNames.stream().map(column->data.get(column).toString()).collect(Collectors.joining(propertySplitor));
        return  main+KV_SPLIT+value;
    }

    @Override
    public Map<String, String> parse(String data) {
        String[] kv = data.split(KV_SPLIT);
        Map<String,String> map = new HashMap<>();
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
    public CheckDataModel convert(String data) {
        String[] kv = data.split(KV_SPLIT);
        return new CheckDataModel(kv[0],kv[1]);
    }

    @Override
    public String formatter(CheckDataModel dataModel) {
        return  dataModel.getKey()+KV_SPLIT+dataModel.getValue();
    }
}
