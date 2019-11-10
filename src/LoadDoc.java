
/**
 * LoadDoc - 
 * - implement 1 TextField Query with title to its left
 * - CLEAR Button that clears Query's texts
 * - JPanel that prints out search results that 
 * 				- also returns the numbers of results at the bottom  
 * - TextPane that prints out full document, starting from selected results.
 *
 *
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import DataObjects.Book;
import PirexControll.InvertedIndexer;
import PirexIO.*;

public class LoadDoc extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	
	// serialize object that implements ActionListener
	private static final long serialVersionUID = -8494198535564855288L;


	private InvertedIndexer indexer;
	private Book book;
	
	Font font = new Font("Arial", Font.BOLD, 18);
	
	private JComboBox<String> typeComboBox;
	
	private JTextField textFile;
	private JTextField title;
	private JTextField author;
	private JTextArea progressTextArea;
	private JTextArea sumDocTextArea;
		
	private JButton browse 					= new JButton("Browse");
	private JButton process 				= new JButton("Process");
	String fileType;
	
	//CONSTRUCTOR
	public LoadDoc(JTextArea tArea) {
		super();
		buildLoadDocPanel();
		sumDocTextArea = tArea;
	}
	
	public void buildLoadDocPanel() {

		setLayout(new BorderLayout());
		JPanel tophalf = new JPanel(new GridLayout(4,0,10,10));
		
		//top half
		
		constructTxtFilePane(tophalf);			//textFile Pane
		constructTypePane(tophalf);				//textFileType Pane
		constructTitleAuthorPane(tophalf);		//title and author Pane	
		constructProcessPane(tophalf);  		//Process
		
		//bottom half  change
		progressTextArea = new JTextArea();
		progressTextArea.setEditable(false);
		JPanel progressScreen = new JPanel(new BorderLayout());
		progressScreen.add(progressTextArea);
		progressScreen.setBorder(BorderFactory.createLineBorder(Color.gray));
		progressScreen.setBackground(Color.white);
		add(progressScreen);
	}
	
/**********************************************************************************/	
	
	public void constructTxtFilePane(JPanel tophalf)	{
		JPanel txtFilePane = new JPanel(new BorderLayout());
		txtFilePane.add(getTextFileLabel(), BorderLayout.WEST);
		txtFilePane.add(getTextFileField(), BorderLayout.CENTER);
		txtFilePane.add(getBrowseButton(), BorderLayout.EAST);
		tophalf.add(txtFilePane);
	}
	
	public JLabel getTextFileLabel() {
		JLabel txtLabel = new JLabel("Text File");
		txtLabel.setFont(font);
		return txtLabel;
	}
	
	public JTextField getTextFileField() {
		textFile = new JTextField();
		textFile.setFont(font);
		textFile.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0,10,0,10, getBackground()),
				BorderFactory.createLineBorder(Color.gray)));
		textFile.setHorizontalAlignment(JTextField.LEADING);
		return textFile;
	}
	
	public JButton getBrowseButton() {
		browse = new JButton("Browse");
		browse.addActionListener(this);
		browse.setFont(font);
		return browse;
	}
	// Done txtFilePane
	
	//CONSTRUCT TYPE PANE
	public void constructTypePane(JPanel tophalf)	{
		JPanel txtTypePane = new JPanel(new BorderLayout());
		txtTypePane.add(getTypeLabel(), BorderLayout.WEST);
		txtTypePane.add(getJComboBox(), BorderLayout.CENTER);
		tophalf.add(txtTypePane);
	}
	//
	
	public JLabel getTypeLabel()	{
		JLabel typeLabel = new JLabel("Text File Type: ");
		typeLabel.setFont(font);
		return typeLabel;
	}
	
	public JComboBox<String> getJComboBox()	{
		typeComboBox = new JComboBox<String>();
		typeComboBox.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0,10,0,0, getBackground()),
				BorderFactory.createLineBorder(Color.gray)));
		typeComboBox.setFont(font);
		//typeComboBox.setEditable(true);
		typeComboBox.addItem("Project Gutenberg File");
		typeComboBox.addItem("HTML");
		typeComboBox.addItem("Rich Text Format");
		return typeComboBox;
	}
	//Done txtTypePane
	
	//CONSTRUCT TITLE/AUTHOR PANE
	
	public void constructTitleAuthorPane(JPanel tophalf)	{
		JPanel titleAuthor = new JPanel(new GridLayout(0,2, 10,10));
		
		//Title//
		JPanel titlePane = new JPanel(new BorderLayout(10,10));
		titlePane.add(getTitleLabel(), BorderLayout.WEST);
		titlePane.add(getTitleField(), BorderLayout.CENTER);
		
		
		//Author//	
		JPanel authorPane = new JPanel(new BorderLayout(10,10));
		
		authorPane.add(getAuthorLabel(), BorderLayout.WEST);
		authorPane.add(getAuthorField(), BorderLayout.CENTER);
		
		titleAuthor.add(titlePane);
		titleAuthor.add(authorPane);
		
		tophalf.add(titleAuthor);
	}
	
	public JLabel getTitleLabel()	{
		JLabel titleLabel = new JLabel("Title: ");
		titleLabel.setFont(font);
		return titleLabel;
	}
	
	public JTextField getTitleField()	{
		title = new JTextField();
		title.setName("title");
		title.setFont(font);
		return title;
	}
	//Done title
	public JLabel getAuthorLabel()	{
		JLabel authorLabel = new JLabel("Author: ");
		authorLabel.setFont(font);
		return authorLabel;
	}
	
	public JTextField getAuthorField()	{
		author = new JTextField();
		author.setFont(font);
		author.setName("author");
		return author;
	}
	//Done author
	//Done title and author Pane
	
	//CONSTRUCT PROCESS BUTTON
	
	public void constructProcessPane(JPanel tophalf) {
		JPanel proPane = new JPanel(new BorderLayout());
		proPane.add(getProcessButton(), BorderLayout.WEST);
		tophalf.add(proPane);
		tophalf.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		add(tophalf, BorderLayout.NORTH);
	}
	
	public JButton getProcessButton()	{
		process = new JButton("Process");
		process.addActionListener(this);
		process.setEnabled(false);
		process.setFont(font);
		return process;
	}
	//Done Process Button
	
	
/********************************************************************************/	

	
	//Mutators
	public void setTextFileType(String fileType) {
		//typeComboBox.insertItemAt(String.format("%s%n", fileType), 0);
		typeComboBox.setSelectedItem(fileType);
		//typeComboBox.setSelectedIndex(0);
	}
	public void setTextFileText(String innerText) {
		textFile.setText(innerText);
	}
	public void setTitleText(String titleText) {
		title.setText(titleText);
	}
	public void setAuthorText(String authorText) {
		author.setText(authorText);
	}
	public void setProgressTextArea(String progressText)	{
		progressTextArea.setText(progressText);
	}
	public void setIndexer(InvertedIndexer indexer) {
		this.indexer = indexer;
	}
	
	//Accessors
	public String getTextFileText() {
		return textFile.getText();
	}
	public String getTitleText()	{
		return title.getText();
	}
	public String getAuthorText()	{
		return author.getText();
	}
	public String getProgressTextArea()	{
		return progressTextArea.getText();
	}
	public String getTextComboBox()	{
		String myStr;
		myStr = (String)typeComboBox.getSelectedItem();
		return myStr;
	}
	public Book getBook() {
		return this.book;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		String actionP;
		actionP = ev.getActionCommand();
		int retVal;
		File myFile;
		//Browse switches to Load File Selected State
		//opens file chooser, displays author and title
		//Results screen is cleared, process button is enabled.
		//Process switches to Load Summary State
		//textFile, title, author text is cleared
		//display results, disable process button
		
		if(actionP == "Browse")	{
			fileChooser myChooser = new fileChooser();
			
			retVal = myChooser.showOpenDialog(null);
			
			//Open
			
			if(retVal == JFileChooser.APPROVE_OPTION)	{
				myFile = myChooser.getSelectedFile();
				setTextFileText(myFile.getPath());
				setTextFileType(myChooser.getFileFilter().getDescription());
					
				//TO BE ADDED
				// Extracts author, title, whole document from **START marker
				// to **END marker
				ExtractBook extractor = new ExtractBook(myFile.getPath());
				book = extractor.getBook();
				//extractTitle();
				this.setTitleText(book.getTitle());
				//extractAuthor();
				this.setAuthorText(book.getAuthor());
				//extract the summary results
				setProgressTextArea(null);
			
			}
			
			//Cancel 
			else if(retVal == JFileChooser.CANCEL_OPTION) {
				// System.out.println("closed the file chooser");
				myFile = myChooser.getSelectedFile();
				myChooser.setSelectedFile(myFile);
				if( myFile == null)
					setTextFileText("");
				}
			
			// System.out.println(getTextFileText());
			if (getTextFileText().isEmpty())	{
				process.setEnabled(false);
				// System.out.println("No file chosen");
			}
			else	{
				process.setEnabled(true);				
			}
		
			//setResultstext("");
		}
		else if(actionP == "Process")	{
			// System.out.println(" files are loaded and the UI must switch to the Load Summary state. ");
			process.setEnabled(false); 
			String ttle = getTitleText() == "" ? "None" : getTitleText();
			String auth = getAuthorText() == "" ? "Anonymous" : getAuthorText();
			
			book.setTitle(ttle);
			book.setAuthor(auth);
			
			setTextFileText("");
			setTitleText("");
			setAuthorText("");
			int id = indexer.addOpus(book);
			if(id >= 0) {
				setProgressTextArea(indexer.getLoadDetails());
				sumDocTextArea.setText(null);
				sumDocTextArea.setText(indexer.getSumDetails());
			}
				
		}
		
		// printEventInfo(ev);
		
	}
	
	public void printEventInfo(ActionEvent ev) {
		System.out.println("Action command is " + ev.getActionCommand());
		System.out.println(ev.getID());
		System.out.println(ev.getSource());
		System.out.println(ev.toString());
		System.out.println(ev.getClass());
	}
	
}