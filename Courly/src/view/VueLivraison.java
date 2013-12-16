/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.Livraison;

/**
 *
 * @author tanguyhelesbeux
 */
public class VueLivraison extends VueLieu {
    
    
    private static final String normalImagePath = "Images/map_pin_50px.png";
    private static final String highlightedImagePath = "Images/map_pin_highlighted_50px.png";
    private static final String selectedImagePath = "Images/map_pin_selected_50px.png";
    private static final String selectedHighlightedImagePath = "Images/map_pin_selectedhighlighted_50px.png";

    /**
     * Creates new form VueLivraison
     */
    public VueLivraison() {
        initComponents();
        this.setOpaque(false);
        this.setSize(50, 50);
    }

    /*
    @Override
    public void setHighlighted(boolean highlighted) {
        super.setHighlighted(highlighted);
        this.updateUI();
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        this.updateUI();
    }
    */
    
    
    @Override
    protected BufferedImage pinImageForActualState() {
        
        if (this.selected && this.highlighted) {
            return this.pinImage(selectedHighlightedImagePath);
        } else if (this.selected) {
            return this.pinImage(selectedImagePath);
        } else if (this.highlighted) {
            return this.pinImage(highlightedImagePath);
        } else {
            return this.pinImage(normalImagePath);
        }
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
