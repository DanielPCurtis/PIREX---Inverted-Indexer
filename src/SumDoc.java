import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import DataObjects.Book;



public class SumDoc extends JPanel implements ActionListener {
	private JScrollPane scroll;
	private JTextArea textPane;
	private int num = 0;

    public SumDoc(JTextArea textPane) {
    	super();
       	this.setLayout(new BorderLayout());
       	this.textPane = textPane;
       	this.textPane.setEditable(false);
    	scroll = new JScrollPane(textPane,
    				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	scroll.setBorder(BorderFactory.createCompoundBorder(
    			BorderFactory.createEmptyBorder(20, 10, 10, 10)
    			, BorderFactory.createLineBorder(Color.gray)));
    	this.add(scroll,BorderLayout.CENTER);
    }
    

    
    /**
     * create a string 
     * @param Book object
     * OpusID, Author, Title, NumDoc, & Dir (directory)
     * 
     * 
     */
    private String sumString(Book b) {
    	String author = b.getAuthor();
    	int numdocs = b.getNumDocs();
    	String title = b.getTitle();
    	int opusID = b.getOpusId();
    	String dir = b.getDir();
    	String r = String.format("Opus %d: %s %s %d documents\n\t %s" , opusID, author, title, numdocs, dir);
    	return r;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
    

}    

    

