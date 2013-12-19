package controller;

import java.util.ArrayList;

import javax.swing.JScrollPane;

import model.Depot;
import model.Lieu;
import model.Livraison;
import model.Noeud;
import model.PlageHoraire;
import view.VueFenetrePrincipale;
import view.VueFenetrePrincipale.MessageType;
import view.VueInspecteur;

/**
 * Le controleur de la partie affichant les informations des noeuds/livraisons
 * @author tanguyhelesbeux
 */
public class ControleurInspecteur {

	/**
	 * La vue correspondante à l'inspecteur
	 */
	private final VueInspecteur vue;
	
	/**
	 * Le controleur principal 
	 */
	private final ControleurFenetrePrincipale controleurParent;
	
	/**
	 * Le noeud a afficher par la vue 
	 */
	private Noeud noeud;
	
	/**
	 * Le Lieu a afficher par la vue
	 */
	private Lieu lieu;

	public ControleurInspecteur(JScrollPane scrollPane, ControleurFenetrePrincipale controleurParent) {
		this.controleurParent = controleurParent;
		this.vue = new VueInspecteur(this);

		scrollPane.setViewportView(this.vue);
	}

	/**
	 * Set les plagesHoraires de la vue
	 * @param plagesHoraires : Les plages horaires à setter
	 */
	public void setPlagesHoraires(ArrayList<PlageHoraire> plagesHoraires) {
		this.vue.setPlagesHoraires(plagesHoraires);
	}


        /**
         * Acutalise la vueInspecteur en fonction du Noeud
         * @param noeud : Noeud à afficher
         */
	public void setVueFromNoeud(Noeud noeud) {
		this.noeud = noeud;
		this.vue.setNoeud(noeud);        
		if (noeud != null && this.controleurParent.canCreateLivraison()) {
			this.vue.setMode(VueInspecteur.AffichageMode.NoeudSelected);
		} else if (noeud != null) {
			this.vue.setMode(VueInspecteur.AffichageMode.NoeudOnly);
		} else {
			this.vue.setMode(VueInspecteur.AffichageMode.Empty);
		}

	}

        /**
         * Actualise la vueInspecteur en fonction du lieu
         * @param lieu : Lieu à afficher
         */
	public void setVueFromLieu(Lieu lieu) {
		this.lieu = lieu;
		this.vue.setLieu(lieu);
		if (lieu != null) {
			this.vue.setNoeud(lieu.getNoeud());
			if (lieu.getClass() == Livraison.class) {
				this.vue.setMode(VueInspecteur.AffichageMode.LivraisonSelected);
			} else if (lieu.getClass() == Depot.class) {
				this.vue.setMode(VueInspecteur.AffichageMode.DepotSelected);
			}
		}  else {
			this.vue.setMode(VueInspecteur.AffichageMode.NoeudOnly);
		}           
	}

        /**
         * Appelé lorsque la vue demande de créer une nouvelle livraison.
         * @param newClient : ID du client de la nouvelle livraison
         * @param plageHoraire : PlageHoraire de la nouvelle livraison
         * @return Succès ? true sinon false
         */
	public boolean shouldCreateLivraison(String newClient, PlageHoraire plageHoraire) {
		Integer client;        
		try {
			client = Integer.parseInt(newClient);
		} catch (NumberFormatException e) {
			showMessage("Le numéro client doit être un entier", MessageType.MessageTypeError);
			return false;
		}
		Livraison livraison = new Livraison(this.noeud, this.noeud.getId(), client, plageHoraire);
		controleurParent.shouldAddLivraisonAndReload(livraison);
		return true;
	}

        /**
         * Appelé lorsque la vue demande de supprimer une livraison.
         * @return Succès ? true sinon false
         */
	public boolean shouldRemoveLivraison() {
		this.vue.setMode(VueInspecteur.AffichageMode.NoeudSelected);
		this.controleurParent.shouldRemoveLivraisonAndReload((Livraison)this.lieu);
		return true;
	}

        /**
         * Demande au controleur parent d'afficher un message.
         * @param message : Message à afficher
         * @param type : Type de message
         */
	public void showMessage(String message, VueFenetrePrincipale.MessageType type) {
		this.controleurParent.showMessage(message, type);
	}
}
