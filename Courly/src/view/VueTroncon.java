/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.ControleurPlan;
import model.Troncon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

/**
 *
 * @author tanguyhelesbeux
 */
public class VueTroncon extends javax.swing.JPanel {
    
    private Troncon tronconAller;
    private Troncon tronconRetour;
    
    private static final int minWidth = 4; 
    private static final int minHeight = 4; 
    private static final int lineWidth = 4;
    private static final int noeudSize = ControleurPlan.noeudSize;
    
    private static final int padding = 10;

    /**
     * Creates new form VueTroncon
     */
    public VueTroncon() {
        initComponents();
    }

    public VueTroncon(Troncon troncon) {
        initComponents();
        this.setTronconAller(troncon);
        this.setVisible(true);
        this.setOpaque(false);
        this.setBackground(Color.BLACK);        
    }

    public Troncon getTronconAller() {
        return tronconAller;
    }

    public final void setTronconAller(Troncon troncon) {
        this.tronconAller = troncon;
        
        /*
        int x = Math.min(this.getTroncon().getOrigine().getX(), this.getTroncon().getDestination().getX());
        int y = Math.min(this.getTroncon().getOrigine().getY(), this.getTroncon().getDestination().getY());
        this.setLocation(x, y);
        
        int width = Math.abs(this.getTroncon().getDestination().getX() - this.getTroncon().getOrigine().getX());
        int height = Math.abs(this.getTroncon().getDestination().getY() - this.getTroncon().getOrigine().getY());
        
        if (width < minWidth) {
            width = minWidth;
        }
        if (height < minHeight) {
            height = minHeight;
        }
        
        this.setSize(width, height);
        */
    }

    public Troncon getTronconRetour() {
        return tronconRetour;
    }

    public void setTronconRetour(Troncon tronconRetour) {
        this.tronconRetour = tronconRetour;
    }
    
    
        @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if (this.getTronconAller().getTrajets() != null && !this.getTronconAller().getTrajets().isEmpty()) {
            g.setColor(Color.RED);
        } else if (this.getTronconRetour() != null && this.getTronconRetour().getTrajets() != null && !this.getTronconRetour().getTrajets().isEmpty()) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GRAY);
        }
        
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(lineWidth));
        
        /*int xDepart, yDepart, xArrivee, yArrivee;
        
         if (this.getWidth() <= minWidth) {
            xDepart = minWidth/2;
            xArrivee = minWidth/2;
        } else if (this.getTronconAller().getOrigine().getX() > this.getTronconAller().getDestination().getX()) {
            xDepart = this.getWidth() - noeudSize/2;
            xArrivee = noeudSize/2;
        } else {
            xDepart = noeudSize/2;
            xArrivee = this.getWidth() - noeudSize/2;
        }
        
        if (this.getHeight() <= minHeight) {
            yDepart = minHeight/2;
            yArrivee = minHeight/2;
        } else if (this.getTronconAller().getOrigine().getY() > this.getTronconAller().getDestination().getY()) {
            yDepart = this.getHeight() - noeudSize/2;
            yArrivee = noeudSize/2;
        } else {
            yDepart = noeudSize/2;
            yArrivee = this.getHeight() - noeudSize/2;
        }*/
        
        int xOrigine = this.getTronconAller().getOrigine().getX();
        int yOrigine = this.getTronconAller().getOrigine().getY();
        int xDestination = this.getTronconAller().getDestination().getX();
        int yDestination = this.getTronconAller().getDestination().getY();
        
        int width = this.getWidth();
        int height = this.getHeight();
        
        double size = Math.sqrt(Math.pow((xOrigine - xDestination), 2) + Math.pow((yOrigine - yDestination), 2));
        int intSize = (int)Math.round(size);
        
        double angle;
        if (yOrigine != yDestination) {
            angle = Math.atan((double)(xOrigine - xDestination)/(yOrigine - yDestination));
        } else {
            angle = Math.PI/2;
        }        
        System.out.println(angle);
        
        AffineTransform matrix = g2D.getTransform(); // Backup
        g2D.rotate(-angle, width/2, height/2);
        /* Begin */
        g.drawLine(width/2, height/2 - intSize/2, width/2, height/2 + intSize/2);
        /* End */
        g2D.setTransform(matrix);
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
