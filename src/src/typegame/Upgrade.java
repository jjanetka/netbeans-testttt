/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typegame;

/**
 *
 * @author Victor
 */
import java.util.ArrayList;
import java.util.*;
public class Upgrade {
    private int timesBought;
    private ArrayList<String> descriptions=new ArrayList<String>();
    private int cost;
    private boolean priceScales;
    //creates new upgrade with description
    public Upgrade(String firstDesc,boolean scale, int money){
        timesBought=0;
        descriptions.add(0,firstDesc);
        cost=money;
        priceScales=scale;
    }
    //returns timesBought
    public int getTimesBought(){
        return timesBought;
    }
    //returns cost
    public int getCost(int num){
        int total=cost*num;
        return total;
    }
    //returns description appropriate for times bought if description changes
    public String getDescription(){
        if(timesBought>descriptions.size()){
            return descriptions.get((descriptions.size())-1);
        }
        else{
            return descriptions.get(timesBought);
        }
    }
    public void addDescription(String desc){
        descriptions.add(desc);
    }
    public void buyUpgrade(int num){
        timesBought=timesBought + num;
        if(priceScales=true){
            cost=cost*(int)Math.pow(2, num);
        }
    }
}
