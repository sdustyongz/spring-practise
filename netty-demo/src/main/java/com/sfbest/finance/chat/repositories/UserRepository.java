package com.sfbest.finance.chat.repositories;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {

    static Map<String,String> userMap = new ConcurrentHashMap<>();

    static {
        userMap.put("zhangsan","123");
        userMap.put("lisi","123");
        userMap.put("wangwu","123");
        userMap.put("zhaoliu","123");
    }

    public static boolean validateUser(String userName,String passwd){
        String pass = userMap.get(userName);
        return passwd.equals(pass);
    }
}
