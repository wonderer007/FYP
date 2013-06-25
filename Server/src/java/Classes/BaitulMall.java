    
package Classes;

import Data.DataAccessLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "BaitulMall", urlPatterns = {"/BaitulMall"})
public class BaitulMall extends HttpServlet {

      private DataAccessLayer    dataObject= new DataAccessLayer();
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BaitulMall</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BaitulMall at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             
        } finally {            
            out.close();
        }
    }

    
    public int getBaitUlMallCoins() throws SQLException, ClassNotFoundException
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
    
    
    
    
    private int getBaitUlMallZakatCoins() throws SQLException, ClassNotFoundException
    {

        
                String sql = "SELECT Max(id) FROM baitulmall";
                ArrayList<String> parm  =   new ArrayList<String>();                
                ArrayList<String> parmType  =    new ArrayList<String>();
                int MaxID = dataObject.getMaxID(sql, parm, parmType);
        
        
                sql = "SELECT zakatCoins as coins FROM baitulmall WHERE ID = ?";            
                parm.clear();
                parmType.clear();
                parm.add(MaxID+"");
                parmType.add("Integer");
                
                int totalCoins = dataObject.getBaitUlMallCoins(sql, parm, parmType);
                
       
    return totalCoins;
    
    }    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        


        if(request.getParameter("action").equals("addCharity"))
        {
            try {
                addCharity(request,response);
            } catch (SQLException ex) {
                Logger.getLogger(BaitulMall.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BaitulMall.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(request.getParameter("action").equals("updateZakatCoins"))
        {
            try {
                updateZakatCoins(request,response);
            } catch (SQLException ex) {
                Logger.getLogger(BaitulMall.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BaitulMall.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (request.getParameter("action").equals("askLoan"))
        {
            try {
                askLoan(request,response);
            } catch (SQLException ex) {
                Logger.getLogger(BaitulMall.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BaitulMall.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void updateZakatCoins(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        
//http://localhost:8080/WebApplication3/BaitulMall?action=updateZakatCoins&zakat=780

                String amount = request.getParameter("zakat");
                
                int totalCoins =0;
                totalCoins = getBaitUlMallZakatCoins();
                int value =  Integer.parseInt(amount);
                totalCoins = totalCoins + value;
                
        
                String sql = "UPDATE baitulmall SET zakatCoins = '"+ totalCoins +"' WHERE ID = '0'";
                ArrayList<String> parm  =   new ArrayList<String>();
                
                ArrayList<String> parmType  =    new ArrayList<String>();
                
                dataObject.alterFarmState(sql, parm, parmType);
                
        
    }
    
    

    private void addCharity(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        

//http://localhost:8080/WebApplication3/BaitulMall?action=addCharity&charity=780

                String amount = request.getParameter("charity");
                
                
                int totalCoins =0;
                totalCoins = getBaitUlMallCoins();
                int value =  Integer.parseInt(amount);
                totalCoins = totalCoins + value;
                
        
                String sql = "UPDATE baitulmall SET coins = '"+ totalCoins +"' WHERE ID = 0";
                ArrayList<String> parm  =   new ArrayList<String>();
                
                ArrayList<String> parmType  =    new ArrayList<String>();
                
                dataObject.alterFarmState(sql, parm, parmType);
                
        
    }

    private void askLoan(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException {
        
        
        
//http://localhost:8080/WebApplication3/BaitulMall?action=askLoan       
        int coins = getBaitUlMallCoins();
        
        
            response.setContentType("text/xml");

            PrintWriter out = response.getWriter();
 

        if(coins > 500)
        {
            out.println("<?xml version=\"1.0\"?>");
            out.println("<status>OK</status>");                
            
        }else
        {
            
            out.println("<?xml version=\"1.0\"?>");
            out.println("<status>NO</status>");                
        }
        
        
    }
    
}
