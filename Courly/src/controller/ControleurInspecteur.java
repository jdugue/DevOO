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
            // Noeud ok
            
            this.vue.setAdresse(Integer.toString(noeud.getId()));
            if (noeud.getLieu() != null) {
                
                // Le noeud a un lieu
                this.vue.setLivraisonEnabled(false);
                Lieu lieu = noeud.getLieu();
                
                if (lieu.getClass() == Livraison.class) {
                    
                    // le lieu est une livraison
                    this.vue.setLivraison((Livraison)lieu);
                } else if (lieu.getClass() == Depot.class) {
                    // le lieu est un depot
                    this.vue.cleanLivraison();
                }
            } else {
                
                // le noeud n'a pas de lieu
                this.vue.setLivraisonEnabled(true);
                this.vue.cleanLivraison();
            }
        } else {
            
            // noeud null
            this.vue.setAdresse("");
            this.vue.cleanLivraison();
        }
    }
    
}
