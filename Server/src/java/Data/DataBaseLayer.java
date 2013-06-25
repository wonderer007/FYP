
package Data;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public class DataBaseLayer {
          
          protected Connection        connect;    
          protected Statement         statement;
          protected PreparedStatement preparedStatement;
          protected ResultSet         resultSet;

    public DataBaseLayer() {

                        this.connect             = null;    
                        this.statement           = null;
                        this.preparedStatement   = null;
                        this.resultSet           = null;
        
    }
  
          
          
    
    
          private void getConnection () throws ClassNotFoundException, SQLException
          {
                  Class.forName("com.mysql.jdbc.Driver");
                        connect = (Connection) DriverManager
                            .getConnection("jdbc:mysql://localhost/fyp?"
                                + "user=root&password=");
                    
          } 
          
          protected  void executeQuery(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
          {
          
 
                    getConnection();
                    preparedStatement = (PreparedStatement) connect.prepareStatement(sql);   
                    bindParam(sql, parm, parmType);
                    resultSet = (ResultSet) preparedStatement.executeQuery();
                    

          }
          
          protected boolean executeUpdate(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
          { 
                    getConnection();
                    preparedStatement = (PreparedStatement) connect.prepareStatement(sql);   
                    bindParam(sql, parm, parmType);
                    int value = preparedStatement.executeUpdate();  
                    
                    if(value==1)
                        return true;
                    else 
                        return false;
                    
          }
        
          private void bindParam(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException
          {
                for(int i=0;i<parm.size();i++)
                {
                        if(parmType.get(i).equalsIgnoreCase("String"))
                            preparedStatement.setString((i+1), parm.get(i).toString());
                        else if(parmType.get(i).equalsIgnoreCase("Integer"))
                        {
                            String num =    parm.get(i);
                            int tmp =   Integer.parseInt(num);
                            preparedStatement.setInt((i+1),tmp );
                        }
                }
                
          }
          
          protected void close() {
            try {
              if (resultSet != null) {
                resultSet.close();
              }

              if (statement != null) {
                statement.close();
              }

              if (connect != null) {
                connect.close();
              }
            } catch (Exception e) {

            }
          }          
        
}
