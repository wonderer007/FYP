/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Data.DataAccessLayer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Haider
 */
public class timer extends Thread{
    
   private DataAccessLayer    dataObject= new DataAccessLayer();
     
   @Override
    public void run()
    {
        while(true) {
           System.out.println("Thread is doing something");
            try {

                Thread.sleep(10000);

                    String sql = "insert into tmp values('')";
                    ArrayList<String> parm  =   new ArrayList<String>();
                    ArrayList<String> parmType  =    new ArrayList<String>();                    
                try {
                    dataObject.updateScore(sql, parm, parmType);
                } catch (SQLException ex) {
                    Logger.getLogger(timer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(timer.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(timer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   
}
