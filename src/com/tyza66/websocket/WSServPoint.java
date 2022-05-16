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
    //set是群发的时候用的列表
    static Set<Session> set = new HashSet<>();
    //userlist是用于构建输出在用户列表的Msg对象用到的参数
    static List<String> userList = new ArrayList<String>();
    //us是用于私聊模式使用的
    static Map<Session, String> us = new HashMap<Session, String>();
    //map存的是传入的session键值对
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
        userList.add(map.get("loginName"));
        System.out.println("map:" + map);
        ms = new Msg();
        ms.setType("s");
        ms.setMsgDate(new Date());
        ms.setMsgSender("system");
        ms.setUserList(userList);
        ms.setMsgInfo(map.get("loginName") + "已上线！");
        String jsonstr = JSONObject.toJSONString(ms);
        set.add(session);
        us.put(session, map.get("loginName"));
        bordcast(set, jsonstr);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("连接已关闭！");
        userList.remove(map.get("loginName"));
        ms = new Msg();
        ms.setType("s");
        ms.setMsgDate(new Date());
        ms.setMsgSender("system");
        ms.setUserList(userList);
        ms.setMsgInfo(map.get("loginName") + "已下线！");
        String jsonstr = JSONObject.toJSONString(ms);
        set.remove(session);
        bordcast(set, jsonstr);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("信息接收！" + message);
        //ms.setMsgInfo(message);
        //String jsonstr = JSONObject.toJSONString(ms);
        ms = new Msg();
        ms.setType("p");
        ms.setMsgDate(new Date());
        ms.setMsgSender(map.get("loginName"));
        ms.setMsgInfo(message);
        String jsonstr = JSONObject.toJSONString(ms);
        bordcast(set, jsonstr);
        //session.getAsyncRemote().sendText(message);
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
        System.out.println("系统异常！msg:" + t);
    }

    public void bordcast(Set<Session> set, String message) {
        for (Session s : set) {
            s.getAsyncRemote().sendText(message);
        }
    }
}
