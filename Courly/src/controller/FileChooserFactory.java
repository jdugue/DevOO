package controller;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Ianic
 * Classe qui crée des {@link JFileChooser} paramétrés.
 */
public class FileChooserFactory {

	public FileChooserFactory() {
	}
	
	/**
	 * @param extension Le type de fichier à être pris par le filtre.
	 * @param filetypeName Le nom du type de fichier utilisé.
	 * @param currentDirectory Le dossier à démarrer le {@link JFileChooser}.
	 * @return L'instance de {@link JFileChooser} créé à partir des paramètres.
	 */
	public static JFileChooser createFileChooser ( String extension, 
			String filetypeName, File currentDirectory  ){
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(filetypeName, extension);
		JFileChooser fChooser = new JFileChooser();
		fChooser.setFileFilter(filter);
        fChooser.setCurrentDirectory( currentDirectory );
        return fChooser;
	}

}
