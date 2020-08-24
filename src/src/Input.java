import java.util.ArrayList;

public class Input {
    
    private ArrayList<String> sentences;
    
    public Input (String input){
        sentences = new ArrayList <String>();
        int record = 0;
        for (int i = 0;i < input.length();i ++){
            String chara = input.substring(i, i + 1);
            if (chara.equals(".") || chara.equals(",") || chara.equals("!") || 
                    chara.equals("?") || chara.equals(":") || chara.equals(";")){
                //If the character is a punctuation - it marks the end of a text segment;
                sentences.add(input.substring(record,i + 1));
                record = i + 1;
                //to account for spaces after the punctuation:
            }
            while(record < input.length() && input.substring(record, record + 1).equals(" ")){
                record ++;
            }
        }
    }
    
    public ArrayList<String> getList (){
        return sentences;
    }
    
    
}
