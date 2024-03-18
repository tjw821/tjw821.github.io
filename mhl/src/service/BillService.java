package service;

import DAO.BillDAO;
import DAO.MultTableBeanDAO;
import domain.Bill;
import domain.MultiTableBean;

import java.util.List;
import java.util.UUID;

public class BillService {
    private BillDAO billDAO=new BillDAO();

    private MultTableBeanDAO multTableBeanDAO=new MultTableBeanDAO();
    private  MenuService menuService=new MenuService();

    private  DiningTableService diningTableService=new DiningTableService();

    public boolean orderMenu(int menuId,int nums,int diningTableId){
        String billId= UUID.randomUUID().toString();
        double money=menuService.getMenuById(menuId).getPrice()*nums;
        int update =billDAO.update("insert into bill values(null,?,?,?,?,?,now(),'未结账')",billId,menuId,nums,money,diningTableId);
        if(update<=0){
            return false;
        }else {
            return diningTableService.updateState(diningTableId,"就餐中.......");
        }
    }

    public List<MultiTableBean> getBill(){
        return multTableBeanDAO.queryMulti("select bill.*,name from bill,menu where bill.menuId=menu.id", MultiTableBean.class);
    }



    public boolean hasPayBill(int diningTableId){
        Bill bill = billDAO.querySingle("select * from bill where diningTableId=? and state='未结账' limit 0,1", Bill.class, diningTableId);
        return bill!=null;
    }

    public boolean payBill(int diningTableId,String state){
        int update = billDAO.update("update bill set state=? where diningTableId=?", state, diningTableId);
        if(update<1){
            return false;
        }
        if(!(diningTableService.clear(diningTableId))){
            return false;
        }
        return true;
    }


}
