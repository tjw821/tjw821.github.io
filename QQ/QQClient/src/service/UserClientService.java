package service;

import domain.ClientConnectServerThread;
import domain.ThreadHashMap;
import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;
import utils.Utility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {

    private User user=new User();
    private Socket socket;
    public boolean checkUser(String userId,String passwd){//检查用户和密码是否正确
        boolean check=false;
        user.setUserId(userId);
        user.setPasswd(passwd);
        try {
            socket = new Socket(InetAddress.getLocalHost(), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
            //读取服务端发送的Message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if(ms.getMessType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){//判断是否登陆成功
                check=true;
                //登陆成功就创建一个和服务端保持通信的线程
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start();
                //将线程放入到集合中
                ThreadHashMap.addClientConnectServerThread(userId,ccst);
            }else{
                socket.close();
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return check;
    }
    //向服务端要求返回在线用户列表
    public void olineFriendList(){
        Message message=new Message();
        message.setMessType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(user.getUserId());
        //得到该用户线程的输入流发送消息
        try {
            ClientConnectServerThread clientConnectServerThread = ThreadHashMap.getClientConnectServerThread(user.getUserId());
            Socket socket1 = clientConnectServerThread.getSocket();
            ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public void logout(){
        Message message=new Message();
        message.setMessType(MessageType.MESSAGE_CLTENT_EXIT);
        message.setSender(user.getUserId());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ThreadHashMap.getClientConnectServerThread(user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println(user.getUserId()+"退出系统");
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
