package Data;

import java.sql.SQLException;
import java.util.ArrayList;
import Classes.FarmMataData;
import Classes.UserMataData;


public class DataAccessLayer extends DataBaseLayer{

    
    
    public DataAccessLayer() {
                super();
    }
    
    
    public int getLatestFarm(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
                executeQuery(sql,parm,parmType);

                int ID = 0; 
                if(resultSet.next())
                {
                    ID = resultSet.getInt("ID");
                }
                close();
                return ID;
    }

    
    public String getFarmData(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
        
    
        String xml = "<?xml version=\"1.0\"?>";
        xml += "<farms>";
        int count =0;

        executeQuery(sql,parm,parmType);    
        
        

        while(resultSet.next())
        {
            count++;
            xml += "<farm>";
            xml += "<ID>";
            xml += resultSet.getInt(FarmMataData.ID);
            xml += "</ID>";
            
            xml += "<crop>";
            xml += resultSet.getString(FarmMataData.crop);
            xml += "</crop>";

            
            xml += "<cycle>";
            xml += resultSet.getInt(FarmMataData.cycle);
            xml += "</cycle>";

            
            xml += "<owner>";
            xml += resultSet.getString(FarmMataData.owner);
            xml += "</owner>";
            
            xml += "<state>";
            xml += resultSet.getString(FarmMataData.state);
            xml += "</state>";

            
            xml += "<x>";
            xml += resultSet.getInt(FarmMataData.x);
            xml += "</x>";
            
            xml += "<y>";
            xml += resultSet.getInt(FarmMataData.y);
            xml += "</y>";
            
            
            xml += "</farm>";
            
        }
        
        xml += "<size>" + count + "</size>";
        xml += "</farms>";
        close();
        
        
        return xml;
    }
    
    public String getInitialData(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
    
        String xml = "<?xml version=\"1.0\"?>";
        xml += "<user>";

        executeQuery(sql,parm,parmType);    
        
        if(resultSet.next())
        {
            xml += "<coin>";
            xml += resultSet.getInt(UserMataData.coin);
            xml += "</coin>";
            
            xml += "<energy>";
            xml += resultSet.getInt(UserMataData.energy);
            xml += "</energy>";

            
            xml += "<score>";
            xml += resultSet.getInt(UserMataData.score);
            xml += "</score>";

            
            xml += "<isDebtor>";
            xml += resultSet.getString(UserMataData.isDebtor);
            xml += "</isDebtor>";
            
            xml += "<wheat>";
            xml += resultSet.getString(UserMataData.total_wheat);
            xml += "</wheat>";
            
            xml += "<corn>";
            xml += resultSet.getString(UserMataData.total_corn);
            xml += "</corn>";            
            
        }
        
        
        xml += "</user>";
        
        close();
        
        return xml;
    }
            
    public int returnValue(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException

    {
        executeQuery(sql,parm,parmType);    
        
        if(resultSet.next())
        {
            return resultSet.getInt("count(*)");
        }
        close();
        
        return 0;
    }
    
    
    public int returnPreviousRate(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
        executeQuery(sql,parm,parmType);    
        int rate =0;
        if(resultSet.next())
        {
            rate = resultSet.getInt("rate");
        }
        close();
        
        return rate;
    }    

    
    
    public int[] returnPreviousRate1(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
        
        int [] array = new int[2];
        int count = 0;
        
        executeQuery(sql,parm,parmType);    
        
        while(resultSet.next())
        {
            array[count++] =  resultSet.getInt("rate");
        }
        close();
        
       return  array;
    }    
    
    
    public  boolean loginAuthentication(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
        executeQuery(sql,parm,parmType);        
        
        if(resultSet.next())
        {
            return true;
        }
        
        
        close();

        return false;
    }

    public boolean signUp(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
        return executeUpdate(sql,parm,parmType);
        
        

    }  
    
    
    public int getStock(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
        
        executeQuery(sql,parm,parmType);
        int totalStock = 0;
        
        if(resultSet.next())
        {
            totalStock = resultSet.getInt("stock");
        }
        
        
        close();

        return totalStock;        
    
    }    
    public int getBaitUlMallCoins(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
        
        executeQuery(sql,parm,parmType);
        int totalCoins = 0;
        
        if(resultSet.next())
        {
            totalCoins = resultSet.getInt("coins");
        }
        
        
        close();

        return totalCoins;        
    
    }
    public void alterFarmState(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
        executeUpdate(sql,parm,parmType);
        close();
        

    }

    public  void updateScore(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
        executeUpdate(sql,parm,parmType);                                
        close();
       
    }
    public void executeUpdate1(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
            executeUpdate(sql,parm,parmType);                                
            close();
    }
    
    public int getGameDay() throws SQLException, ClassNotFoundException
    {
            String sql = "select Max(id) from tmp";
            ArrayList<String> parm  = new ArrayList<String>();
            ArrayList<String> parmType = new ArrayList<String>();
            
        int day = 0 ;    
        executeQuery(sql,parm,parmType);    
        
        if(resultSet.next())
        {
            day =  resultSet.getInt("Max(id)");
        }
        close();
        
        return day;        
        
    }
    
    
    public int getMaxID(String sql , ArrayList<String> parm  , ArrayList<String> parmType) throws SQLException, ClassNotFoundException
    {
            
        int ID = 0 ;    
        executeQuery(sql,parm,parmType);    
        
        if(resultSet.next())
        {
            ID =  resultSet.getInt("Max(id)");
        }
        close();
        
        return ID;        
        
    }    
    
    
    
}
