package view;

import domain.*;
import org.junit.Test;
import service.BillService;
import service.DiningTableService;
import service.EmployeeService;
import service.MenuService;
import utils.Utility;

import java.util.List;


public class MHLView {
    private static boolean loop = true;
    private String key = "";
    private EmployeeService employeeService=new EmployeeService();
    private DiningTableService diningTableService=new DiningTableService();
    private MenuService menuService=new MenuService();

    private BillService billService=new BillService();

    public static void main(String[] args) {
        new MHLView().mainMenu();
    }

    public void listDiningTable(){
        List<DiningTable> diningTAble = diningTableService.getDiningTAble();
        System.out.println("\n�������\t\t����״̬");
        for (DiningTable o :diningTAble) {
            System.out.println(o);

        }
        System.out.println("==============��ʾ���============");

    }

    public void orderDiningTable(){
        System.out.println("\n===============Ԥ������==============");
        System.out.print("��ѡ��ҪԤ��������ţ�-1�˳�����");
        int orderId=Utility.readInt();
        if(orderId==-1){
            System.out.println("=============ȡ��Ԥ������============");
            return;
        }else {
            DiningTable diningTable=diningTableService.getDiningTableById(orderId);
            if(diningTable==null){
                System.out.println("=============Ԥ������������============");
                return;
            }
            if("��".equals(diningTable.getState())){
                char key=Utility.readConfirmSelection();
                if(key=='Y'){
                    System.out.print("\nԤ�������֣�");
                    String orderName=Utility.readString(50);
                    System.out.print("\nԤ���˵绰��");
                    String orderTel=Utility.readString(25);
                    if(diningTableService.orderDiningTable(orderId,orderName,orderTel)){
                        System.out.println("=============Ԥ���ɹ�============");
                    }else {
                        System.out.println("=============Ԥ��ʧ��============");
                    }
                }else {
                    System.out.println("\n==============ȡ��Ԥ��============");
                }
            }else{
                System.out.println("\n==============�ò����Ѿ�Ԥ�����߾Ͳ���============");
                return;

            }
        }
    }
    public void listMenu(){
        List<Menu> menu = menuService.getMenu();
        System.out.println("\n��Ʒ���\t\t��Ʒ��\t\t���\t\t�۸�");
        for (Menu menu1 :menu) {
            System.out.println(menu1);
        }


    }

    public void orderMenu(){
        System.out.println("\n=============��ͷ���============");
        System.out.print("��ѡ���͵����ţ�-1�˳�����");
        int diningTableID=Utility.readInt();
        if(diningTableID==-1){
            System.out.println("\n=============ȡ�����============");
            return;
        }
        DiningTable diningTable=diningTableService.getDiningTableById(diningTableID);
        if(diningTable==null){
            System.out.println("\n=============Ԥ������������============");
            return;
        }
        if("��".equals(diningTable.getState())){
            System.out.println("\n=============��ҪԤ������============");
            return;
        }
        System.out.print("\n��ѡ���Ʒ�ı�ţ�-1�˳�����");
        int menuID=Utility.readInt();
        if(menuID==-1){
            System.out.println("\n=============ȡ�����============");
            return;
        }
        Menu menu = menuService.getMenuById(menuID);
        if(menu==null){
            System.out.println("\n=============��Ʒ������============");
            return;
        }
        System.out.print("\n��ѡ���Ʒ��������-1�˳�����");
        int nums=Utility.readInt();
        if(nums==-1){
            System.out.println("\n=============ȡ�����============");
            return;
        }
        if(billService.orderMenu(menuID,nums,diningTableID)){
            System.out.println("\n=============��ͳɹ�============");
        }else{
            System.out.println("\n=============���ʧ��============");
        }
    }

    public void listBill(){
        List<MultiTableBean> bill1 = billService.getBill();
        System.out.println("\n���\t\t��Ʒ��\t\t��Ʒ��\t\t��Ʒ��\t\t���\t\t����\t\t����\t\t\t\t\t\t\t״̬");
        for (MultiTableBean bill : bill1) {
            System.out.println(bill);
        }
        System.out.println("==============��ʾ���============");

    }

    public void payBill(){
        System.out.println("\n==============���˷���============");
        System.out.print("��ѡ��Ҫ���˵����ӱ�ţ�-1�˳�����");
        int diningTableId=Utility.readInt();
        if(diningTableId==-1){
            System.out.println("\n==========����ȡ��===========");
            return;
        }
        if(!(billService.hasPayBill(diningTableId))){
            System.out.println("\n==========�ò����ѽ���===========");
            return;
        }
        System.out.print("\n���˷�ʽ���ֽ�/֧����/΢�ţ��س���ʾ�˳���");
        String state=Utility.readString(20,"");
        if("".equals(state)){
            System.out.println("\n==========����ȡ��===========");
            return;
        }
        char key=Utility.readConfirmSelection();
        if(key=='Y'){
            if(billService.payBill(diningTableId, state)){
                System.out.println("\n==========���˳ɹ�===========");
            }else{
                System.out.println("\n==========����ʧ��===========");
            }
        }else{
            System.out.println("\n==========����ȡ��===========");
        }
    }

    @Test
    public void mainMenu() {
        while (loop) {
            System.out.println("===========����¥============");
            System.out.println("\t\t1 ��¼����¥");
            System.out.println("\t\t2 �˳�����¥");
            System.out.print("���������ѡ��");
            key =Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.print("������Ա���룺");
                    String empid=Utility.readString(50);
                    System.out.print("\n�������� �룺 ");
                    String pwd=Utility.readString(50);
                    Employee employee = employeeService.getEmployee(empid,pwd);
                    if (employee!=null){
                        System.out.println("\n==========��½�ɹ�===========");
                        Menu();
                    }else{
                        System.out.println("\n===========��½ʧ��==========");
                    }
                    break;
                case "2":
                    loop=false;
                    break;
                default:
                    System.out.println("�������������������롣");

            }

        }
        System.out.println("�˳�����¥ϵͳ");
    }
@Test
    public void Menu() {
        while (loop) {
            System.out.println("\n===============����¥(�����˵�)================");
            System.out.println("\t\t 1 ��ʾ����״̬");
            System.out.println("\t\t 2 Ԥ������");
            System.out.println("\t\t 3 ��ʾ���в�Ʒ");
            System.out.println("\t\t 4 ��ͷ���");
            System.out.println("\t\t 5 �鿴�˵�");
            System.out.println("\t\t 6 ����");
            System.out.println("\t\t 9 �˳�����¥");
            System.out.print("���������ѡ��: ");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    listDiningTable();
                    break;
                case "2":
                    orderDiningTable();
                    break;
                case "3":
                    listMenu();
                    break;
                case "4":
                    orderMenu();
                    break;
                case "5":
                    listBill();
                    break;
                case "6":
                    payBill();
                    break;
                case "9":
                    loop = false;
                    break;
                default:
                    System.out.println("�������������������롣");
            }

        }
    }
}