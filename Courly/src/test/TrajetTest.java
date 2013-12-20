package test;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import model.Trajet;
import model.Troncon;

import org.junit.Before;
import org.junit.Test;

public class TrajetTest {
	private Trajet trajet = null;

	@Before
	public void setUp() throws Exception {
		trajet = new Trajet();
		Troncon t1 = new Troncon();
		t1.setVitesse(2.0);
		t1.setLongueur(20.0);
		Troncon t2 = new Troncon();
		t2.setVitesse(3.0);
		t2.setLongueur(18.0);
		ArrayList<Troncon> ts = new ArrayList<Troncon>();
		ts.add(t1);
		ts.add(t2);
		trajet.setTroncons(ts);
	}

	@Test
	public void testGetTempsTrajet() {
		if ( trajet.getTempsTrajet() != 16.0 ){
			fail("Erreur temps trajet.");
		}
	}

}
