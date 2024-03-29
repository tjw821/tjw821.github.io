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
    public boolean checkUser(String userId,String passwd){//����û��������Ƿ���ȷ
        boolean check=false;
        user.setUserId(userId);
        user.setPasswd(passwd);
        try {
            socket = new Socket(InetAddress.getLocalHost(), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
            //��ȡ����˷��͵�Message����
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if(ms.getMessType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){//�ж��Ƿ��½�ɹ�
                check=true;
                //��½�ɹ��ʹ���һ���ͷ���˱���ͨ�ŵ��߳�
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start();
                //���̷߳��뵽������
                ThreadHashMap.addClientConnectServerThread(userId,ccst);
            }else{
                socket.close();
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return check;
    }
    //������Ҫ�󷵻������û��б�
    public void olineFriendList(){
        Message message=new Message();
        message.setMessType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(user.getUserId());
        //�õ����û��̵߳�������������Ϣ
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
            System.out.println(user.getUserId()+"�˳�ϵͳ");
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
