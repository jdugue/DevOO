/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controleur.ControleurPlan;
import java.util.ArrayList;

/**
 *
 * @author tanguyhelesbeux
 */
public class VuePlan extends javax.swing.JPanel {
    
    private ControleurPlan controleur;

    public void setControleur(ControleurPlan controleur) {
        this.controleur = controleur;
    }

    public ControleurPlan getControleur() {
        return controleur;
    }
    
    public void addVueNoeud(VueNoeud vueNoeud) {
        this.add(vueNoeud);
        this.displayVueNoeud(vueNoeud);
    }
    
    private void displayVueNoeud(VueNoeud vueNoeud) {
            vueNoeud.setPlan(this);
            vueNoeud.setVisible(true);
    }

    /**
     * Creates new form VuePlan
     */
    public VuePlan() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
