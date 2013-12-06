/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controleur;

import devoo.MainFrame;
import model.Noeud;
import model.Troncon;
import model.Livraison;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JScrollPane;
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
    
    private JScrollPane scrollPane;
    private VuePlan vuePlan;
    private ArrayList<VueNoeud> vueNoeuds = new ArrayList();
    private ArrayList<Noeud> noeuds = new ArrayList();
    private ArrayList<VueTroncon> vueTroncons = new ArrayList();
    private ArrayList<Troncon> troncons = new ArrayList();
    
    public static final int noeudSize = 14;
    public static final int padding = 30;
    
    private int minX, minY, maxX, maxY;
    
    protected double zoomScale = 1.0;
    
    private MainFrame fenetreParent;
    
    private VueNoeud selectedVueNoeud;
    private Noeud selectedNoeud;

    public ControleurPlan(Point vueLocation, Dimension vueDimension, MainFrame fenetreParent) {
        this.vuePlan = new VuePlan();
        this.vuePlan.setSize(vueDimension);
        this.vuePlan.setLocation(vueLocation);
        this.setFenetreParent(fenetreParent);
        this.fenetreParent.add(vuePlan);
    }
    
    public ControleurPlan(VuePlan vuePlan, MainFrame fenetreParent) {
        this.setVuePlan(vuePlan);
        this.scrollPane = new JScrollPane();
        this.scrollPane.setViewportView(this.vuePlan);
        this.scrollPane.setLocation(0, 80);
        this.scrollPane.setSize(this.vuePlan.getSize());
        this.vuePlan.setSize(1000, 1000);
        this.setFenetreParent(fenetreParent);
        this.fenetreParent.add(this.scrollPane);
    }
    
    public ControleurPlan(JScrollPane scrollPane, MainFrame fenetreParent) {
        this.setVuePlan(new VuePlan());
        this.scrollPane = scrollPane;
        
        this.vuePlan.setBackground(Color.WHITE);
        this.scrollPane.setViewportView(this.vuePlan);
        
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
        
        // Vue noeud
        VueNoeud vueNoeud = new VueNoeud(noeud);
        this.vueNoeuds.add(vueNoeud);
        
        vueNoeud.setSize(noeudSize, noeudSize);
        int xLocation = this.scaledCoordonateHorizontal(vueNoeud.getNoeud().getX()) - vueNoeud.getWidth()/2;
        int yLocation = this.scaledCoordonateVertical(vueNoeud.getNoeud().getY()) - vueNoeud.getHeight()/2;
        vueNoeud.setLocation(xLocation, yLocation);
        vueNoeud.setPlan(this.vuePlan);
        
        // Vue livraison
        if (noeud.getLieu() != null) {
            if (noeud.getLieu().getClass() == Livraison.class) {
                VueLivraison vueLivraison = new VueLivraison();
                vueLivraison.setSize(50, 50);
                vueNoeud.setVueLieu(vueLivraison);
            } else {
                VueDepot vueDepot = new VueDepot();
                vueDepot.setSize(40, 40);
                vueNoeud.setVueLieu(vueDepot);
            }                    
        }
        
        this.vuePlan.addVueNoeud(vueNoeud);
        
          
        if (this.selectedNoeud == noeud) {
            vueNoeud.setSelected(true);
        }
    }
    
    public void addNoeud(Noeud noeud) {
        
        if (this.noeuds.isEmpty()) {
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
        
        this.noeuds.add(noeud);  
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

        // Vue Troncon
        VueTroncon vueTroncon = new VueTroncon(troncon);
        this.vueTroncons.add(vueTroncon);
        
        int x = Math.min(troncon.getOrigine().getX(), troncon.getDestination().getX());
        int y = Math.min(troncon.getOrigine().getY(), troncon.getDestination().getY());
        vueTroncon.setLocation(this.scaledCoordonateHorizontal(x) - noeudSize/2, this.scaledCoordonateVertical(y) - noeudSize/2);
        
        int width = Math.abs(troncon.getDestination().getX() - troncon.getOrigine().getX());
        int height = Math.abs(troncon.getDestination().getY() - troncon.getOrigine().getY());
        
        vueTroncon.setSize(this.scaledSize(width) + noeudSize, this.scaledSize(height) + noeudSize);
        
        this.vuePlan.add(vueTroncon);
        
        
    }
    
    public void addTroncon(Troncon troncon) {
        this.troncons.add(troncon);
        this.createVueTronconFromTroncon(troncon);
        this.paint();
    }
    
    public void addAllTroncons(ArrayList<Troncon> troncons) {
        for (Troncon troncon : troncons) {
            this.troncons.add(troncon);
            this.createVueTronconFromTroncon(troncon);
        }
        this.paint();
    }
    
    private void paint() {
        this.vuePlan.removeAll();
        this.vuePlan.updateUI();
        this.vueNoeuds.clear();
        this.vueTroncons.clear();
        
        for (Noeud noeud : noeuds) {
            this.createVueNoeudFromNoeud(noeud);
        }
        for (Troncon troncon : troncons) {
            this.createVueTronconFromTroncon(troncon);
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
