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
import view.VueNoeud;
import view.VuePlan;

/**
 *
 * @author tanguyhelesbeux
 */
public class ControleurPlan {
    
    private VuePlan vuePlan;
    private MainFrame fenetreParent;

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
    
    public void addNoeud(Noeud noeud) {
        VueNoeud vueNoeud = new VueNoeud();
        vueNoeud.setNoeud(noeud);
        this.vuePlan.addNoeud(vueNoeud);
    }
    
    public void didSelectVueNoeud(VueNoeud selectedNoeud) {
        Noeud noeud = selectedNoeud.getNoeud();
        this.fenetreParent.didSelectNoeud(noeud);
    }
    
}
