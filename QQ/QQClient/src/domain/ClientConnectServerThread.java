package domain;

import qqcommon.FileMessage;
import qqcommon.Message;
import qqcommon.MessageType;

import java.io.*;
import java.net.Socket;

public class ClientConnectServerThread extends Thread{
    private Socket socket;

    @Override
    public void run() {
        while(true){
            try {
                System.out.println("客户端等待接受从服务端发来的消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //接受不到Message，线程就会阻塞在这里
                Message ms = (Message) ois.readObject();
                if(ms.getMessType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    System.out.println("==========当前在线用户列表如下：==============");
                    String[] split = ms.getContent().split(" ");
                    for (String s :split) {
                        System.out.println(s);
                    }

                }else if ((ms.getMessType().equals(MessageType.MESSAGE_COMM_MES_One))){
                    System.out.println("时间："+ms.getSendTime()+"\n"+ms.getSender()+"想对你说："+ms.getContent());
                }else if(ms.getMessType().equals(MessageType.MESSAGE_COMM_MES_ALL)){
                    System.out.println("时间："+ms.getSendTime()+"\n"+ms.getSender()+"想对大家说："+ms.getContent());
                }else if(ms.getMessType().equals(MessageType.MESSAGE_FILE_MES)){
                    FileMessage fileMessage= (FileMessage) ois.readObject();
                    System.out.println("\n"+ms.getSender()+"给"+ms.getGetter()+"发送文件:"+fileMessage.getSrc()+"到我的电脑："+fileMessage.getDest());
                    try {
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileMessage.getDest()));
                        outputStreamWriter.write(fileMessage.getFileChars());
                        outputStreamWriter.close();
                        System.out.println("文件保存完毕");
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public ClientConnectServerThread() {
    }

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * 获取
     * @return socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * 设置
     * @param socket
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String toString() {
        return "ClientConnectServerThread{socket = " + socket + "}";
    }
}
