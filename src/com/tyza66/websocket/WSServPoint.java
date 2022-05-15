package com.tyza66.websocket;

import com.alibaba.fastjson.JSONObject;
import com.tyza66.pojo.Msg;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@ServerEndpoint(value = "/chatroom")
public class WSServPoint {
    static Set<Session> set = new HashSet<>();
    Map<String, String> map;
    private Msg ms;

    @OnOpen
    public void onOpen(Session session) throws UnsupportedEncodingException {
        System.out.println("连接创建成功！");
        String msg = session.getQueryString();
        msg = URLDecoder.decode(msg, "utf-8");
        System.out.println("msg:" + msg);
        map = new HashMap<String, String>();
        if (msg.contains("\\&")) {
            String[] sts = msg.split("\\&");
            for (String str : sts) {
                String[] strs = str.split("=");
                map.put(strs[0], strs[1]);
            }
        } else {
            String[] strs = msg.split("=");
            map.put(strs[0], strs[1]);
        }
        System.out.println("map:" + map);
        ms = new Msg();
        ms.setMsgSender(map.get("loginName"));
        ms.setMsgDate(new Date());
        set.add(session);
    }

    @OnClose
    public void onClose() {
        System.out.println("连接已关闭！");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("信息接收！" + message);
        ms.setMsgInfo(message);
        String jsonstr =JSONObject.toJSONString(ms);
        bordcast(set, jsonstr);
        //session.getAsyncRemote().sendText(message);
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
        System.out.println("系统异常！msg:"+t);
    }

    public void bordcast(Set<Session> set, String message) {
        for (Session s : set) {
            s.getAsyncRemote().sendText(message);
        }
    }
}
