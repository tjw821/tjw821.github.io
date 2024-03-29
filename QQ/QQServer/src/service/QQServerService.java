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
            System.out.println("�������9999�˿ڼ���.......");
            new ServerNewsToAllService().start();
            ss=new ServerSocket(9999);
            while (true) {//��ͻ����Ӻ�������ּ�������ClientConnectServerThread�е�run����һ��
                Socket socket = ss.accept();
                //�������Կͻ��˵�user
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User u = (User) ois.readObject();
                //����һ��Message��׼�����ͻ��˷���
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message message = new Message();
                //��֤�û��������Ƿ���ȷ
                if(userService.getUser(u.getUserId(),u.getPasswd())){
                    //���ͻ��˷���Message
                    message.setMessType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);
                    //���ӳɹ�������һ���̱߳�������
                    ServerConnectClientThread scct = new ServerConnectClientThread(socket, u.getUserId());
                    scct.start();
                    ThreadHashMap.addServerConnectClientThread(u.getUserId(),scct);
                }else{
                    System.out.println("�û� "+u.getUserId()+"���� "+u.getPasswd()+"��½ʧ��");
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
