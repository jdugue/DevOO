package test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;

import model.PlageHoraire;
import model.Tournee;

import org.junit.Before;
import org.junit.Test;

public class TourneeTest {
	private Tournee tournee = null;
	private ArrayList<PlageHoraire> plages = new ArrayList<PlageHoraire>();

	@Before
	public void setUp() throws Exception {
		tournee = new Tournee();
		
		
	}

	@Test
	public void testGetFirstPlageHoraire() {
		PlageHoraire plage1 = new PlageHoraire();
		Date first = new Date();
		first.setHours(10);
		plage1.setHeureDebut( first );
		
		PlageHoraire plage2 = new PlageHoraire();
		Date second = new Date();
		second.setHours(11);
		plage2.setHeureDebut( second );
		
		PlageHoraire plage3 = new PlageHoraire();
		Date third = new Date();
		third.setHours(12);
		plage3.setHeureDebut( third );
		
		plages.add(plage1);
		plages.add(plage2);
		plages.add(plage3);
		
		tournee.setPlagesHoraire(plages);
		
		if( tournee.getFirstPlageHoraire().getHeureDebut() != first ){
			fail("Mauvaise plage horaire en premier.");
		}
	}

}
