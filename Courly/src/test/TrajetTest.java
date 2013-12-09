package test;

import static org.junit.Assert.*;

import model.Trajet;
import model.Troncon;

import org.junit.Test;

public class TrajetTest {

	@Test
	public void testGetTempsTrajet() {
		
		
		
		Trajet trajet = new Trajet();
		
		Troncon troncon1 = new Troncon();
		troncon1.setLongueur(1.0);
		troncon1.setVitesse(2.0);
		
		Troncon troncon2 = new Troncon();
		troncon2.setLongueur(1.1);
		troncon2.setVitesse(1.0);
		
		Troncon troncon3 = new Troncon();
		troncon3.setLongueur(10.2);
		troncon3.setVitesse(2.3);
		
		double cost1 = troncon1.getCost();
		double cost2 = troncon2.getCost();
		double cost3 = troncon3.getCost();
		
		double res = troncon1.getLongueur()/troncon1.getVitesse() + troncon2.getLongueur()/troncon2.getVitesse()+troncon3.getLongueur()/troncon3.getVitesse();
		
		if(res != cost1+cost2+cost3) {
			fail("Somme différente");
		}
		
		
		
	}

}
