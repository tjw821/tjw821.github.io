package domain;

import qqcommon.FileMessage;
import qqcommon.Message;
import qqcommon.MessageType;
import service.MessageService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class ServerConnectClientThread extends Thread{
    private Socket socket=null;
    private String useId;

    public ServerConnectClientThread(Socket socket, String useId) {
        this.socket = socket;
        this.useId = useId;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true){
            ObjectInputStream ois = null;

            try {
                System.out.println("服务端和客户端"+useId+"保持通信......");
                ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //返回在线用户列表
                if(message.getMessType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
                    System.out.println("=========="+message.getSender()+"要求返回在线用户列表======");
                    String onlineUser=ThreadHashMap.geiOnlineUser();
                    //返回Message
                    Message message1=new Message();
                    message1.setMessType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    message1.setContent(onlineUser);
                    message1.setGetter(message.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message1);
                }else if(message.getMessType().equals(MessageType.MESSAGE_CLTENT_EXIT)){
                    //客户端退出
                    System.out.println("========"+useId+"退出系统=========");
                    //先将线程从集合中删除
                    ThreadHashMap.removeOnlineUser(useId);
                    socket.close();
                    break;
                }else if(message.getMessType().equals(MessageType.MESSAGE_COMM_MES_One)){
                    //私聊
                    if(ThreadHashMap.getServerConnectClientThread(message.getGetter())==null){
                    MessageService.notOnlineMessage(message);
                    return;}
                    ObjectOutputStream oos = new ObjectOutputStream(ThreadHashMap.getServerConnectClientThread(message.getGetter()).getSocket().getOutputStream());
                    oos.writeObject(message);
                }else if(message.getMessType().equals(MessageType.MESSAGE_COMM_MES_ALL)){
                    //群聊
                    HashMap<String, ServerConnectClientThread> hm = ThreadHashMap.getHm();
                    ArrayList<String> user = new ArrayList<>();
                    Iterator<String> iterator = hm.keySet().iterator();
                    while(iterator.hasNext()){
                        user.add(iterator.next().toString());
                    }
                    user.remove(message.getSender());
                    for (int i = 0; i < user.size(); i++) {
                        ObjectOutputStream oos = new ObjectOutputStream(ThreadHashMap.getServerConnectClientThread(user.get(i)).getSocket().getOutputStream());
                        oos.writeObject(message);
                    }
                }else if(message.getMessType().equals(MessageType.MESSAGE_FILE_MES)){
                    FileMessage fileMessage= (FileMessage) ois.readObject();
                    ObjectOutputStream oos = new ObjectOutputStream(ThreadHashMap.getServerConnectClientThread(message.getGetter()).getSocket().getOutputStream());
                    oos.writeObject(message);
                    oos.writeObject(fileMessage);
                } else if (message.getMessType().equals(MessageType.MESSAGE_NOT_ONLINE)) {
                    //查询是否有离线消息
                    List<Message> messages = MessageService.sendOntOnlineMessage(message);
                    //删除离线消息
                    MessageService.update(message);
                    if(messages==null){
                        return;
                    }
                    for (Message message1 :messages) {
                        ObjectOutputStream oos = new ObjectOutputStream(ThreadHashMap.getServerConnectClientThread(message.getGetter()).getSocket().getOutputStream());
                        oos.writeObject(message1);
                    }

                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
