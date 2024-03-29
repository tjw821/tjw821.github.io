package service;

import domain.ThreadHashMap;
import qqcommon.FileMessage;
import qqcommon.Message;
import qqcommon.MessageType;

import java.io.*;

public class FileClientService {
    public void sendFileToOne(String src,String dest,String sender,String getter){
        Message message = new Message();
        message.setSender(sender);
        message.setGetter(getter);
        message.setMessType(MessageType.MESSAGE_FILE_MES);
        FileMessage fileMessage = new FileMessage();
        fileMessage.setSrc(src);
        fileMessage.setDest(dest);
        try {
            char [] fileChars=new char[(int) new File(src).length()];
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(src));
            inputStreamReader.read(fileChars);
            fileMessage.setFileChars(fileChars);
            ObjectOutputStream oos = new ObjectOutputStream(ThreadHashMap.getClientConnectServerThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
            oos.writeObject(fileMessage);
            if(inputStreamReader!=null){
                inputStreamReader.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
