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
public class Hero {
    private int hp;
    private int maxHP;
    private int damage;
    //makes new hero
    public Hero(int healthy, int strong){
        maxHP=healthy;
        damage=strong;
        hp=maxHP;
    }
    //returns current hp
    public int gethp(){
        return hp;
    }
    //returns maxhp
    public int getMaxHP(){
        return maxHP;
    }
    //returns damage
    public int getDamage(){
        return damage;
    }
    //increases damage
    public void damageUp(int uppers){
        damage=damage+uppers;
    }
    public void subtractHP(int ouch){
        if(ouch>=hp){
            hp=0;
        }
        else{
            hp=hp-ouch;
        }
    }
    public void addHP(int heal){
        if(hp+heal>=maxHP){
            hp=maxHP;
        }
        else{
            hp=hp+heal;
        }
    }
}
