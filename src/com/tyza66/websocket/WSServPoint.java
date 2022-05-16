package com.tyza66.websocket;

import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.util.ArrayUtil;
import com.tyza66.pojo.Msg;
import org.apache.commons.lang3.ArrayUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.*;

@ServerEndpoint(value = "/chatroom")
public class WSServPoint {
    //set是群发的时候用的列表
    //static Set<Session> set = new HashSet<>();
    //userlist是用于构建输出在用户列表的Msg对象用到的参数
    //static List<String> userList = new ArrayList<String>();
    //us是用于私聊模式使用的 优化代码
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
        //userList.add(map.get("loginName"));
        System.out.println("map:" + map);
        us.put(session, map.get("loginName"));
        ms = new Msg();
        ms.setType("s");
        ms.setMsgDate(new Date());
        ms.setMsgSender("system");
        //ms.setUserList(userList);
        ms.setUserList(new ArrayList<String>(us.values()));
        ms.setMsgInfo(map.get("loginName") + "已上线！");
        String jsonstr = JSONObject.toJSONString(ms);
        //set.add(session);
        bordcast(us.keySet(), jsonstr);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("连接已关闭！");
        //userList.remove(map.get("loginName"));
        us.remove(session);
        ms = new Msg();
        ms.setType("s");
        ms.setMsgDate(new Date());
        ms.setMsgSender("system");
        ms.setUserList(new ArrayList<String>(us.values()));
        ms.setMsgInfo(map.get("loginName") + "已下线！");
        String jsonstr = JSONObject.toJSONString(ms);
        //set.remove(session);
        bordcast(us.keySet(), jsonstr);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("信息接收！" + message);
        ms = new Msg();
        ms.setType("p");
        ms.setMsgDate(new Date());
        ms.setMsgSender(map.get("loginName"));
        if (message.startsWith("@") && message.contains(":")) {
            String reivName = message.substring(message.indexOf("@") + 1, message.indexOf(":"));
            if (us.containsValue(reivName)) {
                for (Map.Entry<Session, String> e : us.entrySet()) {
                    if (reivName.equals(e.getValue())) {
                        Session reivSession = e.getKey();
                        message = message.substring(message.indexOf(":") + 1);
                        ms.setMsgInfo(message);
                        ms.setMsgReceiver(reivName);
                        String jsonstr = JSONObject.toJSONString(ms);
                        Set<Session> hashSet = new HashSet<Session>();
                        hashSet.add(reivSession);
                        hashSet.add(session);
                        bordcast(hashSet, jsonstr);
                        break;
                    }
                }
            }
        } else {
            //ms.setMsgInfo(message);
            //String jsonstr = JSONObject.toJSONString(ms);
            ms.setMsgInfo(message);
            String jsonstr = JSONObject.toJSONString(ms);
            bordcast(us.keySet(), jsonstr);
            //session.getAsyncRemote().sendText(message);
        }
    }

    byte[] bc = null;
    @OnMessage
    public void onMessage(byte[] input, Session session, boolean flag) {
        if (!flag) {
            //System.out.println(input.length + "||" + flag);
            bc =  ArrayUtils.addAll(bc,input);
        } else {
            //System.out.println(input.length + "||" + flag + "%%%%");
            bc =  ArrayUtils.addAll(bc,input);
            ByteBuffer bb = ByteBuffer.wrap(bc);
            bordcast(us.keySet(),bb);
            bc = null;
        }
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

    public void bordcast(Set<Session> set, ByteBuffer bb) {
        for (Session s : set) {
            s.getAsyncRemote().sendBinary(bb);
        }
    }
}
