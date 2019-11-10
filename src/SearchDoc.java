/*The top text box must show the short form display.
 * A scroll bar must be added to the text box if necessary.
 * Selecting an item from the results display must cause the UI to switch to the Long Form Display state.
 * When a document in the short form display is chosen, the entire document must be shown in the bottom
   text box in the long form display.
 * Choosing another document must change the contents of the long from display to the selected document.*/

// SCROLL BAR DOES NOT WORK FOR LONG FORM SEARCH RESULT BOX

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

import DataObjects.Book;
import DataObjects.SearchRecord;
import PirexControll.CriteriaExtraction;
import PirexControll.InvertedIndexer;
import PirexControll.SearchCriteria;
import PirexIO.ExtractBook;
import PirexIO.SearchFile;

public class SearchDoc extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	InvertedIndexer InvIndx;
	JTextField text;
	JTextArea longAnswer;
	StyledDocument doc;
	SimpleAttributeSet attr;
	JTextPane shortAnswer;
	SearchRecord[] result = new SearchRecord[100];
	int eye;
	
	@SuppressWarnings("static-access")
	public SearchDoc() {
		super();
		Font font = new Font("Arial",Font.BOLD, 18);
		setLayout(new BorderLayout());
		/**
		 * Use Layouts to make items proportional
		 */
		// QUERY TEXTFIELD
		BorderLayout layout = new BorderLayout();
		JPanel query = new JPanel(layout);
		query.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		layout.setHgap(30);
		
		JLabel qLabel = new JLabel("Query:");
	
		qLabel.setFont(font);

	//  ShortAnswerPane();
		text = new JTextField();
		text.addActionListener(this);
		text.setFont(font);
		//Clear Button
		JButton  clear = new JButton("Clear");
		clear.addActionListener(this);
		clear.setFont(font);
				
		query.add(qLabel, layout.WEST);		
		query.add(text, layout.CENTER);
		query.add(clear, layout.EAST);
		
		//contains short and long answer boxes
		//doc = shortAnswer.getStyledDocument();
		attr = new SimpleAttributeSet();
		//addMultipleLinesClickableText(doc);
		
		JPanel results = new JPanel();
		results.setLayout(new GridLayout(2,0));
		
		
		//Short Answer Panel
		shortAnswer = new JTextPane();
		shortAnswer.setEditable(false); 
		shortAnswer.setBackground(Color.white);
		JScrollPane scroll1 = new JScrollPane(shortAnswer,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll1.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 40, 10),
				BorderFactory.createLineBorder(Color.DARK_GRAY)));
		

		//ScrollPanel long answer, set border
		longAnswer = new JTextArea();
		longAnswer.setEditable(false);
		longAnswer.setLineWrap(true);
		longAnswer.setBackground(Color.white);
		JScrollPane scroll2 = new JScrollPane(longAnswer,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll2.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 40, 10),
				BorderFactory.createLineBorder(Color.DARK_GRAY)));
		
		
		//add shortanswer box + scroll to results panel
		
		results.add(scroll1);
		results.add(scroll2);
		
		
		
		//add both pane and Results to tab
		add(query,BorderLayout.NORTH);
		add(results, BorderLayout.CENTER);
		
	}
	
	public void setInvertedIndexer(InvertedIndexer invrtd) {
		InvIndx = invrtd;
	}
	
	public void clearShortAnswer() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
    {
        String myText =  text.getText();
        String clr = evt.getActionCommand();
        doc = shortAnswer.getStyledDocument();
        
        System.out.println("Clear is..." + clr);
        if(clr == "Clear")	{
        	
        	try {
				doc.remove(0, doc.getLength());
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        	longAnswer.setText(null);
        	text.setText(null);
        }
        
        else if (myText!=null && !"".equals(myText.trim()))
        {
            /*JLabel label = new JLabel(myText);
            label.setOpaque(true);
            label.setBackground(Color.yellow);
            label.setBorder(BorderFactory.createLineBorder(Color.black,1));
            shortAnswer.setCaretPosition(shortAnswer.getDocument().getLength());
            shortAnswer.insertComponent(label);*/
        	//shortAnswer.setCaretPosition(shortAnswer.getDocument().getLength());
            String[] queries = new String[100];
            
            LinkedList<SearchRecord> results = InvIndx.searchTerms(myText);
			//StringBuilder sb = new StringBuilder();
			Iterator<SearchRecord> itor = results.iterator();
			
			int i = 0;
			while(itor.hasNext() && i < 100) {
				SearchRecord rec = itor.next();
				this.result[i] = rec;
				queries[i] = this.result[i].toString();
				//sb.append(rec.toString()).append("\n");
				i++;
			}
            
            //StringBuilder queryList = new StringBuilder();
            //JLabel docResult = null;
            
            for (eye = 0; eye < queries.length; eye++) {
            	if (queries[eye] !=null && !"".equals(queries[eye].trim())) {
                	int j=eye;
    	            JLabel docResult = new JLabel(queries[eye].toString());
    	            docResult.setOpaque(true);
    	            // label.setBackground(Color.gray);
    	            docResult.setBorder(BorderFactory.createLineBorder(Color.gray,1));
    	            shortAnswer.setCaretPosition(shortAnswer.getDocument().getLength());
    	            shortAnswer.insertComponent(docResult);
    	            try {
    					doc.insertString(doc.getLength(), "\n", null);
    				} catch (BadLocationException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    	            
    	            
    	            docResult.addMouseListener(new MouseAdapter() {
    	                public void mouseClicked(MouseEvent evt)
    	                {
    	                	ExtractBook book1;
    	                    Book book2;
    	                    //Set Long Form Summary
    	
    	                    book1 = new ExtractBook(result[j].getOpusDir());
    	                    
    	                    longAnswer.setText(book1.printDoc());
    	                    //JOptionPane.showMessageDialog(SearchDoc.this,"This will link to the complete book in the Long Form Display "+ myText,"Information",JOptionPane.INFORMATION_MESSAGE);
    	                }
                });
            }
            
            
            
	            
	            //text.setText("\n");
	        
	            
            }
            try
            {
                doc.insertString(doc.getLength(), " ", attr );
            }
            catch (BadLocationException ex)
            {
                ex.printStackTrace();
            }
        }
   
        printEventInfo(evt);
    }
	public void printEventInfo(ActionEvent ev) {
		System.out.println(ev.getActionCommand());
		System.out.println(ev.getID());
		System.out.println(ev.getSource());
		System.out.println(ev.toString());
		System.out.println(ev.getClass());
	}
}
