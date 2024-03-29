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
                System.out.println("�ͻ��˵ȴ����ܴӷ���˷�������Ϣ");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //���ܲ���Message���߳̾ͻ�����������
                Message ms = (Message) ois.readObject();
                if(ms.getMessType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    System.out.println("==========��ǰ�����û��б����£�==============");
                    String[] split = ms.getContent().split(" ");
                    for (String s :split) {
                        System.out.println(s);
                    }

                }else if ((ms.getMessType().equals(MessageType.MESSAGE_COMM_MES_One))){
                    System.out.println("ʱ�䣺"+ms.getSendTime()+"\n"+ms.getSender()+"�����˵��"+ms.getContent());
                }else if(ms.getMessType().equals(MessageType.MESSAGE_COMM_MES_ALL)){
                    System.out.println("ʱ�䣺"+ms.getSendTime()+"\n"+ms.getSender()+"��Դ��˵��"+ms.getContent());
                }else if(ms.getMessType().equals(MessageType.MESSAGE_FILE_MES)){
                    FileMessage fileMessage= (FileMessage) ois.readObject();
                    System.out.println("\n"+ms.getSender()+"��"+ms.getGetter()+"�����ļ�:"+fileMessage.getSrc()+"���ҵĵ��ԣ�"+fileMessage.getDest());
                    try {
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileMessage.getDest()));
                        outputStreamWriter.write(fileMessage.getFileChars());
                        outputStreamWriter.close();
                        System.out.println("�ļ��������");
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
     * ��ȡ
     * @return socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * ����
     * @param socket
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String toString() {
        return "ClientConnectServerThread{socket = " + socket + "}";
    }
}
