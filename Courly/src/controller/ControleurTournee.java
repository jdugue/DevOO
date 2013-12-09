package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.Tournee;

public class ControleurTournee {

	public ControleurTournee() {
		// TODO Auto-generated constructor stub
	}

	public boolean tourneeToTxt (Tournee aEcrire, String filename){
		try {
			 
			String content = "This is the content to write into file";
 
			File file = new File(filename);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
