package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtilsDruid {
    private static DataSource ds;
    static{
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src\\druid01.properties"));
            ds= DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getconnection() throws SQLException {
        return ds.getConnection();
    }
    public static void getClose(ResultSet resultSet){
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void getClose(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void getClose(Statement state){
        if(state!=null){
            try {
                state.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

