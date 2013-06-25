package Classes;

import Data.DataAccessLayer;
import Data.DataBaseLayer;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "user", urlPatterns = {"/user"})
public class user extends HttpServlet  {


      private DataAccessLayer    dataObject= new DataAccessLayer();
      
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
        try {

            out.println("<?xml version=\"1.0\"?>");
            out.println("<status>true</status>");
             
        } finally {            
            out.close();
        }
    }
    
    
    
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            String username =   request.getParameter("username");
            String password =   request.getParameter("password");   
            
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            
            

            if(!"".equals(username) && !"".equals(password))
            {
                ArrayList<String> parm  =   new ArrayList<String>();
                parm.add(username);
                parm.add(password);
                
                ArrayList<String> parmType  =    new ArrayList<String>();
                parmType.add("String");
                parmType.add("String");
                
               
                String  sql =   "SELECT * FROM USER WHERE username=? AND password=?";
                try {
                    boolean result = dataObject.loginAuthentication(sql, parm, parmType);
                    
                    if(result)
                    {
                        out.println("<?xml version=\"1.0\"?>");
                        out.println("<status>true</status>");                    
                    }
                    else
                    {
                        out.println("<?xml version=\"1.0\"?>");
                        out.println("<status>false</status>");                    
                    }
            
                } catch (SQLException ex) {
                    Logger.getLogger(user.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(user.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
    
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
                String action   =   request.getParameter("action");
                if(action.equalsIgnoreCase("init"))
                    initData(request, response); 
                else if(action.equalsIgnoreCase("login"))
                    login(request, response); 
                else if(action.equalsIgnoreCase("signUp"))                
                    try {
            signUp(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(user.class.getName()).log(Level.SEVERE, null, ex);
            
            
                response.setContentType("text/xml");
                PrintWriter out = response.getWriter();
            
            

                        out.println("<?xml version=\"1.0\"?>");
                        out.println("<status>false</status>");                    
                      
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(user.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }


    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void initData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        

        
//http://localhost:8080/WebApplication3/user?action=init&username=ali
        
                    String  sql =   "Select * FROM player_info WHERE player_info.ID = ? ";        
    
                    String username       =   request.getParameter("username");

                    ArrayList<String> parm  =   new ArrayList<String>();
                    parm.add(username);

                    ArrayList<String> parmType  =    new ArrayList<String>();
                    parmType.add("String");



                    try {
                        String string = dataObject.getInitialData(sql, parm, parmType);
                        
                        PrintWriter out = response.getWriter();
                        
                        out.println(string);
                                    
                    } catch (SQLException ex) {
                        Logger.getLogger(farm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(farm.class.getName()).log(Level.SEVERE, null, ex);
                    }                
                
                
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        
        
    //http://localhost:8080/WebApplication3/user?action=login&username=ali&password=123
            String username =   request.getParameter("username");
            String password =   request.getParameter("password");  
            
            
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            
            

            if(!"".equals(username) && !"".equals(password))
            {
                ArrayList<String> parm  =   new ArrayList<String>();
                parm.add(username);
                parm.add(password);
                
                ArrayList<String> parmType  =    new ArrayList<String>();
                parmType.add("String");
                parmType.add("String");
                
               
                String  sql =   "SELECT * FROM USER WHERE username=? AND password=?";
                try {
                    boolean result = dataObject.loginAuthentication(sql, parm, parmType);
                    
                    if(result)
                    {
                        out.println("<?xml version=\"1.0\"?>");
                        out.println("<status>true</status>");                    
                    }
                    else
                    {
                        out.println("<?xml version=\"1.0\"?>");
                        out.println("<status>false</status>");                    
                    }
            
                } catch (SQLException ex) {
                    Logger.getLogger(user.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(user.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }        
    }

    private void signUp(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException {
        
        
//http://localhost:8080/WebApplication3/user?action=signUp&username=ali&password=123&email=ali@hotmail.com        
              
            
        
                
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            
            
   
                   
                   int totalCoins = getBaitUlMallCoins();
                   int totalWheat = getTotalStock("wheat");
                   int totalcorn = getTotalStock("corn");
                   
                   
                   
                        out.println("<?xml version=\"1.0\"?>");                   
                   if( totalCoins < 1000 || totalWheat < 500 || totalcorn <500 )
                   {

                        out.println("<status>resource</status>");                      
                   
                   
                   }
                   else
                   {
                       
                       
                       
                       
                String username =   request.getParameter("username");
                String password =   request.getParameter("password");  
                String email =   request.getParameter("email"); 
            
            
            //alterFarmState
            
                String sql = "INSERT INTO user VALUES (?,?,?)";
            
                ArrayList<String> parm  =   new ArrayList<String>();
                parm.add(username);
                parm.add(password);
                parm.add(email);
                
                ArrayList<String> parmType  =    new ArrayList<String>();
                parmType.add("String");
                parmType.add("String");
                parmType.add("String");
            
            
            
                      boolean result = dataObject.signUp(sql, parm, parmType);                         
                       
                       parm.clear();
                       parmType.clear();
                       sql = "INSERT INTO player_info VALUES ('"+username+"',500,100,'no',500,200,200)";
                       
                       dataObject.alterFarmState(sql, parmType, parmType);
                       
                       
                       
                        parm.clear();
                        parmType.clear();
                        
                       
                        
                       totalCoins = totalCoins - 500;                        
                       sql = "UPDATE baitulmall SET coins = '"+totalCoins+"' WHERE ID = 0 ";                       
                       dataObject.alterFarmState(sql, parmType, parmType);                        
                       
                        
                        
                        
                       parm.clear();
                       parmType.clear();
                        
                       totalWheat = totalWheat - 200;
                       sql = "UPDATE market SET stock = '"+totalWheat+"' where crop = 'wheat'";                       
                       dataObject.alterFarmState(sql, parmType, parmType);
                       
                        
                       
                       
                       parm.clear();
                       parmType.clear();
                       totalcorn = totalcorn-200;
                       sql = "UPDATE market SET stock = '"+totalcorn+"' where crop = 'corn'";                       
                       dataObject.alterFarmState(sql, parmType, parmType);
                       
                       
                       

                       out.println("<?xml version=\"1.0\"?>");
                       out.println("<status>true</status>");    
                   
                   }
                   
                   
        
    }

    private int getTotalStock(String item_type) throws SQLException, ClassNotFoundException
    {
                String sql = "SELECT stock FROM market WHERE crop = ?";            
                ArrayList<String> parm  =   new ArrayList<String>(); 
                parm.add(item_type);
                ArrayList<String> parmType  =    new ArrayList<String>();
                parmType.add("String");
                
                
                int totalStock = dataObject.getStock(sql, parm, parmType);
                
       
    return totalStock;        
    
    }
            
            
    private int getBaitUlMallCoins() throws SQLException, ClassNotFoundException
    {

        
                String sql = "SELECT Max(id) FROM baitulmall";
                ArrayList<String> parm  =   new ArrayList<String>();                
                ArrayList<String> parmType  =    new ArrayList<String>();
                int MaxID = dataObject.getMaxID(sql, parm, parmType);
        
        
                sql = "SELECT coins FROM baitulmall WHERE ID = ?";            
                parm.clear();
                parmType.clear();
                parm.add(MaxID+"");
                parmType.add("Integer");
                
                int totalCoins = dataObject.getBaitUlMallCoins(sql, parm, parmType);
                
       
    return totalCoins;
    
    }
    
}
