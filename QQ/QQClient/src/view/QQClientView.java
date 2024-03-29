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

    public void enter(){//��¼ϵͳ
        System.out.print("\n�������û�����");
        String userId=Utility.readString(50);
        System.out.print("\n�������� �룺");
        String passwd=Utility.readString(25);
        if(userClientService.checkUser(userId,passwd)){
            messageService.notOnline(userId);
            secondMenu(userId);
        }else {
            System.out.print("\n=========�˺Ż����������===========");
            return;
        }
    }

    public void secondMenu(String useId){//�����˵�
        while(loop){
            System.out.println("\n============����ͨ��ϵͳ�����˵�=============");
            System.out.println("\t\t1 ��ʾ�����û��б�");
            System.out.println("\t\t2 Ⱥ����Ϣ");
            System.out.println("\t\t3 ˽����Ϣ");
            System.out.println("\t\t4 �����ļ�");
            System.out.println("\t\t9 �˳�ϵͳ");
            System.out.print("���������ѡ��");
            key=Utility.readString(1);
            switch (key){
                case"1":
                    userClientService.olineFriendList();
                    break;
                case"2":
                    System.out.print("\n����������˵�Ļ���");
                    String content1=Utility.readString(100);
                    System.out.println("\n"+useId+"��Դ��˵��"+content1);
                    messageService.sendMessageToAll(content1,useId);
                    break;
                case"3":
                    System.out.print("��������������û��������ߣ���");
                    String gettetId= Utility.readString(50);
                    System.out.print("\n����������˵�Ļ���");
                    String content=Utility.readString(100);
                    System.out.println("\n"+useId+"���"+gettetId+"˵��"+content);
                    messageService.sendMessageToOne(content,useId,gettetId);
                    break;
                case"4":
                    System.out.print("\n��Ҫ���ļ����͸�˭��");
                    String getter=Utility.readString(50);
                    System.out.print("\n��Ҫ���͵��ļ���ַ��(D:\\xx.jpg)");
                    String src=Utility.readString(50);
                    System.out.print("\n��Ҫ���͵����(E:\\xx.jpg)");
                    String dest=Utility.readString(50);
                    fileClientService.sendFileToOne(src,dest,useId,getter);
                    break;
                case"9":
                    userClientService.logout();
                    loop=false;
                    break;
                default:
                    System.out.println("\n����������");
            }
        }

    }

    public static void main(String[] args) {
        new QQClientView().mainMenu();
    }


    private void mainMenu(){
        while(loop){
            System.out.println("==========��ӭ��¼����ͨ��ϵͳ============");
            System.out.println("\t\t1 ��¼ϵͳ");
            System.out.println("\t\t9 �˳�ϵͳ");
            System.out.print("���������ѡ��");
            key= Utility.readString(1);
            switch (key){
                case "1":
                    enter();
                    break;
                case "9":
                    loop=false;
                    break;
                default:
                    System.out.println("\n�����������������");
            }
            System.out.println("\n==========�˳�ϵͳ===========");

        }
    }
}
