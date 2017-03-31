/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ACER
 */
public class IngresoAutomata extends JFrame {

    protected JTextField nFilas;
    protected JTextField nColumnas;
    protected JButton aceptar;

    protected JPanel mostrador;

    public IngresoAutomata() {
        //hay un panel implícito
        getContentPane().setLayout(new GridLayout(2,7));
        setPreferredSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //recibe: orientación, min, max, inicial
        mostrador = new JPanel();
        setLocationRelativeTo(null);
        aceptar = new JButton("Aceptar");
        aceptar.setSize(5, 5);

      
        add(mostrador);
       ArrayList<JTextField> campos = new ArrayList<JTextField>();
        aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrador.removeAll();
                try {
                    int f = Integer.parseInt("5");
                    int c = Integer.parseInt("5");
                    int n = 0;
                    for (int i = 1; i <= f; i++) {
                        for (int j = 1; j <= c; j++) {
                            final int m = j;
                            JTextField a = new JTextField();
                            campos.add(a);
                            mostrador.add(new JTextField() {
                                {
                                    
                                    setOpaque(true);
                                    setPreferredSize(new Dimension(7,7));
                                   
                                }
                            });
                        }
                    }
                    mostrador.setLayout(new GridLayout(f, c, 6, 6));
                } catch (NumberFormatException nex) {
                    mostrador.add(new JLabel("sólo números!"));
                }
                mostrador.validate();
                mostrador.repaint();
            }
        });
        pack();
  add(new JPanel(new GridLayout(4,1)) {
            {
               
                add(aceptar);
                aceptar.setSize(1,4);
            }
        });
        

    }
}
