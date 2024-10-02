package com.sfbest.finance.chat.repositories;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelRepository {


    private static Map<String, Channel> channelMap  = new ConcurrentHashMap<>();

    public static void registoryChannel(String name,Channel channel){
        channelMap.put(name,channel);
    }

    public static Channel getChannel(String name){
        return channelMap.get(name);
    }
}
