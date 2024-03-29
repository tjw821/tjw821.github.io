package service;

import domain.ThreadHashMap;
import qqcommon.Message;
import qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageService {
    public void sendMessageToOne(String content,String sender,String getter){
        //˽��
        Message message=new Message();
        message.setSender(sender);
        message.setGetter(getter);
        message.setContent(content);
        message.setMessType(MessageType.MESSAGE_COMM_MES_One);
        message.setSendTime(new Date().toString());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ThreadHashMap.getClientConnectServerThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessageToAll(String content,String sender){
        //Ⱥ��

        Message message=new Message();
        message.setSender(sender);
        message.setContent(content);
        message.setGetter("all");
        message.setMessType(MessageType.MESSAGE_COMM_MES_ALL);
        message.setSendTime(new Date().toString());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ThreadHashMap.getClientConnectServerThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void notOnline(String userId)  {
        //������Ϣ�����Ƿ���������Ϣ
        Message message = new Message();
        message.setGetter(userId);
        message.setMessType(MessageType.MESSAGE_NOT_ONLINE);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(ThreadHashMap.getClientConnectServerThread(userId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
