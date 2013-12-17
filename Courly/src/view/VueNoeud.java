/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.ControleurPlan;
import model.Noeud;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import model.Lieu;

/**
 *
 * @author tanguyhelesbeux
 */
public class VueNoeud extends javax.swing.JPanel {
    
    public boolean highlighted;
    public boolean selected;
    
    private static final Color normalEmptyColor = Color.WHITE;
    private static final Color normalLieuColor = new Color(220, 160, 60);
    private static final Color highlightedColor = new Color(240, 60, 40);
    private static final Color selectedColor = new Color(30, 80, 170);
    private static final Color selectedHighlightedColor = new Color(80, 150, 255);
    private static final Color BorderColor = new Color(198, 190, 180);
    
    protected VueLieu vueLieu;
    
    protected VuePlan vuePlan;
    private Noeud noeud;
    private Lieu lieu;

    /**
     * Creates new form NoeudView
     */
    public VueNoeud() {
        this.initialize();
    }
    
    public VueNoeud(Noeud noeud) {
        this.setNoeud(noeud);
        this.initialize();
    }
    
    private void initialize() {
        initComponents();

        this.setOpaque(false);
        this.setVisible(true); 
    }

    public VueLieu getVueLieu() {
        return vueLieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public void setVueLieu(VueLieu vueLieu) {
        
        if (vueLieu.getClass() == VueLivraison.class) {
            this.setVueLivraison((VueLivraison) vueLieu);
        } else {
            this.setVueDepot((VueDepot) vueLieu);
        }
    }
    
    public void setVueLivraison(VueLivraison vueLivraison) {
        this.vueLieu = vueLivraison;
        
        int x = this.getX() - (this.vueLieu.getWidth() - this.getWidth())/2;
        int y = this.getY() - (this.vueLieu.getHeight() - this.getHeight());
        
        this.vueLieu.setLocation(x, y);
        
        this.vueLieu.setVueNoeud(this);
        this.vueLieu.setVuePlan(vuePlan);
        this.vuePlan.add(this.vueLieu);
    }
    
    public void setVueDepot(VueDepot vueDepot) {
        this.vueLieu = vueDepot;
        
        int x = this.getX() - (this.vueLieu.getWidth() - this.getWidth())/2;
        int y = this.getY() - (this.vueLieu.getHeight() - this.getHeight())/2;
        
        this.vueLieu.setLocation(x, y);
        
        this.vueLieu.setVueNoeud(this);
        this.vueLieu.setVuePlan(vuePlan);
        this.vuePlan.add(this.vueLieu);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseClickedHandler(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mouseExitedHandler(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mouseEnterHandler(evt);
            }
        });

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

    private void mouseEnterHandler(java.awt.event.MouseEvent evt) {                                   
        // TODO add your handling code here:
        this.setHighlighted(true);
    }                                  

    private void mouseExitedHandler(java.awt.event.MouseEvent evt) {                                    
        // TODO add your handling code here:
        this.setHighlighted(false);
    }                                   

    private void mouseClickedHandler(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
        //this.setSelected(true, true);
        this.vuePlan.didSelectVueNoeud(this);
    }                                    

 
    
    

    

    // Variables declaration - do not modify                     
    // End of variables declaration                   

    public void setPlan(VuePlan plan) {
        this.vuePlan = plan;
    }

    public void setNoeud(Noeud noeud) {
        this.noeud = noeud;
    }

    public Noeud getNoeud() {
        return noeud;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setSelected(boolean selected, boolean callBack) {
        if (this.selected != selected) {
            this.selected = selected;

            this.repaint();
        }
    }
    
    public void setSelected(boolean selected) {
        this.setSelected(selected, false);
    }
    
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        if (this.vueLieu != null && this.vueLieu.highlighted != this.highlighted) {
            this.vueLieu.setHighlighted(highlighted);
        }
        
        this.repaint();
    }
    
    private Color colorForActualState() {
        if (this.selected && this.highlighted) {
            return VueNoeud.selectedHighlightedColor;
        } else if (this.selected) {
            return VueNoeud.selectedColor;
        } else if (this.highlighted) {
            return VueNoeud.highlightedColor;
        } else {
            if (this.lieu == null) {
                return VueNoeud.normalEmptyColor;
            } else {
                return VueNoeud.normalLieuColor;
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(this.colorForActualState());
        g.fillOval(0,0,this.getWidth(),this.getHeight());
        g.setColor(BorderColor);
        g.drawOval(0,0,this.getWidth(),this.getHeight());
    }
}
