package question1;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class IHMPile extends JFrame implements ActionListener{
    private JTextField donnee = new JTextField(6);
    private JTextField sommet = new JTextField(6);
    private JLabel     contenu = new JLabel("[]");

    private Pile p;

    public IHMPile(){
        super("IHM Pile");
        JButton    boutonEmpiler = new JButton("empiler");
        JButton    boutonDepiler = new JButton("depiler");

        JPanel enHaut = new JPanel();
        enHaut.add(donnee);
        enHaut.add(boutonEmpiler);
        enHaut.add(boutonDepiler);
        enHaut.add(sommet);
        setLayout(new BorderLayout(5,5));
        add("North",enHaut);
        add("Center",contenu);
        enHaut.setBackground(Color.red);
        setLocation(100,100);
        pack();setVisible(true);
        boutonEmpiler.addActionListener(this);
        boutonDepiler.addActionListener(this);
        
        /**
         * La capacit� de p est mis � 6 car l'applette incluse 
         * dans l'�nonc� poss�de une capacit� de la pile �gale � 6
         */ 
        p = new Pile(6);

    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getActionCommand().equals("empiler")){
            try {
                Object objEmpiler = donnee.getText();
                p.empiler(objEmpiler);
                // Afficher le contenu de la pile apr�s empilation
                contenu.setText(p.toString());
                
                /** 
                 *  Suite � l'affichage, on pourra vider l'entr�e
                 *  afin de faciliter la prochaine empilation 
                 *  en ajoutant: donnee.setText("");
                 *  MAIS celle-ci n'est pas pr�sente dans 
                 *  l'applette incluse dans l'�nonc� alors on 
                 *  l'ignore
                 *  
                 */ 
            } catch (PilePleineException pleineExc) {
                contenu.setText(p.toString() + " estPleine !");
            }
        }else{
            try {
                Object objDepiler = p.depiler();
                // afficher l'objet d�pil�
                sommet.setText(objDepiler.toString());
                // afficher le contenu de la pile apr�s d�pilation
                contenu.setText(p.toString()); 
            } catch (PileVideException VideExc) {
                contenu.setText(p.toString() + " estVide !");
            }
        }
    }

    public static void main(String[] args){
        new IHMPile();
    }
}
