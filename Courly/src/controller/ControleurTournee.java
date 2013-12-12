package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.Lieu;
import model.Livraison;
import model.PlageHoraire;
import model.Tournee;

public class ControleurTournee {
	
	private final String TITRE = "Feuille de route";
	private final String PLAGE_HORAIRE = "Plage horaire";
	private final String LIVRAISON = "Livraison";
	private final String CLIENT = "Client";
	private final String ADRESSE = "Adresse";
	private final String HEURE_PASSAGE = "Heure de passage";
	private final String ESP = " ";
	private final String DP = ":";
	private final String RC = "\n";
	private final String TAB = "\t";
	
	private final String EXT = ".txt";
	
	private final int INDENTATION_TITRE = 4;
	private final int INDENTATION_PLAGE_HORAIRE = 1;
	private final int INDENTATION_LIVRAISON = 2;
	private final int INDENTATION_INFO = 3;

	public ControleurTournee() {
		// TODO Auto-generated constructor stub
	}

	public boolean tourneeToTxt (Tournee aEcrire, String filename){
		filename = fixExtension(filename);
		try {
			 
			int compteurLivraison = 1;			
 
			File file = new File(filename);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write( getRepetition(INDENTATION_TITRE, TAB) + TITRE + RC );
			bw.write( RC );
			
			for ( PlageHoraire plage : aEcrire.getPlagesHoraire() ){
				bw.write( getRepetition(INDENTATION_PLAGE_HORAIRE, TAB) + PLAGE_HORAIRE + DP + ESP );
				bw.write( plage.getHeureDebut() + " � " + plage.getHeureFin() + RC);
				
				for ( Livraison livraison : plage.getLivraisons() ){
					bw.write( getRepetition(INDENTATION_LIVRAISON, TAB) + 
							LIVRAISON + ESP + compteurLivraison + RC);
					
					bw.write ( getRepetition(INDENTATION_INFO, TAB) + 
							CLIENT + DP + ESP + livraison.getClient() + RC );
					
					bw.write ( getRepetition(INDENTATION_INFO, TAB) + 
							ADRESSE + DP + ESP + livraison.getAdresse() + RC );
					
					bw.write ( getRepetition(INDENTATION_INFO, TAB) + 
							HEURE_PASSAGE + DP + ESP + livraison.getHeurePassage() + RC );
					
					compteurLivraison++;
				}
			}
	
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private String getRepetition ( int nbFois, String aRepeter ){
		String repetition = "";
		for ( int i = 0; i<nbFois; i++ ){
			repetition += aRepeter;
		}
		return repetition;
	}
	
	private String fixExtension ( String file ){
		if ( !file.endsWith( EXT ) ){
			file += EXT;
		}
		return file;
	}
}
