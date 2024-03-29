package qqcommon;

public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED="1";
    String MESSAGE_LOGIN_FALT="2";
    String MESSAGE_COMM_MES_One="3";//普通信息包
    String MESSAGE_GET_ONLINE_FRIEND="4";//要求返回在线用户列表
    String MESSAGE_RET_ONLINE_FRIEND="5";//返回在线用户列表
    String MESSAGE_CLTENT_EXIT="6";//客户端请求退出
    String MESSAGE_COMM_MES_ALL="7";//群发
    String MESSAGE_FILE_MES="8";//发送文件
    String MESSAGE_NOT_ONLINE="9";//离线消息

}
