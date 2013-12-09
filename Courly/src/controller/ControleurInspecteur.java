/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import javax.swing.JScrollPane;
import model.Lieu;
import model.Noeud;
import model.Livraison;
import model.Depot;
import view.VueInspecteur;

/**
 *
 * @author tanguyhelesbeux
 */
public class ControleurInspecteur {
    
    private VueInspecteur vue;
    private ControleurFenetrePrincipale controleurParent;

    public ControleurInspecteur(JScrollPane scrollPane, ControleurFenetrePrincipale controleurParent) {
        this.controleurParent = controleurParent;
        this.vue = new VueInspecteur(this);
        
        scrollPane.setViewportView(this.vue);
    }
    
    
    
    public void setVueFromNoeud(Noeud noeud) {
        if (noeud != null) {
            this.vue.setAdresse(Integer.toString(noeud.getId()));
            if (noeud.getLieu() != null) {
                Lieu lieu = noeud.getLieu();
                if (lieu.getClass() == Livraison.class) {
                    this.vue.setLivraison((Livraison)lieu);
                } else if (lieu.getClass() == Depot.class) {
                    
                }
            }
        } else {
            this.vue.setAdresse("");
        }
    }
    
}
