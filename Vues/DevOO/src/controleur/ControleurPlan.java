/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controleur;

import devoo.MainFrame;
import devoo.Noeud;
import view.VueNoeud;
import view.VuePlan;

/**
 *
 * @author tanguyhelesbeux
 */
public class ControleurPlan {
    
    private VuePlan vuePlan;
    private MainFrame controleurParent;

    public void setVuePlan(VuePlan vuePlan) {
        this.vuePlan = vuePlan;
    }

    public void setControleurParent(MainFrame controleurParent) {
        this.controleurParent = controleurParent;
    }
    
    
    public void addNoeud(Noeud noeud) {
        VueNoeud vueNoeud = new VueNoeud();
        vueNoeud.setNoeud(noeud);
        this.vuePlan.addNoeud(vueNoeud);
    }
    
    public void didSelectVueNoeud(VueNoeud selectedNoeud) {
        Noeud noeud = selectedNoeud.getNoeud();
        
        this.controleurParent.didSelectNoeud(noeud);
    }
    
}
