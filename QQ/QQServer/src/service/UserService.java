package service;

import DAO.UserDAO;
import qqcommon.User;

public class UserService {
    private UserDAO userDAO=new UserDAO();
    public boolean getUser(String userId,String passwd){
        User user = userDAO.querySingle("select * from user where userId = ? and passwd = ? ", User.class, userId, passwd);
        if(user!=null){
            return true;
        }else {
            return false;
        }

    }
}
