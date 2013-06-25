package Classes;

import Data.DataAccessLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "market", urlPatterns = {"/market"})
public class market extends HttpServlet {

    private DataAccessLayer    dataObject= new DataAccessLayer();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

        } finally {            
            out.close();
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     

       
        
                String action   =   request.getParameter("action");
                
                try
                {
                    if(action.equalsIgnoreCase("addTransaction"))
                        addTransaction(request, response);              
                    else if(action.equalsIgnoreCase("getRates"))
                        getRates(request, response);
                    else if(action.equalsIgnoreCase("addRates"))
                        addRates(request, response);
                    else if(action.equalsIgnoreCase("getLastFiveRates"))
                        getLastFiveRates(request, response);
                    
 
                
                }
                
           catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    private float calculatePrice(String item_type) throws SQLException, ClassNotFoundException
    {
    
        

            int current_day = dataObject.getGameDay();
            int rate1 = getRate(item_type,current_day - 1);
            int rate2 = getRate(item_type,current_day - 2);
            
            int demand1 = getDemand(item_type, current_day - 1 );
            int demand2 = getDemand(item_type, current_day - 2);            

            int supply1 = getSupply(item_type, current_day - 1);
            int supply2 = getSupply(item_type, current_day - 2);                      


           Equation eq1 = new Equation() ;
           eq1.y = supply1;
           eq1.x = rate1;
               
           Equation eq2 = new Equation() ;
           eq2.y = supply2;
           eq2.x = rate2;

           Equation supplay = getEquation(eq1, eq2);
           
           eq1.y = demand1;
           eq1.x = rate1;

           eq2.y = demand2;
           eq2.x = rate2;
           
           Equation demand = getEquation(eq1, eq2);           
           float price =  solveSupplyDemand(supplay,demand);

           if(price == 0)
               return rate1;
           
           
           return price;
           
    };
    
    
    
    private float solveSupplyDemand(Equation eq1,Equation eq2)
    {
    


            float tmp1 = eq1.m - eq2.m;
            float tmp2 = eq2.c - eq1.c;
            if(tmp1 == 0)
                return 0;
            
            
            float ans = (float) ( tmp2*1.0 / tmp1);            
            return ans;            
    }
    private Equation getEquation(Equation eq1,Equation eq2)
    {

        
           float m = (float) ((float) (eq2.y - eq1.y)*1.0 / (eq2.x - eq1.x)) ;   
           float c =  (float) (eq1.y - (eq1.x * m));

           
           Equation eq = new Equation();
           eq.m = m;
           eq.c = c;
           
           
           return eq;
           
           
    }
    
    
    
   private int getDemand(String item_type,int day) throws SQLException, ClassNotFoundException
   {
   
           String sql = "SELECT count(*) FROM market_transaction WHERE game_day = ? AND transaction_type = '0' AND item_type = ?";
           ArrayList<String> parm  =   new ArrayList<String>();
           parm.add((day )+"");
           parm.add(item_type);

           ArrayList<String> parmType  =    new ArrayList<String>();
           parmType.add("Integer");  
           parmType.add("String");             
           int demand = dataObject.returnValue(sql, parm, parmType);        
   
           return demand;
           
   }
    
   private int getSupply(String item_type,int day) throws SQLException, ClassNotFoundException
   {
       
           String sql = "SELECT count(*) FROM market_transaction WHERE game_day = ? AND transaction_type = '1' AND item_type = ?";
           ArrayList<String> parm  =   new ArrayList<String>();
           parm.add((day )+"");
           parm.add(item_type);

           ArrayList<String> parmType  =    new ArrayList<String>();
           parmType.add("Integer");  
           parmType.add("String");  
           
           int supply = dataObject.returnValue(sql, parm, parmType);        
   
           return supply;   
   }
    
   private int getRate(String item_type,int day) throws SQLException, ClassNotFoundException
   {
       
        String sql = "SELECT  rate FROM market_rates WHERE item_type=? AND game_day = ?";

        ArrayList<String> parm  =   new ArrayList<String>();
        parm.add(item_type);
        parm.add(day+"");

       ArrayList<String> parmType  =    new ArrayList<String>();
       parmType.add("String");
       parmType.add("Integer");


       int tmp =dataObject.returnPreviousRate(sql, parm, parmType);


       if(tmp == 0)
           tmp = 35;
       
       return tmp;

   }

   
   private void getLastFiveRates(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException         
   {
            //http://localhost:8080/WebApplication3/market?action=getLastFiveRates&item_type=corn&count=7   

            String item_type    =   request.getParameter("item_type"); 
            String count    =   request.getParameter("count"); 
            int game_day        =  dataObject.getGameDay() ;
            
            
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();       
       
            out.println("<?xml version=\"1.0\"?>");
            out.println("<prices>");            
            
            
            //returnPreviousRate
            for(int i=1;i < Integer.parseInt(count)+1;i++)
            {
                
            
                String sql = "SELECT rate FROM market_rates WHERE game_day = ? AND item_type = ?";
                ArrayList<String> parm  =   new ArrayList<String>();
                parm.add((game_day - i)+"");
                parm.add(item_type);
                ArrayList<String> parmType  =    new ArrayList<String>();
                parmType.add("Integer");            
                parmType.add("String");  
                
                
                int price = dataObject.returnPreviousRate(sql, parm, parmType);

               out.println("<value>");                
                        out.println("<day>"); 
                        out.println(game_day - i);                
                        out.println("</day>"); 

                        out.println("<price>"); 
                        out.println(price);                
                        out.println("</price>"); 
                        
                out.println("</value>");   
               
               

            
            }
            
                out.println("<count>");
                out.println(count);
                out.println("</count>");
                
                //count
                out.println("</prices>");
            

            

            
            
       
       
   }

           private void getRates(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException
    {
        
        
    //http://localhost:8080/WebApplication3/market?action=getRates&item_type=corn

        /*
 * 
        
        calculatePrice("");
       
       String transaction_type    =   request.getParameter("transaction_type");    
       String item    =   request.getParameter("item"); 
         * 
         
       
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date date = new Date();
       String item_type = request.getParameter("item_type");
       
       
       String sql = "SELECT count(*) FROM market_transaction WHERE date BETWEEN DATE_SUB(NOW(), INTERVAL 5 DAY) AND NOW() AND transaction_type = '0' AND item_type = ?";
       ArrayList<String> parm  =   new ArrayList<String>();
       parm.add(item_type);

       ArrayList<String> parmType  =    new ArrayList<String>();
       parmType.add("String");          
       int buying = dataObject.returnValue(sql, parm, parmType);  
            
             
       
       
       sql = "SELECT count(*) FROM market_transaction WHERE date BETWEEN DATE_SUB(NOW(), INTERVAL 5 DAY) AND NOW() AND transaction_type = '1' AND item_type = ?";
          
       int selling = dataObject.returnValue(sql, parm, parmType);     
       int previousRates = getPreviousRates(item_type);                        

       if(buying > selling)
       {
           float tmp = (float)(buying)/(buying*selling) * 10;
           previousRates += Math.ceil(tmp);
       }
               
       else if(selling > buying)
       {

           float tmp = (float)(selling)/(buying*selling) * 10;           
           previousRates -= Math.ceil(tmp);
           if(previousRates <=0)
               previousRates += Math.ceil(tmp);
       }
       
       
       int tmp =0 ;
       

            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();

       

        try {

            out.println("<?xml version=\"1.0\"?>");
            out.println("<result>"+previousRates+"</result>");                   

             
        } finally {            
            out.close();
        }
                    

 */
        
        //addRates(item_type,previousRates+"");    
        
        
        
        
        
        
        
            String item_type    =   request.getParameter("item_type"); 
       
            float price1 =  calculatePrice(item_type);
       
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();       
       
            out.println("<?xml version=\"1.0\"?>");
            out.println("<result>"+price1+"</result>"); 
            
            
        
    }
    
    

    private int getPreviousRates(String item_type) throws SQLException, ClassNotFoundException
    {
                    String sql = "SELECT date,rate FROM market_rates WHERE date BETWEEN DATE_SUB(NOW(), INTERVAL 1 DAY) AND NOW() AND item_type =? order by date desc";
            
                    ArrayList<String> parm  =   new ArrayList<String>();
                    parm.add(item_type);

                    ArrayList<String> parmType  =    new ArrayList<String>();
                    parmType.add("String");

    
                     int tmp =dataObject.returnPreviousRate(sql, parm, parmType);  
                     
                     return tmp;
        
        
    }
    
    


    
    private void addRates(String item_type , String amount) throws SQLException, ClassNotFoundException
    {
        
//http://localhost:8080/WebApplication3/market?action=addRates&item_type=corn&amount=35        

        
                   String game_day =  dataObject.getGameDay()+"";
        	   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   //get current date time with Date()
                    Date date = new Date();
           
                    String sql = "insert into market_rates values('',?,?,'"+dateFormat.format(date)+"',?)";
        
                    ArrayList<String> parm  =   new ArrayList<String>();
                    parm.add(item_type);
                    parm.add(amount);
                    parm.add(game_day);

                    
                    
                    ArrayList<String> parmType  =    new ArrayList<String>();
                    parmType.add("String");
                    parmType.add("Integer");
                    parmType.add("Integer");
    
                    dataObject.updateScore(sql, parm, parmType);                    
    
    
    }
    
    
    private void addRates(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException
    {
        
//http://localhost:8080/WebApplication3/market?action=addRates&item_type=corn&amount=35        

        	   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   //get current date time with Date()
                    Date date = new Date();
           
                    String sql = "insert into market_rates values('',?,?,'"+dateFormat.format(date)+"',?)";
        
        

                    String item_type    =   request.getParameter("item_type");
                    String amount    =   request.getParameter("amount");
                    String game_day    =   request.getParameter("day");
                    
        
    
                    
                    ArrayList<String> parm  =   new ArrayList<String>();
                    parm.add(item_type);
                    parm.add(amount);
                    parm.add(game_day);

                    
                    
                    ArrayList<String> parmType  =    new ArrayList<String>();
                    parmType.add("String");
                    parmType.add("Integer");
                    parmType.add("Integer");
                    
                    
                     dataObject.updateScore(sql, parm, parmType);                    
    
    
    }
    
    
    
    private void addTransaction(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException
    {
        
//http://localhost:8080/WebApplication3/market?action=addTransaction&username=ali&transaction_type=1&item_type=wheat&amount=30        
//http://localhost:8080/WebApplication3/market?action=addTransaction&username=ali&transaction_type=0&item_type=wheat&amount=32        

                   String game_day =  dataObject.getGameDay() +"";
                   
                   
                   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   //get current date time with Date()
                   Date date = new Date();
           
                    String sql = "insert into market_transaction values('',?,?,?,?,'"+dateFormat.format(date)+"',?)";
        
        
                    String username    =    request.getParameter("username");                    
                    String transaction_type    =   request.getParameter("transaction_type");
                    String item_type    =   request.getParameter("item_type");
                    String amount    =   request.getParameter("amount");
        
        
    
                    
                    ArrayList<String> parm  =   new ArrayList<String>();
                    parm.add(username);
                    parm.add(transaction_type);
                    parm.add(item_type);
                    parm.add(amount);
                    parm.add(game_day);

                    
                    
                    ArrayList<String> parmType  =    new ArrayList<String>();
                    parmType.add("String");
                    parmType.add("String");
                    parmType.add("String");
                    parmType.add("Integer");
                    parmType.add("Integer");
    
                     dataObject.alterFarmState(sql, parm, parmType);                    
    
    
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
}
