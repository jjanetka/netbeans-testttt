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
import java.util.*;
public class Enemy {
    private int hp;
    private int maxHP;
    private int attackPoints;
    private int loot;
    private int poisonStacks;
    //creates new enemy of certain level
    public Enemy(int level){
        maxHP=100*(int)Math.pow(2,level-1);
        hp=100*(int)Math.pow(2,level-1);
        attackPoints=10*(int)Math.pow(2,level-1);
        loot=100*(int)Math.pow(2,level-1);
        poisonStacks=0;
    }
    //returns maxHP
    public int getMaxHP(){
        return maxHP;
    }
    //returns hp
    public int getHP(){
        return hp;
    }
    //returns attackpoints
    public int getAttackPoints(){
        return attackPoints;
    }
    //subtracts hp until 0
    public void subtractHP(int damage){
        if(damage>=hp){
            hp=0;
        }
        else{
            hp=hp-damage;
        }
    }
    //returns loot value
    public int getLoot(){
        return loot;
    }
    //adds poison stacks to enemy
    public void poison(int stax){
        poisonStacks=poisonStacks+stax;
    }
    //returns poison stacks on enemy
    public int getPoisonStacks(){
        return poisonStacks;
    }
    //clears poison stacks
    public void clearPoison(){
        poisonStacks=0;
    }
}
