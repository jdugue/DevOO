/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.ArrayList;
import javax.swing.JScrollPane;
import model.Lieu;

import model.Depot;
import model.Livraison;
import model.Noeud;
import model.PlageHoraire;
import view.VueFenetrePrincipale.MessageType;
import view.VueFenetrePrincipale;
import view.VueInspecteur;

/**
 *
 * @author tanguyhelesbeux
 */
public class ControleurInspecteur {
    
    private VueInspecteur vue;
    private ControleurFenetrePrincipale controleurParent;
    private Noeud noeud;
    private Lieu lieu;

    public ControleurInspecteur(JScrollPane scrollPane, ControleurFenetrePrincipale controleurParent) {
        this.controleurParent = controleurParent;
        this.vue = new VueInspecteur(this);
        
        scrollPane.setViewportView(this.vue);
    }
    
    public void setPlagesHoraires(ArrayList<PlageHoraire> plagesHoraires) {
        this.vue.setPlagesHoraires(plagesHoraires);
    }
    
    
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
    
    public boolean shouldCancelLivraisonEdit() {
        this.vue.setMode(VueInspecteur.AffichageMode.LivraisonSelected);
        this.vue.setNoeud(this.noeud);
        return true;
    }
    
    public boolean shouldRemoveLivraison() {
        this.vue.setMode(VueInspecteur.AffichageMode.NoeudSelected);
        this.controleurParent.shouldRemoveLivraisonAndReload((Livraison)this.lieu);
        return true;
    }
    
    public void showMessage(String message, VueFenetrePrincipale.MessageType type) {
        this.controleurParent.showMessage(message, type);
    }
}
