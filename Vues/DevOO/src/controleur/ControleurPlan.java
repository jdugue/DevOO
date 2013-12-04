/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controleur;

import devoo.MainFrame;
import devoo.Noeud;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import view.VueNoeud;
import view.VuePlan;

/**
 *
 * @author tanguyhelesbeux
 */
public class ControleurPlan {
    
    private VuePlan vuePlan;
    private MainFrame fenetreParent;
    
    private VueNoeud selectedVueNoeud;

    public ControleurPlan(Point vueLocation, Dimension vueDimension, MainFrame fenetreParent) {
        this.vuePlan = new VuePlan();
        this.vuePlan.setSize(vueDimension);
        this.vuePlan.setLocation(vueLocation);
        this.setFenetreParent(fenetreParent);
        this.fenetreParent.add(vuePlan);
    }
    
    public ControleurPlan(VuePlan vuePlan, MainFrame fenetreParent) {
        this.setVuePlan(vuePlan);
        this.setFenetreParent(fenetreParent);
    }

    private void setFenetreParent(MainFrame fenetreParent) {
        this.fenetreParent = fenetreParent;
    }    
    
    public void setVuePlan(VuePlan vuePlan) {
        this.vuePlan = vuePlan;
        this.vuePlan.setControleur(this);
    }   

    public VueNoeud getSelectedVueNoeud() {
        return selectedVueNoeud;
    }

    public void setSelectedVueNoeud(VueNoeud selectedVueNoeud) {
        this.selectedVueNoeud = selectedVueNoeud;
    }
    
    
    public void addNoeud(Noeud noeud) {
        VueNoeud vueNoeud = new VueNoeud();
        vueNoeud.setNoeud(noeud);
        this.vuePlan.addVueNoeud(vueNoeud);
    }
    
    public void addAllNoeuds(ArrayList<Noeud> noeuds) {
        for (Noeud noeud : noeuds) {
            this.vuePlan.addVueNoeud(new VueNoeud(noeud));
        }
    }
    
    public void didSelectVueNoeud(VueNoeud selectedVueNoeud) {
        if (this.getSelectedVueNoeud() != null) {
            this.getSelectedVueNoeud().setSelected(false);
        }
        this.setSelectedVueNoeud(selectedVueNoeud);
        
        Noeud noeud = selectedVueNoeud.getNoeud();
        this.fenetreParent.didSelectNoeud(noeud);
    }
    
    public void didDeselectVueNoeud(VueNoeud deselectedNoeud) {
        Noeud noeud = deselectedNoeud.getNoeud();
        this.fenetreParent.didDeselectNoeud(noeud);
    }
    
}
