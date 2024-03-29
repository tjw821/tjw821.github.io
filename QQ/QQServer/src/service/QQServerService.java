package service;

import domain.ServerConnectClientThread;
import domain.ThreadHashMap;
import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class QQServerService {
    private ServerSocket ss =null;
    private UserService userService=new UserService();

    public QQServerService() {
        try {
            System.out.println("服务端在9999端口监听.......");
            new ServerNewsToAllService().start();
            ss=new ServerSocket(9999);
            while (true) {//与客户连接后持续保持监听，和ClientConnectServerThread中的run方法一样
                Socket socket = ss.accept();
                //接受来自客户端的user
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User u = (User) ois.readObject();
                //定义一个Message，准备给客户端发送
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message message = new Message();
                //验证用户和密码是否正确
                if(userService.getUser(u.getUserId(),u.getPasswd())){
                    //给客户端发送Message
                    message.setMessType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);
                    //连接成功，创建一个线程保持连接
                    ServerConnectClientThread scct = new ServerConnectClientThread(socket, u.getUserId());
                    scct.start();
                    ThreadHashMap.addServerConnectClientThread(u.getUserId(),scct);
                }else{
                    System.out.println("用户 "+u.getUserId()+"密码 "+u.getPasswd()+"登陆失败");
                    message.setMessType(MessageType.MESSAGE_LOGIN_FALT);
                    oos.writeObject(message);
                    socket.close();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
