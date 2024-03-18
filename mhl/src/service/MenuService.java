package service;

import DAO.MenuDAO;
import domain.Menu;

import java.util.List;

public class MenuService {
    private MenuDAO menuDAO=new MenuDAO();

    public List<Menu> getMenu(){
        return menuDAO.queryMulti("select * from menu",Menu.class);
    }
    public Menu getMenuById(int id){
        return menuDAO.querySingle("select * from menu where id=?",Menu.class,id);
    }

}
