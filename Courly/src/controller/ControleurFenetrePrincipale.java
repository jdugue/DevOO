/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

<<<<<<< HEAD
import java.util.ArrayList;
=======
import java.awt.Color;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Plan;
import model.Tournee;
import org.xml.sax.SAXException;
import view.FenetrePrincipale;
>>>>>>> contrôleur inspecteur
import view.VueFenetrePrincipale;

/**
 *
 * @author tanguyhelesbeux
 */
public class ControleurFenetrePrincipale {
<<<<<<< HEAD

    private VueFenetrePrincipale fenetre;
    
    private ArrayList<String> colorMsg = new ArrayList();
=======
    
    private VueFenetrePrincipale fenetre;
    private ControleurPlan controleurPlan;
    
    private Plan plan;
    
    private double zoomScale = 1.0;
    private static final double zoomDelta = 0.05;
    private static final double zoomMin = 0.1;
    private static final double zoomMax = 2.0;

    public ControleurFenetrePrincipale(VueFenetrePrincipale aFenetre) {
        
        this.fenetre = aFenetre;
        this.controleurPlan = new ControleurPlan(this.fenetre.getScrollPanePlan(), this);
    }

    public void setZoomScale(double zoomScale) {
        
        if (zoomScale > zoomMax) {
            zoomScale = zoomMax;
        } else if (zoomScale < zoomMin) {
            zoomScale = zoomMin;
        }
        this.zoomScale = zoomScale;
        this.controleurPlan.setZoomScale(zoomScale);
    }
    
    
    
    public void shouldZoomOut() {
        this.setZoomScale(zoomScale - zoomDelta);
    }
    
    public void shouldZoomIn() {
        this.setZoomScale(zoomScale + zoomDelta);
    }
    
    public void shouldLoadPlan() {
        this.plan = null;
        JFileChooser fChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier plan", "xml");
        fChooser.setFileFilter(filter);
        int returnVal = fChooser.showOpenDialog(this.fenetre);
        if( returnVal == JFileChooser.APPROVE_OPTION ) {
            String file = fChooser.getSelectedFile().getAbsolutePath();
            ParseurXML p = new ParseurXML();

            try {
                    plan = p.construirePlanXML(file);
                    if (plan !=null) {
                            this.controleurPlan.loadVuePlanFromModel(plan);
                    } else {
                        System.out.print("Une erreur inconnue est survenue");
                    }
            } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    //textFieldError.setForeground(Color.RED);
                    //textFieldError.setText("Fichier XML incorrect");
                System.out.print("Fichier XML incorrect");
            } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    //textFieldError.setForeground(Color.RED);
                    //textFieldError.setText("Fichier inexistant");
                System.out.print("Fichier inexistant");
            } catch (SAXException e) {
                    // TODO Auto-generated catch block
                    //textFieldError.setForeground(Color.RED);
                    //textFieldError.setText("Fichier XML incorrect");
                System.out.print("Fichier XML incorrect");
            }

        }

    }
    
    public void shouldLoadLivraison() {

        if( plan!=null) {
                JFileChooser fChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier livraison", "xml");
                fChooser.setFileFilter(filter);
                int returnVal = fChooser.showOpenDialog(this.fenetre);
                if( returnVal == JFileChooser.APPROVE_OPTION ) {
                        String file = fChooser.getSelectedFile().getAbsolutePath();
                        ParseurXML p = new ParseurXML();

                        Tournee tournee = p.construireTourneeXML(file);
                        for (int i=0;i< tournee.getLivraisons().size();i++) {
                                Integer adresse = tournee.getLivraisons().get(i).getAdresse();
                                tournee.getLivraisons().get(i).setNoeud(plan.getNoeuds().get(adresse));
                        }
                        this.controleurPlan.paint();
                }
        }
        else {
                System.out.print("Aucun plan en mémoire !");
        }
    }
>>>>>>> contrôleur inspecteur
    
    public ControleurFenetrePrincipale(VueFenetrePrincipale fenetre) {
        colorMsg.add("#a00a11"); // ERROR
        colorMsg.add("#d49e15"); // WARNING
        colorMsg.add("#2ca024"); // SUCCESS
        colorMsg.add("#333333"); // LOG
    }

    public void setMessage(String msg, int msgType) {
        javax.swing.JEditorPane editorPane = fenetre.getCommentArea();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html");
        String text = "<font color='"+colorMsg.get(msgType)+"'>" + msg + "</font><br>";
        editorPane.setText(text + fenetre.getCommentArea().getText());
    }

}