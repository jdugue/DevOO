/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import view.VueFenetrePrincipale;

/**
 *
 * @author tanguyhelesbeux
 */
public class ControleurFenetrePrincipale {

    private VueFenetrePrincipale fenetre;
    
    private ArrayList<String> colorMsg = new ArrayList();
    
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
