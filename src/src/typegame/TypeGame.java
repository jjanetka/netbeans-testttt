
package typegame;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public class TypeGame extends javax.swing.JFrame{

    private int offset=50;
    private String filePath=FileSystems.getDefault().getPath(new String()).toAbsolutePath().toString();
    private ArrayList<colWord> rainbow=new ArrayList<colWord>();
    private int delay=1000;
    private TimerTask ticks;
    private Timer timer=new Timer();
    private Scanner wordListGetter;
    private String wordList;
    private Hero chad;
    private Enemy monster;
    private ArrayList<Upgrade> upgradesList=new ArrayList<Upgrade>();
    private boolean counterAttack=false;
    private int money=0;
    private List<String> words=new ArrayList<String>();
    private Image ib;
    private int enemyLevel=1;
    private Graphics ibg;
    private boolean timerSet=false;
    private boolean wasImmune=false;
    private int immunity=0;
    private int h=0;
    private Color lastWord;
    private FileWriter saver;
    private Scanner loadScanner;
    private List<String> loadArray;
    private String loadString;
    private File loadFile;
            
    public TypeGame() {
        initComponents();
        setLocationRelativeTo(null);
        setUpImageBuffer();
        goldText.setEditable(false);
        goldText.setText("gold: " + money);
        System.out.println("Click the start battle button to enter battle");
        System.out.println("Click the shop button to buy upgrades");
        System.out.println("Type the correct words into the textbox to fight");
        System.out.println("Type words in a certain order to perform combos");
        System.out.println("Red words deal damage, green words apply a stack of damaging poison, blue heals you");
        /*Timer timer=new Timer();
        TimerTask task=new actionTime();
        timer.schedule(task, 1000,1000);*/
        /*File file=new File(filePath+"\\wordList.txt");
        try{
        Scanner wordListGetter=new Scanner(file);
        while(wordListGetter.hasNextLine()){
            wordList=wordList + " " + wordListGetter.nextLine();
            System.out.println(wordList);
        }
        //System.out.println();
        }
        catch(Exception e){
            System.out.println("something messed up");
        }*/
        setUpWords();
        setUpUpgrades();
    }
    //updates graphics
    public void draw(){
        ibg.setColor(Color.white);
        ibg.clearRect(0, 0, 500, 500);
        ibg.fillRect(0,0,500,500);
        ibg.setColor(Color.black);
        Font font = new Font("Serif", Font.PLAIN, 24);
        ibg.setFont(font);
        for(int i=0;i<rainbow.size();i++){
            ibg.setColor(rainbow.get(i).getColor());
            ibg.drawString(rainbow.get(i).getWord(), 50, 150+(i*100));
        }
        ibg.setColor(lastWord);
        ibg.fillRect(0, 380, 40, 40);
        ibg.setColor(Color.red);
        //Player hp bar
        ibg.fillRect(390-getBarLength(chad.gethp(),chad.getMaxHP()),440,getBarLength(chad.gethp(),chad.getMaxHP()),50);
        //monster hp 
        ibg.fillRect(110,10,getBarLength(monster.getHP(),monster.getMaxHP()),50);
        Graphics g=this.getGraphics();
        g.drawImage(ib, 50, 50, this);
        g.drawImage(Toolkit.getDefaultToolkit().getImage(filePath+"\\Images\\Enemy.png"), 50, 50, this);
        g.drawImage(Toolkit.getDefaultToolkit().getImage(filePath + "\\Images\\Hero.png"),450, 450, this );
    }
    //calculates healthbar length
    public int getBarLength(int current, int max){
        double length=(double)current/max;
        length=length*380;
        return (int)length;
    }
    public void setUpImageBuffer(){
        ib=this.createImage(501,501);
        ibg=ib.getGraphics();
    }

    //creates timertask that allows enemy to attack and poison to deal damage
    public class actionTime extends TimerTask{
    public actionTime(){
        super();
    }
    public void run(){
        if(immunity==0){
        chad.subtractHP(monster.getAttackPoints()-upgradesList.get(2).getTimesBought());
        }
        else{
            immunity--;
        }
        monster.subtractHP(monster.getPoisonStacks()*chad.getDamage());
        if(counterAttack){
            monster.subtractHP((chad.getDamage()*chad.getMaxHP()));
            counterAttack=false;
        }
        draw();
        if(chad.gethp()==0){
            endBattle();
        }
        else if(monster.getHP()==0){
            newEnemy();
        }
    }
    
}
    //checks if String can be converted to int
    
    public static boolean canParse(String s){
            try{
                Integer.parseInt(s);
                return true;
            }
            catch(NumberFormatException e){
                return false;
            }
        }
    //ends battle
    public void endBattle(){
        inputField.setEditable(false);
        monster.clearPoison();
        ticks.cancel();
        battleButton.setEnabled(true);
        System.out.println("you have died");
    }
    //returns current money
    public int getMoney(){
        return money;
    }
    //removes money
    public void removeMoney(int minus){
        money=money-minus;
    }
    //returns arraylist of upgrades
    public ArrayList<Upgrade> getUpgradesList(){
        return upgradesList;
    }
    //converts text file to string
    public void setUpWords(){
        String[] tempStringList=new String[201];
        try{
            File file=new File(filePath+"\\wordList.txt");
        Scanner wordListGetter=new Scanner(file);
        while(wordListGetter.hasNextLine()){
            wordList=wordList + " " + wordListGetter.nextLine();
        }
        tempStringList=wordList.split("     ");
        words=Arrays.asList(tempStringList);
        rainbow.add(new colWord(getWord(),Color.red));
        rainbow.add(new colWord(getWord(),Color.green));
        rainbow.add(new colWord(getWord(),Color.blue));
        checkUnique(rainbow);
    }
        catch(Exception e){
            System.out.println("Something messed up");
        }
    }
    
    //returns a String from string array words
    public String getWord(){
        int random=(int)(Math.random()*words.size());
        return words.get(random);
    }
    //sets up proper upgrade descriptions
    public void setUpUpgrades(){
        upgradesList.add(new Upgrade("increases damage by 10, costs ", false, 10));
        
        upgradesList.add(new Upgrade("increases health by 50, costs ",false,20));
        upgradesList.add(new Upgrade("increases armour by 1, costs ",false,20));
        upgradesList.add(new Upgrade("unlocks a red-red combo, which increases damage until the end of the battle, costs ",true,100));
        upgradesList.get(3).addDescription("unlocks a red-blue combo, which deals damage based on max health and damage the next time you are attacked, costs ");
        upgradesList.get(3).addDescription("unlocks a blue-blue combo, which makes you invincible for 5 seconds, usable once per battle,costs  ");
        upgradesList.get(3).addDescription("unlocks a green-red combo, which extra damage based on poison stacks,costs ");
    }
    //starts battle
    private void startBattle(){
        inputField.setEditable(true);
        wasImmune=false;
        ticks=new actionTime();
        timer.schedule(ticks,1000,1000);
        enemyLevel=1;
        monster=new Enemy(enemyLevel);
        chad=new Hero(200+upgradesList.get(1).getTimesBought()*50,50+upgradesList.get(0).getTimesBought()*10);
        lastWord=Color.white;
        draw();
    }
    //updates gold box
    public void updateGold(){
        goldText.setText("gold: "+ money);
    }
    //checks if all words in rainbow arraylist are unique
    private void checkUnique(ArrayList<colWord> kolor){
        ArrayList<String> tempStrings=new ArrayList<String>();
        for(int g=0;g<kolor.size();g++){
            if(tempStrings.contains(kolor.get(g).getWord())!=true){
                tempStrings.add(kolor.get(g).getWord());
            }
            else{
                while(tempStrings.contains(kolor.get(g).getWord())){
                kolor.get(g).setWord(getWord());
                }
                tempStrings.add(kolor.get(g).getWord());
            }
        }
        }
    //spawns a new enemy stronger than the last, and awards gold from the last enemy
    public void newEnemy(){
        money=money+monster.getLoot();
        enemyLevel++;
        monster=new Enemy(enemyLevel);
        System.out.println("Enemy is now level " + enemyLevel);
        goldText.setText("gold: " + money);
    }
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputField = new javax.swing.JTextField();
        battleButton = new javax.swing.JButton();
        shopButton = new javax.swing.JButton();
        goldText = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        saveMenu = new javax.swing.JMenuItem();
        loadMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        inputField.setText("Type here...");
        inputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFieldActionPerformed(evt);
            }
        });

        battleButton.setText("Start Battle");
        battleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                battleButtonActionPerformed(evt);
            }
        });

        shopButton.setText("Visit Shop");
        shopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shopButtonActionPerformed(evt);
            }
        });

        goldText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goldTextActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        saveMenu.setText("Save to File");
        saveMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });
        jMenu1.add(saveMenu);

        loadMenu.setText("Load from File");
        loadMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuActionPerformed(evt);
            }
        });
        jMenu1.add(loadMenu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(battleButton)
                    .addComponent(shopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(goldText, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(battleButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 277, Short.MAX_VALUE)
                .addComponent(goldText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(shopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputFieldActionPerformed
        // TODO add your handling code here:
        if(inputField.getText().equals(rainbow.get(0).getWord())){
            monster.subtractHP(chad.getDamage());
            if(lastWord.equals(Color.red)&&upgradesList.get(3).getTimesBought()>=1){
                chad.damageUp((upgradesList.get(0).getTimesBought())*5);
            }
            else if(lastWord.equals(Color.green)&&upgradesList.get(3).getTimesBought()>=4){
                monster.subtractHP(monster.getPoisonStacks()*monster.getPoisonStacks()*chad.getDamage());
            }
            lastWord=(rainbow.get(0).getColor());
            rainbow.get(0).setWord(getWord());
        }
        else if(inputField.getText().equals(rainbow.get(1).getWord())){
            monster.poison(1);
            lastWord=(rainbow.get(1).getColor());
            rainbow.get(1).setWord(getWord());
        }
        else if(inputField.getText().equals(rainbow.get(2).getWord())){
            chad.addHP(chad.getMaxHP()/10);
            if(lastWord.equals(Color.red)&&upgradesList.get(3).getTimesBought()>=2){
                counterAttack=true;
            }
            else if(lastWord.equals(Color.blue)&&upgradesList.get(3).getTimesBought()>=3){
                immunity=5;
                wasImmune=true;
            }
            lastWord=(rainbow.get(2).getColor());
            rainbow.get(2).setWord(getWord());
        }
        inputField.setText("");
        if(monster.getHP()==0){
            newEnemy();
        }
        draw();
        checkUnique(rainbow);
    }//GEN-LAST:event_inputFieldActionPerformed

    private void battleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_battleButtonActionPerformed
        // TODO add your handling code here:
        
        startBattle();
        battleButton.setEnabled(false);
    }//GEN-LAST:event_battleButtonActionPerformed

    private void shopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shopButtonActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        Shop s=new Shop(this);
        s.setVisible(true);
    }//GEN-LAST:event_shopButtonActionPerformed

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuActionPerformed
        // TODO add your handling code here:
        int[] saveInt ={upgradesList.get(0).getTimesBought(),upgradesList.get(1).getTimesBought(),upgradesList.get(2).getTimesBought(),upgradesList.get(3).getTimesBought(),money};
        
        try{
        saver=new FileWriter(filePath + "\\saveFile.txt");
        saver.write(saveInt[0]+" "+saveInt[1]+" "+saveInt[2]+" "+saveInt[3]+" "+saveInt[4]);
        saver.close();
        System.out.println("progress saved");
        }
        catch(IOException e){
            System.out.println("something went wrong");
        }
        
    }//GEN-LAST:event_saveMenuActionPerformed

    private void loadMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMenuActionPerformed
        // TODO add your handling code here:try{
        loadString="";
        try{
        loadFile=new File(filePath + "\\saveFile.txt");
        loadScanner=new Scanner(loadFile);
        while(loadScanner.hasNextLine()){
            loadString=loadString+loadScanner.nextLine();
        }
        loadArray=Arrays.asList(loadString.split(" "));
        for(int m=0;m<upgradesList.size();m++){
            if(canParse(loadArray.get(m))){
            upgradesList.get(m).buyUpgrade(Integer.parseInt(loadArray.get(m)));
            }
        }
        if(canParse(loadArray.get(4))){
            money=Integer.parseInt(loadArray.get(4));
        }
        updateGold();
        }
        catch(Exception e){
            System.out.println("file not loaded");
        }
    }//GEN-LAST:event_loadMenuActionPerformed

    private void goldTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goldTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_goldTextActionPerformed

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TypeGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TypeGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TypeGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TypeGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TypeGame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton battleButton;
    private javax.swing.JTextField goldText;
    private javax.swing.JTextField inputField;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem loadMenu;
    private javax.swing.JMenuItem saveMenu;
    private javax.swing.JButton shopButton;
    // End of variables declaration//GEN-END:variables
}
