package service;

import DAO.DiningTableDAO;
import domain.DiningTable;
import org.junit.Test;

import java.util.List;

public class DiningTableService {
    private DiningTableDAO diningTableDAO=new DiningTableDAO();
    public List<DiningTable> getDiningTAble(){
        return diningTableDAO.queryMulti("select * from diningTable",DiningTable.class);
    }

    public DiningTable getDiningTableById(int id){
        return diningTableDAO.querySingle("select * from diningtable where id=?",DiningTable.class,id);
    }

    public boolean orderDiningTable(int orderId,String orderName,String orderTel){
        int update =diningTableDAO.update("update diningtable set state='ÒÑ¾­Ô¤¶¨',orderName=?,orderTel=? where id=?",orderName,orderTel,orderId);
        return update>0;
    }

    public boolean updateState(int id,String state){
        int update =diningTableDAO.update("update diningtable set state =? where id=?",state,id);
        return update>0;
    }

    public boolean clear(int id){
        int update = diningTableDAO.update("update diningtable set state='¿Õ',orderName='',orderTel=''where id=?", id);
        return update>0;
    }


}
