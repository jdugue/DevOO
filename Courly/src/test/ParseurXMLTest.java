package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import model.Plan;
import model.Tournee;

import org.junit.Test;
import org.xml.sax.SAXException;

import controller.ParseurXML;

public class ParseurXMLTest {

	@Test
	public void testConstruirePlanXML() {
		ParseurXML parseur = new ParseurXML();

		//Plan 3x3
		//9 Noeuds
		//23 Tronçons
		String cheminFichierPlan = "../XML Examples/Tests/plan_test_ok.xml";

		try {
			Plan plan = parseur.construirePlanXML(cheminFichierPlan);
			Integer nbNoeuds = 9;
			Integer nbTroncons = 23;

			if (plan.getNoeuds().size()!=nbNoeuds){
				fail("Mauvais parsage, nombre de Noeuds générés incorrect");
			}
			if (plan.getTroncons().size()!=nbTroncons) {
				fail("Mauvais parsage, nombre de Tronçons générés incorrect");

			}

		} catch (FileNotFoundException e) {
			fail("Fichier inexistant");
		} catch (SAXException e) {
			fail("Erreur de parsage");
		}

	}

	@Test
	public void testConstruireTourneeXML() {
		ParseurXML parseur = new ParseurXML();

		//Tournee 3x3
		//4 Livraisons
		//1 PlageHoraire
		String cheminFichierLivraison = "../XML Examples/Tests/livraison_test_ok.xml";
	
		Integer nbLivraisons = 4;
		Integer nbPlages = 1;
		
		try {
			Tournee tournee = parseur.construireTourneeXML(cheminFichierLivraison);
		
			if(tournee.getLivraisons().size()!=nbLivraisons){
				fail("Mauvais parsage, nombre de Livraisons générées incorrect");
			}
			if(tournee.getPlagesHoraire().size()!=nbPlages){
				fail("Mauvais parsage, nombre de PlagesHoraires générées incorrect");
			}
		} catch (ParseException e) {
			fail("Erreur de parsage");
		} catch (ParserConfigurationException e) {
			fail("Erreur de parsage");
		} catch (SAXException e) {
			fail("Erreur de parsage");
		} catch (IOException e) {
			fail("Erreur IO");
		}
	}

}
