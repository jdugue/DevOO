/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import model.Noeud;
import model.Plan;
import javax.swing.JScrollPane;
import model.Lieu;
import model.Tournee;
import view.VuePlan;

/**
 * Manipule et controle une VuePlan.
 * Gère la sélection des noeuds. Multi-sélection non prise en charge.
 * @author tanguyhelesbeux
 */
public class ControleurPlan {

	//private JScrollPane scrollPane;
	private VuePlan vuePlan;
        
        /**
         * Noeud actuellement sélectionné
         */
	private Noeud selectedNoeud;

	private final ControleurFenetrePrincipale controleurParent;

	public ControleurPlan(JScrollPane scrollPane, ControleurFenetrePrincipale controleurFenetreParent) {
		this.setVuePlan(new VuePlan());

		scrollPane.setViewportView(this.vuePlan);

		this.controleurParent = controleurFenetreParent;
	}

	private void setVuePlan(VuePlan vuePlan) {
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

        /**
         * Sélectionne le noeud passé en paramêtre. Si un noeud est déjà sélectionné il est déselectionné.
         * @param noeud : Noeud à sélectionné
         */
	public void setSelectedNoeud(Noeud noeud) {
		if (this.selectedNoeud != null) {
			this.vuePlan.setNoeudSelected(this.selectedNoeud, false);
		}
		if (noeud != null) {
			this.vuePlan.setNoeudSelected(noeud, true);
		}
		this.selectedNoeud = noeud;
	}

        /**
         * Déselectionne tous les noeuds.
         */
	public void deselectAll() {
		this.setSelectedNoeud(null);
	}

        /**
         * Appelé par la VuePlan. Signale qu'un noeud vient d'être sélectionné
         * @param noeud : noeud sélectionné
         */
	public void didSelectNoeud(Noeud noeud) {
		this.setSelectedNoeud(noeud);
		this.controleurParent.didSelectNoeud(noeud);
	}

        /**
         * Appelé par la VuePlan. Signale qu'un lieu vient d'être sélectionné
         * @param lieu : lieu sélectionné
         */
	public void didSelectLieu(Lieu lieu) {
		this.setSelectedNoeud(lieu.getNoeud());
		this.controleurParent.didSelectLieu(lieu);
	}

        /**
         * Modifie la valeur de zoom du plan
         * @param zoomScale : nouvelle valeur de zoom
         */
	void setZoomScale(double zoomScale) {
		this.vuePlan.setZoomScale(zoomScale);
	}

}
