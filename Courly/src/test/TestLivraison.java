/**
 * 
 */
package test;

import static org.junit.Assert.fail;

import java.util.Date;

import model.Livraison;
import model.PlageHoraire;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

/**
 * @author Ianic
 *
 */
public class TestLivraison {
	
	private Livraison livraison = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		livraison = new Livraison();
	}


	/**
	 * Test method for {@link model.Livraison#estValide()}.
	 */
	@Test
	public void testEstValide() {
		PlageHoraire plageHoraire = new PlageHoraire();
		Date fin = new Date();
		fin.setHours(9);
		
		Date avantFin = new Date();
		avantFin.setHours(8);

		Date apresFin = new Date();
		apresFin.setHours(10);
		
		plageHoraire.setHeureFin(fin);
		livraison.setPlageHoraire(plageHoraire);
		livraison.setHeureArrivee(avantFin);
		if ( !livraison.estValide() ){
			fail("Devrait etre valide");
		}
		
		livraison.setHeureArrivee(apresFin);
		if( livraison.estValide() ){
			fail("Ne devrait pas etre valide");
		}

	}


}
