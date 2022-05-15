package com.tyza66.websocket;

import org.omg.Messaging.SyncScopeHelper;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value = "/chatroom")
public class WSServPoint {
    static Set<Session> set = new HashSet<>();
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("连接创建成功！");
        set.add(session);
    }

    @OnClose
    public void onClose() {
        System.out.println("连接已关闭！");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("信息接收！"+message);
        for(Session s:set){
            s.getAsyncRemote().sendText(message);
        }
        //session.getAsyncRemote().sendText(message);
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
        System.out.println("系统异常！");
    }
}
