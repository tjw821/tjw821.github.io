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
        System.out.println("\n餐桌编号\t\t餐桌状态");
        for (DiningTable o :diningTAble) {
            System.out.println(o);

        }
        System.out.println("==============显示完毕============");

    }

    public void orderDiningTable(){
        System.out.println("\n===============预定餐桌==============");
        System.out.print("请选择要预定餐桌编号（-1退出）：");
        int orderId=Utility.readInt();
        if(orderId==-1){
            System.out.println("=============取消预定餐桌============");
            return;
        }else {
            DiningTable diningTable=diningTableService.getDiningTableById(orderId);
            if(diningTable==null){
                System.out.println("=============预定餐桌不存在============");
                return;
            }
            if("空".equals(diningTable.getState())){
                char key=Utility.readConfirmSelection();
                if(key=='Y'){
                    System.out.print("\n预定人名字：");
                    String orderName=Utility.readString(50);
                    System.out.print("\n预定人电话：");
                    String orderTel=Utility.readString(25);
                    if(diningTableService.orderDiningTable(orderId,orderName,orderTel)){
                        System.out.println("=============预定成功============");
                    }else {
                        System.out.println("=============预定失败============");
                    }
                }else {
                    System.out.println("\n==============取消预定============");
                }
            }else{
                System.out.println("\n==============该餐桌已经预定或者就餐中============");
                return;

            }
        }
    }
    public void listMenu(){
        List<Menu> menu = menuService.getMenu();
        System.out.println("\n菜品编号\t\t菜品名\t\t类别\t\t价格");
        for (Menu menu1 :menu) {
            System.out.println(menu1);
        }


    }

    public void orderMenu(){
        System.out.println("\n=============点餐服务============");
        System.out.print("请选择点餐的桌号（-1退出）：");
        int diningTableID=Utility.readInt();
        if(diningTableID==-1){
            System.out.println("\n=============取消点餐============");
            return;
        }
        DiningTable diningTable=diningTableService.getDiningTableById(diningTableID);
        if(diningTable==null){
            System.out.println("\n=============预定餐桌不存在============");
            return;
        }
        if("空".equals(diningTable.getState())){
            System.out.println("\n=============需要预定餐桌============");
            return;
        }
        System.out.print("\n请选择菜品的编号（-1退出）：");
        int menuID=Utility.readInt();
        if(menuID==-1){
            System.out.println("\n=============取消点餐============");
            return;
        }
        Menu menu = menuService.getMenuById(menuID);
        if(menu==null){
            System.out.println("\n=============菜品不存在============");
            return;
        }
        System.out.print("\n请选择菜品的数量（-1退出）：");
        int nums=Utility.readInt();
        if(nums==-1){
            System.out.println("\n=============取消点餐============");
            return;
        }
        if(billService.orderMenu(menuID,nums,diningTableID)){
            System.out.println("\n=============点餐成功============");
        }else{
            System.out.println("\n=============点餐失败============");
        }
    }

    public void listBill(){
        List<MultiTableBean> bill1 = billService.getBill();
        System.out.println("\n编号\t\t菜品号\t\t菜品名\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态");
        for (MultiTableBean bill : bill1) {
            System.out.println(bill);
        }
        System.out.println("==============显示完毕============");

    }

    public void payBill(){
        System.out.println("\n==============结账服务============");
        System.out.print("请选择要结账的桌子编号（-1退出）：");
        int diningTableId=Utility.readInt();
        if(diningTableId==-1){
            System.out.println("\n==========结账取消===========");
            return;
        }
        if(!(billService.hasPayBill(diningTableId))){
            System.out.println("\n==========该餐桌已结账===========");
            return;
        }
        System.out.print("\n结账方式（现金/支付宝/微信）回车表示退出：");
        String state=Utility.readString(20,"");
        if("".equals(state)){
            System.out.println("\n==========结账取消===========");
            return;
        }
        char key=Utility.readConfirmSelection();
        if(key=='Y'){
            if(billService.payBill(diningTableId, state)){
                System.out.println("\n==========结账成功===========");
            }else{
                System.out.println("\n==========结账失败===========");
            }
        }else{
            System.out.println("\n==========结账取消===========");
        }
    }

    @Test
    public void mainMenu() {
        while (loop) {
            System.out.println("===========满汉楼============");
            System.out.println("\t\t1 登录满汉楼");
            System.out.println("\t\t2 退出满汉楼");
            System.out.print("请输入你的选择：");
            key =Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.print("请输入员工码：");
                    String empid=Utility.readString(50);
                    System.out.print("\n请输入密 码： ");
                    String pwd=Utility.readString(50);
                    Employee employee = employeeService.getEmployee(empid,pwd);
                    if (employee!=null){
                        System.out.println("\n==========登陆成功===========");
                        Menu();
                    }else{
                        System.out.println("\n===========登陆失败==========");
                    }
                    break;
                case "2":
                    loop=false;
                    break;
                default:
                    System.out.println("你输入有误，请重新输入。");

            }

        }
        System.out.println("退出满汉楼系统");
    }
@Test
    public void Menu() {
        while (loop) {
            System.out.println("\n===============满汉楼(二级菜单)================");
            System.out.println("\t\t 1 显示餐桌状态");
            System.out.println("\t\t 2 预定餐桌");
            System.out.println("\t\t 3 显示所有菜品");
            System.out.println("\t\t 4 点餐服务");
            System.out.println("\t\t 5 查看账单");
            System.out.println("\t\t 6 结账");
            System.out.println("\t\t 9 退出满汉楼");
            System.out.print("请输入你的选择: ");
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
                    System.out.println("你输入有误，请重新输入。");
            }

        }
    }
}