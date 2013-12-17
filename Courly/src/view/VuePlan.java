/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.ControleurPlan;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import model.Lieu;
import model.Livraison;
import model.Noeud;
import model.PlageHoraire;
import model.Plan;
import model.Tournee;
import model.Trajet;
import model.Troncon;

/**
 *
 * @author tanguyhelesbeux
 */
public class VuePlan extends javax.swing.JPanel {
    
    private ControleurPlan controleur;
    private Plan plan = new Plan();
    private Tournee tournee = new Tournee();
    
    private final HashMap<Noeud, VueNoeud> vueNoeuds = new HashMap<Noeud, VueNoeud>();
    private final HashMap<Troncon, VueTroncon> vueTroncons = new HashMap<Troncon, VueTroncon>();
        
    public static final int noeudSize = 14;
    public static final int padding = 30;
    
    private int minX, minY, maxX, maxY;
    private double zoomScale = 1.0;
    
    private static final Color BackgroundColor = new Color(233, 229, 220);
    
    private static ArrayList<Color> plageHoraireColors;

    public void setControleur(ControleurPlan controleur) {
        this.controleur = controleur;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
        this.paint();
    }

    public void setTournee(Tournee tournee) {
        this.tournee = tournee;
        this.paint();
    }
    
    private void initPlageHoraireColors() {
        VuePlan.plageHoraireColors = new ArrayList<Color>();
        
        plageHoraireColors.add(Color.green);
        plageHoraireColors.add(Color.blue);
        plageHoraireColors.add(Color.pink);
        plageHoraireColors.add(Color.black);
        plageHoraireColors.add(Color.yellow);
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
    
    public void addVueNoeud(VueNoeud vueNoeud) {
        this.add(vueNoeud);
        this.displayVueNoeud(vueNoeud);
    }
    
    private void displayVueNoeud(VueNoeud vueNoeud) {
            vueNoeud.setPlan(this);
            vueNoeud.setVisible(true);
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
        this.setPreferredSize(dimension);
    }

    public double getZoomScale() {
        return zoomScale;
    }

    public void setZoomScale(double zoomScale) {
        this.zoomScale = zoomScale;
        this.updateVuePlanFrame();
        this.paint();
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

            this.add(vueTroncon);
        } else {
           vueTroncon.setTronconRetour(troncon);
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
    
    private void cleanVuePlan() {
        this.removeAll();
        this.updateUI();
        this.vueNoeuds.clear();
        this.vueTroncons.clear();
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
                        VueLivraison vueLivraison = new VueLivraison(livraison);
                        vueNoeud.setVueLivraison(vueLivraison);
                        this.setComponentZOrder(vueNoeud, this.getComponentCount()-1);
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
                    vueNoeud.setVueLieu(new VueDepot(this.tournee.getDepot()));
                    this.setComponentZOrder(vueNoeud, this.getComponentCount()-1);
                }
            }
        }
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
            vueNoeud.setPlan(this);

            this.addVueNoeud(vueNoeud);
        }
    }
    
    public void setNoeudSelected(Noeud noeud, boolean selected) {
        VueNoeud vueNoeud = this.vueNoeuds.get(noeud);
        vueNoeud.setSelected(selected);
        if (vueNoeud.getVueLieu() != null) {
            vueNoeud.getVueLieu().setSelected(selected);
        }
        
    }
    
    public void didSelectVueNoeud(VueNoeud selectedVueNoeud) {        
        Noeud noeud = selectedVueNoeud.getNoeud();
        this.controleur.didSelectNoeud(noeud);
    }
    
    /*public void didDeselectVueNoeud(VueNoeud deselectedNoeud) {
        Noeud noeud = deselectedNoeud.getNoeud();
        this.controleur.didDeselectNoeud(noeud);
    }*/
    
    public void didSelectVueLieu(VueLieu vueLieu) {
        VueNoeud vueNoeud = this.vueNoeuds.get(vueLieu.getLieu().getNoeud());
        vueNoeud.setSelected(true);
        this.controleur.didSelectLieu(vueLieu.getLieu());
    }
    
    /*public void didDeselectVueLieu(VueLieu vueLieu) {
        VueNoeud vueNoeud = this.vueNoeuds.get(vueLieu.getLieu().getNoeud());
        vueNoeud.setSelected(false);
        this.controleur.didDeselectLieu(vueLieu.getLieu());
    }*/
    
    
    /**
     * Creates new form VuePlan
     */
    public VuePlan() {
        initComponents();
        this.setBackground(BackgroundColor);
        this.initPlageHoraireColors();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
