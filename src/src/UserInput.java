import java.awt.*;   
import javax.swing.*;

public class UserInput{

   
    public UserInput(){
     
    }
    
    public static int getInteger(){
         String s = JOptionPane.showInputDialog(null,"Enter integer");
         int x = Integer.parseInt(s);
         return(x);
    }
    
    public static String getString(){
         String s = JOptionPane.showInputDialog(null,"Enter String");
         return (s);
    }
    
    public static double getDouble(){
         String s = JOptionPane.showInputDialog(null,"Enter double");
         double y = Double.valueOf(s);
         return(y);
    }

    public static int getInteger(String text){
         String s = JOptionPane.showInputDialog(null,text);
         int x = Integer.parseInt(s);
         return(x);
    }
    
    public static String getString(String text){
         String s = JOptionPane.showInputDialog(null,text);
         return (s);
    }
    
    public static double getDouble(String text){
         String s = JOptionPane.showInputDialog(null,text);
         double y = Double.valueOf(s);
         return(y);
    }
    
}//end of class