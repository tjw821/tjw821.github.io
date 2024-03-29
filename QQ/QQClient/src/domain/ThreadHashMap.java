package domain;

import java.util.HashMap;

public class ThreadHashMap {
    private static HashMap<String,ClientConnectServerThread> hm =new HashMap<>();

    public static void addClientConnectServerThread(String userId,ClientConnectServerThread ccst){
        hm.put(userId,ccst);
    }
    public static ClientConnectServerThread getClientConnectServerThread(String userId){
        return hm.get(userId);
    }
}
