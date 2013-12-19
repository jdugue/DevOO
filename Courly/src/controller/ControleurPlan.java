/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.awt.Color;
import model.Noeud;
import model.Plan;
import model.Troncon;
import model.Livraison;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JScrollPane;
import model.Lieu;
import model.PlageHoraire;
import model.Tournee;
import model.Trajet;

import view.VueDepot;
import view.VueLieu;
import view.VueLivraison;
import view.VueNoeud;
import view.VuePlan;
import view.VueTroncon;

/**
 *
 * @author tanguyhelesbeux
 */
public class ControleurPlan {

	//private JScrollPane scrollPane;
	private VuePlan vuePlan;
	private Noeud selectedNoeud;

	private final ControleurFenetrePrincipale controleurParent;

	public ControleurPlan(JScrollPane scrollPane, ControleurFenetrePrincipale controleurFenetreParent) {
		this.setVuePlan(new VuePlan());

		scrollPane.setViewportView(this.vuePlan);

		this.controleurParent = controleurFenetreParent;
	}

	public ControleurFenetrePrincipale getControleurParent() {
		return controleurParent;
	}

	public final void setVuePlan(VuePlan vuePlan) {
		this.vuePlan = vuePlan;
		this.vuePlan.setControleur(this);
	} 

	public void setTournee(Tournee tournee) {
		this.vuePlan.setTournee(tournee);
		this.deselectAll();
	}

	public void loadVuePlanFromModel(Plan aPlan) {
		//aPlan.trierNoeudsParY();
		this.vuePlan.setPlan(aPlan);
		this.vuePlan.setTournee(null);
	}

	public void setSelectedNoeud(Noeud noeud) {
		if (this.selectedNoeud != null) {
			this.vuePlan.setNoeudSelected(this.selectedNoeud, false);
		}
		if (noeud != null) {
			this.vuePlan.setNoeudSelected(noeud, true);
		}
		this.selectedNoeud = noeud;
	}

	public void deselectAll() {
		this.setSelectedNoeud(null);
	}

	public void didSelectNoeud(Noeud noeud) {
		this.setSelectedNoeud(noeud);
		this.controleurParent.didSelectNoeud(noeud);
	}

	public void didSelectLieu(Lieu lieu) {
		this.setSelectedNoeud(lieu.getNoeud());
		this.controleurParent.didSelectLieu(lieu);
	}

	void addAllNoeuds(ArrayList<Noeud> noeuds) {
		this.vuePlan.addAllNoeuds(noeuds);
	}

	void addTroncon(Troncon troncon) {
		this.vuePlan.addTroncon(troncon);
	}

	void setZoomScale(double zoomScale) {
		this.vuePlan.setZoomScale(zoomScale);
	}

}
