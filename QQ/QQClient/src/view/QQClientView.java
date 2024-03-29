package view;

import qqcommon.FileMessage;
import service.FileClientService;
import service.MessageService;
import service.UserClientService;
import sun.plugin2.message.Message;
import utils.Utility;

public class QQClientView {
    private static boolean loop=true;
    private String key="";
    private UserClientService userClientService=new UserClientService();
    private MessageService messageService=new MessageService();
    private FileClientService fileClientService=new FileClientService();

    public void enter(){//登录系统
        System.out.print("\n请输入用户名：");
        String userId=Utility.readString(50);
        System.out.print("\n请输入密 码：");
        String passwd=Utility.readString(25);
        if(userClientService.checkUser(userId,passwd)){
            messageService.notOnline(userId);
            secondMenu(userId);
        }else {
            System.out.print("\n=========账号或者密码错误===========");
            return;
        }
    }

    public void secondMenu(String useId){//二级菜单
        while(loop){
            System.out.println("\n============网络通信系统二级菜单=============");
            System.out.println("\t\t1 显示在线用户列表");
            System.out.println("\t\t2 群发消息");
            System.out.println("\t\t3 私发消息");
            System.out.println("\t\t4 发送文件");
            System.out.println("\t\t9 退出系统");
            System.out.print("请输入你的选择：");
            key=Utility.readString(1);
            switch (key){
                case"1":
                    userClientService.olineFriendList();
                    break;
                case"2":
                    System.out.print("\n请输入你想说的话：");
                    String content1=Utility.readString(100);
                    System.out.println("\n"+useId+"想对大家说："+content1);
                    messageService.sendMessageToAll(content1,useId);
                    break;
                case"3":
                    System.out.print("请输入想聊天的用户名（在线）：");
                    String gettetId= Utility.readString(50);
                    System.out.print("\n请输入你想说的话：");
                    String content=Utility.readString(100);
                    System.out.println("\n"+useId+"想对"+gettetId+"说："+content);
                    messageService.sendMessageToOne(content,useId,gettetId);
                    break;
                case"4":
                    System.out.print("\n你要把文件发送给谁：");
                    String getter=Utility.readString(50);
                    System.out.print("\n你要发送的文件地址：(D:\\xx.jpg)");
                    String src=Utility.readString(50);
                    System.out.print("\n你要发送到哪里：(E:\\xx.jpg)");
                    String dest=Utility.readString(50);
                    fileClientService.sendFileToOne(src,dest,useId,getter);
                    break;
                case"9":
                    userClientService.logout();
                    loop=false;
                    break;
                default:
                    System.out.println("\n请重新输入");
            }
        }

    }

    public static void main(String[] args) {
        new QQClientView().mainMenu();
    }


    private void mainMenu(){
        while(loop){
            System.out.println("==========欢迎登录网络通信系统============");
            System.out.println("\t\t1 登录系统");
            System.out.println("\t\t9 退出系统");
            System.out.print("请输入你的选择：");
            key= Utility.readString(1);
            switch (key){
                case "1":
                    enter();
                    break;
                case "9":
                    loop=false;
                    break;
                default:
                    System.out.println("\n输入错误，请重新输入");
            }
            System.out.println("\n==========退出系统===========");

        }
    }
}
