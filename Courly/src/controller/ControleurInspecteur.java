/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.ArrayList;
import javax.swing.JScrollPane;
import model.Lieu;

import model.Livraison;
import model.Noeud;
import model.PlageHoraire;
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
    }
    
    public void setVueFromLieu(Lieu lieu) {
        this.lieu = lieu;
        this.vue.setLieu(lieu);
    }
    
    public boolean shouldEditLivraison() {
        this.vue.setMode(VueInspecteur.AffichageMode.LivraisonEdit);        
        return true;
    }
    
    public boolean shouldCreateLivraison(String newClient, PlageHoraire plageHoraire) {
        this.vue.setMode(VueInspecteur.AffichageMode.LivraisonSelected);
        Livraison livraison = new Livraison(this.noeud, this.noeud.getId(), Integer.parseInt(newClient), plageHoraire);//Livraison.createLivraison( newClient, this.noeud.getId(), plageHoraire);
        if ( livraison == null ){
            return false;
        } else {
            controleurParent.shouldAddLivraisonAndReload(livraison);
            return true;
        }
    }
    
    public boolean shouldCancelLivraisonEdit() {
        this.vue.setMode(VueInspecteur.AffichageMode.LivraisonSelected);
        this.vue.setNoeud(this.noeud);
        return true;
    }
    
    public boolean canEditLivraison() {
        this.vue.setMode(VueInspecteur.AffichageMode.LivraisonEdit);
        return true;
    }
}
