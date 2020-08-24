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
import java.awt.Color;
public class colWord {
    private Color wordColor;
    private String word;
    public colWord(String s, Color c){
        wordColor=c;
        word=s;
    }
    public void setWord(String s){
        word=s;
    }
    public String getWord(){
        return word;
    }
    public Color getColor(){
        return wordColor;
    }
}
