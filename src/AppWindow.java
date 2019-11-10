import javax.swing.*;

import PirexControll.InvertedIndexer;

import java.awt.*;
import java.awt.event.ActionListener;

public class AppWindow {
    final static String SEARCHDOCPANEL = "Search for Documents";
    final static String LOADDOCPANEL = "Load Documents";
    final static String SUMDOCPANEL = "Summarize Documents";
    final static int extraWindowWidth = 200;
    
    SearchDoc searchDoc;
    LoadDoc loadDoc;
    SumDoc sumDoc;
    InvertedIndexer indexer = new InvertedIndexer();
    JTextArea sumTextArea  = new JTextArea();

    public void addComponent(Container pane){
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 20));
        tabbedPane.setBorder(
        		BorderFactory.createEmptyBorder(20, 10, 10, 10));
        		//BorderFactory.createLineBorder(Color.gray)));
        tabbedPane.setBackground(Color.white);
        
        
        //create card panels

        searchDoc = new SearchDoc();
        tabbedPane.addTab(SEARCHDOCPANEL, searchDoc);
        searchDoc.setInvertedIndexer(indexer);

        loadDoc = new LoadDoc(sumTextArea);
        tabbedPane.addTab(LOADDOCPANEL, loadDoc);
       loadDoc.setIndexer(indexer);
        	
        	sumTextArea.setBackground(Color.white);
        	sumTextArea.setEditable(false);
        	

        sumDoc = new SumDoc(sumTextArea);
        tabbedPane.addTab(SUMDOCPANEL, sumDoc);

        pane.add(tabbedPane);
       
    }

    private static void showGUI(){
        JFrame jFrame = new JFrame("Pirex");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AppWindow appWindow = new AppWindow();
        appWindow.addComponent(jFrame.getContentPane());

     //   jFrame.pack();
        jFrame.setSize(750,530);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);

    }


    public static void main(String[]args){
        showGUI();
    }



}
