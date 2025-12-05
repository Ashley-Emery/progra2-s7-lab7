/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.s7.lab7;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

/** 
 * 
 * @author esteb
 */
public class InfoPlayerPanel extends JPanel {

    private final JTextArea infoArea;
    private final JTextField searchField;

    public InfoPlayerPanel() {
        setLayout(new BorderLayout(12, 12));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel header = new JLabel("Información del jugador", SwingConstants.LEFT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        add(header, BorderLayout.NORTH);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.setBackground(Color.WHITE);
        searchField = new JTextField(24);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        top.add(new JLabel("Username:"));
        top.add(searchField);
        top.add(btnBuscar);
        add(top, BorderLayout.PAGE_START);

        infoArea = new JTextArea();
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        infoArea.setEditable(false);
        infoArea.setLineWrap(false);
        infoArea.setTabSize(4);

        JScrollPane sp = new JScrollPane(infoArea);
        sp.setPreferredSize(new Dimension(8, 420));
        sp.setBorder(BorderFactory.createTitledBorder("Información"));
        add(sp, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
        });
    }

    public static InfoPlayerPanel create(PSNUsers psn) {
        InfoPlayerPanel p = new InfoPlayerPanel();

        p.searchField.addActionListener(e -> {
            String user = p.searchField.getText().trim();
            if (!user.isEmpty()) {
                try {
                    String info = psn.playerInfo(user);
                    p.infoArea.setText(info);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(p, "Error al leer información:\n" + ex.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(p, "Ingrese un username.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        Component[] comps = p.getComponents();
        Component top = p.getComponent(1);
        if (top instanceof JPanel) {
            for (Component c : ((JPanel) top).getComponents()) {
                if (c instanceof JButton) {
                    ((JButton) c).addActionListener(ev -> {
                        String user = p.searchField.getText().trim();
                        if (!user.isEmpty()) {
                            try {
                                String info = psn.playerInfo(user);
                                p.infoArea.setText(info);
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(p, "Error al leer información:\n" + ex.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(p, "Ingrese un username.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        }
                    });
                }
            }
        }

        return p;
    }
}   
