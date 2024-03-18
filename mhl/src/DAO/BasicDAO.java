package DAO;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.JDBCUtilsDruid;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDAO<T>{

     private QueryRunner qr=new QueryRunner();

     public int update(String sql,Object...parameters){
         Connection connection=null;
         try {
             connection = JDBCUtilsDruid.getconnection();
             return qr.update(connection,sql,parameters);

         } catch (SQLException e) {
             throw new RuntimeException(e);
         }finally {
             JDBCUtilsDruid.getClose(connection);
         }
     }

     //查询的结果为多列
    public List<T> queryMulti(String sql,Class<T> clazz,Object...parameters){
         Connection connection=null;
        try {
            connection=JDBCUtilsDruid.getconnection();
            return qr.query(connection,sql,new BeanListHandler<>(clazz),parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsDruid.getClose(connection);
        }
    }
    //查询结果为单行
    public T querySingle(String sql,Class<T>clazz,Object...parameters){
         Connection connection=null;
        try {
            connection=JDBCUtilsDruid.getconnection();
            return qr.query(connection,sql,new BeanHandler<>(clazz),parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsDruid.getClose(connection);
        }
    }

    //查询结果为单行单列
    public Object queryScalar(String sql,Object...parameters){
         Connection connection=null;
        try {
            connection=JDBCUtilsDruid.getconnection();
            return qr.query(connection,sql,new ScalarHandler<>(),parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsDruid.getClose(connection);
        }
    }
}
