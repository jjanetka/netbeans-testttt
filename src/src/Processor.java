import java.util.ArrayList;

public class Processor {
    
    private int lines;
    private ArrayList<String> copy;
    
    public Processor (int length, Input input){
        //default values;
        lines = length;
        copy = input.getList();
    }
    
    public int checkLength(ArrayList<String> list){
        int maxL = 0;
        int minL = list.get(0).length();
        int minI = 0;
        for (int i = 0; i < list.size(); i ++){
            if (list.get(i).length() > maxL){
                maxL = list.get(i).length();
            }
            if (list.get(i).length() < minL){
                minL = list.get(i).length();
                minI = i;
            }
        }
        if ((double)maxL/minL >= 3){
            return minI;
        }
        return -1;
    }
    
    public ArrayList<String> adjustLength(ArrayList<String> list){
        int checkResult = checkLength(list);
        if(copy.size() > 0 && checkResult >= 0){
            int mindex = 0;
            for (int i = 0; i < copy.size(); i ++){
                if (copy.get(i).length() < copy.get(mindex).length()){
                    mindex = i;
                }
            }
            list.set(checkResult, list.get(checkResult) + " " + copy.get(mindex));
            copy.remove(mindex);
            return adjustLength(list);
        }
        else return list;
    }
    
    public String composePoem(){
        if (lines > copy.size()) return "tooManyLines";  //if the requested length is too large;
        //the poem will never match "tooManyLines" exactly so this is ok (poem requires punctuations);
        ArrayList<String> newPara = new ArrayList<String>();
        //fill new list with randomly picked text segments;
        while(newPara.size() < lines){
            int randnom = (int)(Math.random()*copy.size());
            newPara.add(copy.get(randnom));
            copy.remove(randnom);
        }
        //adjust lengths;
        newPara = adjustLength(newPara);
        
        return printOut(newPara);
    }
    
    public String printOut(ArrayList<String> finishedList){
        String poemString = "";
        for (String s : finishedList)
            poemString = poemString + s + "\n";
        String finalChara = poemString.substring(poemString.length() - 1);
        
        //if the last punctuation is not . ! or ? , force the punctuation in;
        if (!finalChara.equals(".") && !finalChara.equals("!") && !finalChara.equals("?")){
            poemString = poemString.substring(0,poemString.length() - 2) + ".";
        }
        //turn the first letter to uppercase;
        poemString = poemString.substring(0,1).toUpperCase() + poemString.substring(1);
        return poemString;
    }
}
