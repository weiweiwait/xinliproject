package com.qing.admin.system.service.websocketservice;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.qing.admin.system.entity.ConditionException;
import com.qing.admin.system.entity.Message;
import com.qing.admin.system.service.MessageService;
import com.qing.admin.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


@Component
@ServerEndpoint("/chat/{role}/{token}")
public class WebSocketService {

    public static final ConcurrentHashMap<String, WebSocketService> WEBSOCKET_MAP = new ConcurrentHashMap<>();

    private Integer userId;

    private Session session;

    private String role;

    private static MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        WebSocketService.messageService = messageService;
    }

    @OnOpen
    public void openConnection(Session session, @PathParam("role") String role, @PathParam("token") String token) {
        try {
            this.userId = TokenUtil.verifyToken(token);
            this.role = role;
        } catch (Exception ignored) {}
        this.session = session;
        String key = role + userId;
        if(WEBSOCKET_MAP.containsKey(key)) {
            WEBSOCKET_MAP.remove(key);
            WEBSOCKET_MAP.put(key, this);
        } else {
            WEBSOCKET_MAP.put(key, this);
        }
        System.out.println("连接成功");
    }

    @OnClose
    public void closeConnection() {
        String key = role + userId;
        if(WEBSOCKET_MAP.containsKey(key)) {
            WEBSOCKET_MAP.remove(key);
            System.out.println("连接关闭");
        }
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        if(!StringUtils.isNullOrEmpty(message)) {
            sendMessage(message);
            System.out.println("发送消息：" + message);
        }
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        if(StringUtils.isNullOrEmpty(message)) {
            throw new ConditionException("消息不存在！");
        }
        Message object = JSONObject.parseObject(message, Message.class);
        object.setCreateTime(new Date());
        if(role.equals("student")) {
            object.setFrom(1);
            String key = "teacher" + object.getTeacherId();
            WEBSOCKET_MAP.get(key).session.getBasicRemote().sendText(object.getMessage());
        } else {
            object.setFrom(0);
            String key = "student" + object.getStudentId();
            WEBSOCKET_MAP.get(key).session.getBasicRemote().sendText(object.getMessage());
        }
        System.out.println(object);
        messageService.save(object);
    }

    public Session getSession() {
        return session;
    }
}
