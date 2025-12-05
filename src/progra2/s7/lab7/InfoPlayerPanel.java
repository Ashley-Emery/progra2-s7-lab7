/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.s7.lab7;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/**
 *
 * @author esteb
 */
public class InfoPlayerPanel extends JPanel{
     private final JTextArea infoArea;

    public InfoPlayerPanel() {
        setLayout(new BorderLayout(12, 12));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel header = new JLabel("Información del jugador", SwingConstants.LEFT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        add(header, BorderLayout.NORTH);

        infoArea = new JTextArea();
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        infoArea.setEditable(false);
        infoArea.setLineWrap(false);
        infoArea.setTabSize(4);

        JScrollPane sp = new JScrollPane(infoArea);
        sp.setPreferredSize(new Dimension(8, 420));
        sp.setBorder(BorderFactory.createTitledBorder("Información"));

        add(sp, BorderLayout.CENTER);
    }
    
    public static InfoPlayerPanel create() {
        return new InfoPlayerPanel();
    }
}
