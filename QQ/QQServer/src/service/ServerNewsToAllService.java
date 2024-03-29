package service;

import domain.ServerConnectClientThread;
import domain.ThreadHashMap;
import qqcommon.Message;
import qqcommon.MessageType;
import utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;

public class ServerNewsToAllService extends Thread{
    @Override
    public void run() {
        while(true){
            System.out.println("请输入服务器要推送的新闻/消息[输入exit表示退出]：");
            String news= Utility.readString(100);
            if(news.equals("exit")){
                break;
            }
            Message message = new Message();
            message.setSender("服务端");
            message.setMessType(MessageType.MESSAGE_COMM_MES_ALL);
            message.setContent(news);
            message.setGetter("all");
            HashMap<String, ServerConnectClientThread> hm = ThreadHashMap.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while(iterator.hasNext()){
                String next = iterator.next();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(ThreadHashMap.getServerConnectClientThread(next).getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
