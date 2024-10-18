package com.sfbest.finance.chat.serialization;

import com.alibaba.fastjson2.JSONObject;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class FastJsonSerialiaztion implements Serialization{



    @Override
    public byte[] serial(Object obj) {
        String json = JSONObject.toJSONString(obj);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserial(byte[] buf,Class<T> cls) {
        String json = new String(buf,StandardCharsets.UTF_8);
        return (T)JSONObject.parseObject(json,cls);
    }


}
