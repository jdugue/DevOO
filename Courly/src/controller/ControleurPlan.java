/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.awt.Color;
import model.Noeud;
import model.Plan;
import model.Troncon;
import model.Livraison;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JScrollPane;
import model.PlageHoraire;
import model.Tournee;
import model.Trajet;

import view.VueDepot;
import view.VueLivraison;
import view.VueNoeud;
import view.VuePlan;
import view.VueTroncon;

/**
 *
 * @author tanguyhelesbeux
 */
public class ControleurPlan {
    
    //private JScrollPane scrollPane;
    private VuePlan vuePlan;
    private Plan plan = new Plan();
    private Tournee tournee = new Tournee();
    private final HashMap<Noeud, VueNoeud> vueNoeuds = new HashMap<Noeud, VueNoeud>();
    private final HashMap<Troncon, VueTroncon> vueTroncons = new HashMap<Troncon, VueTroncon>();
    
    private static ArrayList<Color> plageHoraireColors;
    
    public static final int noeudSize = 14;
    public static final int padding = 30;
    
    private int minX, minY, maxX, maxY;
    
    protected double zoomScale = 1.0;
    
    private final ControleurFenetrePrincipale controleurParent;
    
    private VueNoeud selectedVueNoeud;
    private Noeud selectedNoeud;

    /*public ControleurPlan(Point vueLocation, Dimension vueDimension, FenetrePrincipale fenetreParent) {
        this.vuePlan = new VuePlan();
        this.vuePlan.setSize(vueDimension);
        this.vuePlan.setLocation(vueLocation);
        this.setFenetreParent(fenetreParent);
        this.fenetreParent.add(vuePlan);
    }*/
    
    /*public ControleurPlan(VuePlan vuePlan, FenetrePrincipale fenetreParent) {
        this.setVuePlan(vuePlan);
        this.scrollPane = new JScrollPane();
        this.scrollPane.setViewportView(this.vuePlan);
        this.scrollPane.setLocation(0, 80);
        this.scrollPane.setSize(this.vuePlan.getSize());
        this.vuePlan.setSize(1000, 1000);
        this.setFenetreParent(fenetreParent);
        this.fenetreParent.add(this.scrollPane);
    }*/
    
    public ControleurPlan(JScrollPane scrollPane, ControleurFenetrePrincipale controleurFenetreParent) {
        this.setVuePlan(new VuePlan());
        
        scrollPane.setViewportView(this.vuePlan);
        
        this.controleurParent = controleurFenetreParent;
        this.initPlageHoraireColors();
    }
    
    private void initPlageHoraireColors() {
        ControleurPlan.plageHoraireColors = new ArrayList<Color>();
        
        plageHoraireColors.add(Color.green);
        plageHoraireColors.add(Color.blue);
        plageHoraireColors.add(Color.pink);
        plageHoraireColors.add(Color.black);
        plageHoraireColors.add(Color.yellow);
    }

    public ControleurFenetrePrincipale getControleurParent() {
        return controleurParent;
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
        this.selectedNoeud = selectedVueNoeud.getNoeud();
    }

    private void setMinX(int minX) {
        this.minX = minX;
        this.updateVuePlanFrame();
    }

    private void setMinY(int minY) {
        this.minY = minY;
        this.updateVuePlanFrame();
    }

    private void setMaxX(int maxX) {
        this.maxX = maxX;
        this.updateVuePlanFrame();
    }

    private void setMaxY(int maxY) {
        this.maxY = maxY;
        this.updateVuePlanFrame();
    }

    public void setTournee(Tournee tournee) {
        this.tournee = tournee;
    }
    
    
    private void updateVuePlanFrame() {
        Dimension dimension = new Dimension(this.scaledSize(maxX) + padding*2, this.scaledSize(maxY) + padding*2);
        this.vuePlan.setPreferredSize(dimension);
    }

    public double getZoomScale() {
        return zoomScale;
    }

    public void setZoomScale(double zoomScale) {
        this.zoomScale = zoomScale;
        this.updateVuePlanFrame();
        this.paint();
    }
    
    
    public void createVueNoeudFromNoeud(Noeud noeud) {
    	
    	// Update content frame
        if (this.plan.getNoeuds().isEmpty()) {
            this.setMaxX(noeud.getX());
            this.setMaxY(noeud.getY());
            this.setMinX(noeud.getX());
            this.setMinY(noeud.getY());
        } else {
            if (noeud.getX() > this.maxX) {
                this.setMaxX(noeud.getX());
            }
            if (noeud.getY() > this.maxY) {
                this.setMaxY(noeud.getY());
            }
            if (noeud.getX() < this.minX) {
                this.setMinX(noeud.getX());
            }
            if (noeud.getY() < this.minY) {
                this.setMinY(noeud.getY());
            }
        }
        
        VueNoeud vueNoeud = this.vueNoeuds.get(noeud);
        if (vueNoeud == null) {
            // Vue noeud
            vueNoeud = new VueNoeud(noeud);
            this.vueNoeuds.put(noeud, vueNoeud);

            vueNoeud.setSize(noeudSize, noeudSize);
            int xLocation = this.scaledCoordonateHorizontal(vueNoeud.getNoeud().getX()) - vueNoeud.getWidth()/2;
            int yLocation = this.scaledCoordonateVertical(vueNoeud.getNoeud().getY()) - vueNoeud.getHeight()/2;
            vueNoeud.setLocation(xLocation, yLocation);
            vueNoeud.setPlan(this.vuePlan);

            this.vuePlan.addVueNoeud(vueNoeud);
        }
        

        
          
        if (this.selectedNoeud == noeud) {
            vueNoeud.setSelected(true);
        }
    }
    
    public void addNoeud(Noeud noeud) {        
        this.plan.addNoeud(noeud);  
        this.createVueNoeudFromNoeud(noeud);
    }
    
    private int scaledCoordonateVertical(int coordonate) {
        return (int)(this.zoomScale * (coordonate - minY)) + padding;
    }
    
    private int scaledCoordonateHorizontal(int coordonate) {
        return (int)(this.zoomScale * (coordonate - minX)) + padding;
    }
    
    private int scaledSize(int size) {
        return (int)(this.zoomScale * size);
    }
    
    public void addAllNoeuds(ArrayList<Noeud> noeuds) {
        for (Noeud noeud : noeuds) {
            this.addNoeud(noeud);
        }
    }
    
    public void createVueTronconFromTroncon(Troncon troncon) {

        VueTroncon vueTroncon = this.vueTroncons.get(troncon);
        
        if (vueTroncon == null) {
            // Vue Troncon
            vueTroncon = new VueTroncon(troncon, this);
            this.vueTroncons.put(troncon, vueTroncon);

            int x = Math.min(troncon.getOrigine().getX(), troncon.getDestination().getX());
            int y = Math.min(troncon.getOrigine().getY(), troncon.getDestination().getY());
            vueTroncon.setLocation(this.scaledCoordonateHorizontal(x) - noeudSize/2, this.scaledCoordonateVertical(y) - noeudSize/2);

            int width = Math.abs(troncon.getDestination().getX() - troncon.getOrigine().getX());
            int height = Math.abs(troncon.getDestination().getY() - troncon.getOrigine().getY());

            vueTroncon.setSize(this.scaledSize(width) + noeudSize, this.scaledSize(height) + noeudSize);

            this.vuePlan.add(vueTroncon);
        } else {
           vueTroncon.setTronconRetour(troncon);
        }
        
    }
    
    public Color colorForPlageHoraire(PlageHoraire plageHoraire) {
        int plageIndex = this.tournee.getPlagesHoraire().indexOf(plageHoraire)%plageHoraireColors.size();
        
        if (plageIndex >= 0) {
            return plageHoraireColors.get(plageIndex);
        } else {
            System.out.println("Un trajet n'a pas de plage horaire !!\nIl apparait en rouge.");
            return Color.RED;
        }
    }
    
    public void addTroncon(Troncon troncon) {
        this.plan.addTroncon(troncon);
        this.createVueTronconFromTroncon(troncon);
        //this.paint();
    }
    
    public void addAllTroncons(ArrayList<Troncon> troncons) {
        for (Troncon troncon : troncons) {
            //this.addTroncon(troncon);
            //this.createVueTronconFromTroncon(troncon);
        	this.plan.addTroncon(troncon);
        }
        this.paint();
    }
    
    public void paint() {
        this.cleanVuePlan();
        
        for (Noeud noeud : this.plan.getNoeuds()) {
            this.createVueNoeudFromNoeud(noeud);
        }
        this.paintLivraisons();
        this.paintDepot();
        
        for (Troncon troncon : this.plan.getTroncons()) {
            this.createVueTronconFromTroncon(troncon);
        }
        this.paintTrajets();
        
        for (VueTroncon vueTroncon : this.vueTroncons.values())
        {
            vueTroncon.repaint();
        }
    }
    
    private void paintTrajets() {
        if (this.tournee != null) {
            if (this.tournee.getTrajets() != null) {
                for (Trajet trajet : this.tournee.getTrajets()) {
                    for (Troncon troncon : trajet.getTroncons()) {
                        VueTroncon vueTroncon = this.vueTroncons.get(troncon);

                        if (vueTroncon != null) {
                            vueTroncon.addTrajet(trajet);
                        }
                    }
                }
            }
        }
    }
    
    private void paintLivraisons() {
        if (this.tournee != null) {
            if (this.tournee.getLivraisons() != null) {
                for (Livraison livraison : this.tournee.getLivraisons()) {
                    VueNoeud vueNoeud = this.vueNoeuds.get(livraison.getNoeud());

                    if (vueNoeud != null) {
                        vueNoeud.setLieu(livraison);
                        VueLivraison vueLivraison = new VueLivraison();
                        vueNoeud.setVueLivraison(vueLivraison);
                        this.vuePlan.setComponentZOrder(vueNoeud, this.vuePlan.getComponentCount()-1);
                    }
                }
            }
        }
    }
    
    private void paintDepot() {
        if (this.tournee != null) {
            if (this.tournee.getDepot() != null) {
                VueNoeud vueNoeud = this.vueNoeuds.get(this.tournee.getDepot().getNoeud());

                if (vueNoeud != null) {
                    vueNoeud.setLieu(this.tournee.getDepot());
                    vueNoeud.setVueLieu(new VueDepot());
                    this.vuePlan.setComponentZOrder(vueNoeud, this.vuePlan.getComponentCount()-1);
                }
            }
        }
    }
    
    private void cleanVuePlan() {
        this.vuePlan.removeAll();
        this.vuePlan.updateUI();
        this.vueNoeuds.clear();
        this.vueTroncons.clear();
    }
    
    public void loadVuePlanFromModel(Plan aPlan) {
    	
    	this.plan = aPlan;   	
    	this.paint();
    }
    
    public void didSelectVueNoeud(VueNoeud selectedVueNoeud) {
        if (this.getSelectedVueNoeud() != null) {
            this.getSelectedVueNoeud().setSelected(false);
        }
        this.setSelectedVueNoeud(selectedVueNoeud);
        
        Noeud noeud = selectedVueNoeud.getNoeud();
        this.controleurParent.didSelectNoeud(noeud);
    }
    
    public void didDeselectVueNoeud(VueNoeud deselectedNoeud) {
        Noeud noeud = deselectedNoeud.getNoeud();
        this.controleurParent.didDeselectNoeud(noeud);
    }
    
}
