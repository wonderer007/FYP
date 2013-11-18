/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Haider
 */
@WebServlet(name = "Game", urlPatterns = {"/Game"})
public class Game extends HttpServlet {


    private Thread t ;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Game</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Game at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {            
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    

            if(request.getParameter("action").equals("start"))
                start();
            else if (request.getParameter("action").equals("stop"))
                stop();

    }

    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    public void start()
    {
        t = new timer();
        t.start();    
    }
    
    public void stop()
    {
        t.stop();
    }
    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
