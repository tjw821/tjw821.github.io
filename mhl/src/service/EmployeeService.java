package service;

import DAO.EmployeeDAO;
import domain.Employee;
import org.junit.Test;

public class EmployeeService {
    private EmployeeDAO employeeDAO=new EmployeeDAO();

    public  Employee getEmployee(String empid,String pwd){
        return employeeDAO.querySingle("select * from employee where empid=? and pwd=md5(?)", Employee.class,empid,pwd);
    }
}
