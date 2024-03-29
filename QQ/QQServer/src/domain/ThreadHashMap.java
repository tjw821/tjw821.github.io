package domain;

import java.util.HashMap;
import java.util.Iterator;

public class ThreadHashMap {
    private static HashMap<String,ServerConnectClientThread>hm=new HashMap<>();

    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }

    public static void addServerConnectClientThread(String userId, ServerConnectClientThread serverConnectClientThread){
        hm.put(userId,serverConnectClientThread);
    }
    public static ServerConnectClientThread getServerConnectClientThread(String userId){
       return  hm.get(userId);
    }

    public static String geiOnlineUser(){
        Iterator<String> iterator = hm.keySet().iterator();
        String user ="";
        while(iterator.hasNext()){
            user+="”√ªß£∫"+iterator.next().toString()+" ";
        }
        return user;
    }
    public  static void removeOnlineUser(String userId){
        hm.remove(userId);
    }
}
