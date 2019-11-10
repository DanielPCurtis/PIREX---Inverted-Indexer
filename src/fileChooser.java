/*		File Chooser
 *
 * Created by: Daniel Curtis  
 * 04/27/2019
 * 
 */


import java.awt.TextField;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import PirexIO.FindDir;



public class fileChooser extends JFileChooser{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9022467553200685415L;
	private static File pirexData = new File(FindDir.getCurrentDirectory());
	
	public fileChooser()	{
		super(pirexData);
		buildFileChooser();	
		
	}
	
	
	public void buildFileChooser()	{
		
		FileFilter projGutFilter = new FileNameExtensionFilter("Project Gutenberg File", "txt");
		FileFilter rtfFilter = new FileNameExtensionFilter("Rich Text Format", "rtf");
		FileFilter htmlFilter = new FileNameExtensionFilter("HTML", "html");
		
		setFileFilter(htmlFilter);
		setFileFilter(rtfFilter);
		setFileFilter(projGutFilter);
		setAcceptAllFileFilterUsed(false);
		
	}
	
}
