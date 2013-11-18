/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Data.DataAccessLayer;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
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

/**
 *
 * @author Umair
 */
@WebServlet(name = "Score", urlPatterns = {"/Score"})
public class Score extends HttpServlet {

      private Connection connect = null;
      private Statement statement = null;
      private PreparedStatement preparedStatement = null;
      private ResultSet resultSet = null;
      private DataAccessLayer    dataObject= new DataAccessLayer();
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Score</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Score at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             
        } finally {            
            out.close();
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         String action=request.getParameter("action");


            try {
                if(action.equalsIgnoreCase("updateScore"))                
                       updateScore(request, response);
                else if(action.equalsIgnoreCase("updateEnergy"))                
                       updateEnergy(request, response);
                else if(action.equalsIgnoreCase("updateCoin"))                
                       updateCoin(request, response);
                else if(action.equalsIgnoreCase("updateLoanInfo"))                
                       updateLoanInfo(request, response);
                
            
            } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
    protected void updateScore(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException{
       
        
//http://localhost:8080/WebApplication3/Score?action=updateScore&ID=ali&score=500        
            String ID =   request.getParameter("ID");
            String score =   request.getParameter("score");   
            
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            
            ////
            if("".equals(ID) || "".equals(score))
                return;
            ////
            
                ArrayList<String> parm  =   new ArrayList<String>();
                parm.add(score);
                parm.add(ID);
                
                
                ArrayList<String> parmType  =    new ArrayList<String>();
                parmType.add("Integer");
                parmType.add("String");
                
                String  sql =   "UPDATE player_info SET score=? WHERE ID=?";
                
                dataObject.updateScore(sql, parm, parmType);
    }
    
    protected void updateEnergy(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException{
       

//http://localhost:8080/WebApplication3/Score?action=updateEnergy&ID=ali&energy=50
        
            String ID =   request.getParameter("ID");
            String energy =   request.getParameter("energy");   
            
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            
            
                ArrayList<String> parm  =   new ArrayList<String>();
                parm.add(energy);
                parm.add(ID);
                
                
                ArrayList<String> parmType  =    new ArrayList<String>();
                parmType.add("Integer");
                parmType.add("String");
                
                String  sql =   "UPDATE player_info SET energy=? WHERE ID=?";
                
                dataObject.updateScore(sql, parm, parmType);
    } 
    
    protected void updateCoin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException{
       
        
//http://localhost:8080/WebApplication3/Score?action=updateCoin&ID=ali&coin=550
        
        
            String ID =   request.getParameter("ID");
            String coin =   request.getParameter("coin");   
            
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            

            
                ArrayList<String> parm  =   new ArrayList<String>();
                parm.add(coin);
                parm.add(ID);
                
                
                ArrayList<String> parmType  =    new ArrayList<String>();
                parmType.add("Integer");
                parmType.add("String");
                
                String  sql =   "UPDATE player_info SET coin=? WHERE ID=?";
                
                dataObject.updateScore(sql, parm, parmType);
    }
    
    protected void updateLoanInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException{
       
       
        
//http://localhost:8080/WebApplication3/Score?action=updateLoanInfo&ID=ali&isDebtor=yes
        
            String ID =   request.getParameter("ID");
            String info =   request.getParameter("isDebtor");   
            
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            


            
                ArrayList<String> parm  =   new ArrayList<String>();
                parm.add(info);
                parm.add(ID);
                
                
                ArrayList<String> parmType  =    new ArrayList<String>();
                parmType.add("String");
                parmType.add("String");
                
                String  sql =   "UPDATE player_info SET isDebtor=? WHERE ID=?";
                
                dataObject.updateScore(sql, parm, parmType);
    }    
    
    









}

