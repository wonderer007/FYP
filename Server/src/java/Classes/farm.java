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

@WebServlet(name = "farm", urlPatterns = {"/farm"})
public class farm extends HttpServlet {
      
    
    private DataAccessLayer    dataObject= new DataAccessLayer();
      
      
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet farm</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet farm at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             
        } finally {            
            out.close();
        }
    }
    
    


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    

                
                String action   =   request.getParameter("action");
                if(action.equalsIgnoreCase("alterFarmState"))
                    this.alterFarmState(request, response);
                else if(action.equalsIgnoreCase("addFarm"))
                    try {
            this.createFarm(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(farm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(farm.class.getName()).log(Level.SEVERE, null, ex);
        }
                else if(action.equalsIgnoreCase("getFarmInfo"))
                    this.getFarmInfo(request, response);
                

                
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    
    private void alterFarmState(HttpServletRequest request, HttpServletResponse response)
    {
  
        
/*
    http://localhost:8080/WebApplication3/farm?action=alterFarmState&ID=87&state=3
  
 */
        
                    String  sql =   "UPDATE FARM SET state=? WHERE  ID=?";        
    
                    String ID       =   request.getParameter("ID");
                    String state    =   request.getParameter("state");

                    ArrayList<String> parm  =   new ArrayList<String>();
                    parm.add(state);
                    parm.add(ID);

                    ArrayList<String> parmType  =    new ArrayList<String>();
                    parmType.add("String");
                    parmType.add("Integer");



                    try {
                        dataObject.alterFarmState(sql, parm, parmType);
                    } catch (SQLException ex) {
                        Logger.getLogger(farm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(farm.class.getName()).log(Level.SEVERE, null, ex);
                    }
        
    }
    
    
    private void createFarm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException
    {
  
        
/*
        http://localhost:8080/WebApplication3/farm?action=addFarm&state=cultivationn&owner=ali&x=10&y=10&crop=Wheat
  
 */
        
                    String  sql =   "INSERT INTO FARM VALUES ('','1','1',?,?,?,?)";        
    
                    

                    String owner    =   request.getParameter("owner");                    
                    String X    =   request.getParameter("x");
                    String Y    =   request.getParameter("y");
                    String crop    =   request.getParameter("crop");


                    
                    
                    ArrayList<String> parm  =   new ArrayList<String>();
                    parm.add(owner);
                    parm.add(X);
                    parm.add(Y);
                    parm.add(crop);

                    
                    
                    ArrayList<String> parmType  =    new ArrayList<String>();
                    parmType.add("String");
                    parmType.add("Integer");
                    parmType.add("Integer");
                    parmType.add("String");



                    try {
                        dataObject.alterFarmState(sql, parm, parmType);
                    } catch (SQLException ex) {
                        Logger.getLogger(farm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(farm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    

                    sql = "SELECT Max(ID) as ID FROM farm WHERE owner = ?";

                    ArrayList<String> parm1  =   new ArrayList<String>();
                    parm1.add(owner);
                    
                    ArrayList<String> parmType1  =    new ArrayList<String>();
                    parmType1.add("String");
                    
                    
                    
                    response.setContentType("text/xml");
                    PrintWriter out = response.getWriter();

            
                    int ID = dataObject.getLatestFarm(sql, parm1, parmType1);
                    
                    out.println("<farm>");
                        out.println("<ID>");
                            out.println(ID);        
                        out.println("</ID>");                    
                    out.println("</farm>");
        
    }
    
    


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void getFarmInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
        
        
                    //http://localhost:8080/WebApplication3/farm?action=getFarmInfo&username=ali
        
                    String  sql =   "Select * FROM farm WHERE owner = ? ";        
    
                    String username       =   request.getParameter("username");

                    ArrayList<String> parm  =   new ArrayList<String>();
                    parm.add(username);

                    ArrayList<String> parmType  =    new ArrayList<String>();
                    parmType.add("String");



                    try {
                        String string = dataObject.getFarmData(sql, parm, parmType);
                        
                        PrintWriter out = response.getWriter();
                        
                        out.println(string);
                                    
                    } catch (SQLException ex) {
                        Logger.getLogger(farm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(farm.class.getName()).log(Level.SEVERE, null, ex);
                    }                
                
        
        
        
    }
}
