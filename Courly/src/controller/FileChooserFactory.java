package controller;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooserFactory {

	public FileChooserFactory() {
	}
	
	public static JFileChooser createFileChooser ( String extension, 
			String filetypeName, File currentDirectory  ){
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(filetypeName, extension);
		JFileChooser fChooser = new JFileChooser();
		fChooser.setFileFilter(filter);
        fChooser.setCurrentDirectory( currentDirectory );
        return fChooser;
	}

}
