package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import model.Plan;

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
		fail("Not yet implemented");
	}

}
