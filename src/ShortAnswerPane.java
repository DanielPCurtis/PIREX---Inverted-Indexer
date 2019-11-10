// allows user to click on search results to show long answer
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import PirexControll.CriteriaExtraction;
import PirexControll.SearchCriteria;
import PirexIO.SearchFile;
public class ShortAnswerPane extends JPanel{
	
	public ShortAnswerPane(){
		super();
		//
		// create clickable Jpanel
		ArrayList<Integer> arrList = new ArrayList<>();
		
		JPanel clickPanel = new JPanel(new GridLayout(arrList.size(), 0));
		clickPanel.setBackground(Color.gray);
		
		JTextField clickText = new JTextField();
		clickText.setBackground(Color.white);
		
	
	
	
		clickPanel.add(clickText);
		add(clickPanel, BorderLayout.NORTH);
	}
	
	
}
