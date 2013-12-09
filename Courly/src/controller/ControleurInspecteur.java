/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import javax.swing.JScrollPane;
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
    
    
    
}
