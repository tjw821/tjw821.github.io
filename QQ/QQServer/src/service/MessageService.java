package service;

import DAO.MessageDAO;
import domain.ThreadHashMap;
import qqcommon.Message;

import java.util.List;

public class MessageService {
    private static MessageDAO messageDAO=new MessageDAO();
    public static void notOnlineMessage(Message message){
            messageDAO.update("insert into message values(?,?,?,?,?)",message.getSender(),message.getGetter(),message.getContent(),message.getSendTime(),message.getMessType());

    }

    public static List<Message> sendOntOnlineMessage(Message message){
        List<Message> messages = messageDAO.queryMulti("select * from message where getter=? or getter = 'all'", Message.class, message.getGetter());
        return messages;
    }

    public  static  void update(Message message){
        messageDAO.update("delete from message where getter=?",message.getGetter());
    }
}
