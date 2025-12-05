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
public class MenuPrincipal extends JFrame {

    private JPanel centerPanel;

    public MenuPrincipal() {
        setTitle("PSN - Panel Principal");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(new EmptyBorder(20, 20, 20, 20));
        left.setPreferredSize(new Dimension(320, 0));
        left.setBackground(new Color(245, 245, 245));

        JLabel lblTitle = new JLabel("PSN Manager");
        lblTitle.setFont(lblTitle.getFont().deriveFont(20f));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        left.add(lblTitle);
        left.add(Box.createVerticalStrut(12));

        JButton btnAgregar = new JButton("Registrar usuario");
        JButton btnDesactivar = new JButton("Desactivar usuario");
        JButton btnAgregarTrofeo = new JButton("Agregar trofeo");
        JButton btnInfo = new JButton("Mostrar info de jugador");
        JButton btnSalir = new JButton("Salir");

        Dimension btnSize = new Dimension(260, 45);
        JButton[] buttons = {btnAgregar, btnDesactivar, btnAgregarTrofeo, btnInfo};

        for (JButton b : buttons) {
            b.setMaximumSize(btnSize);
            b.setPreferredSize(btnSize);
            b.setAlignmentX(Component.LEFT_ALIGNMENT);
            left.add(b);
            left.add(Box.createVerticalStrut(10));
        }

        left.add(Box.createVerticalGlue());
        btnSalir.setMaximumSize(btnSize);
        btnSalir.setAlignmentX(Component.LEFT_ALIGNMENT);
        left.add(btnSalir);

        add(left, BorderLayout.WEST);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        showWelcome();
        add(centerPanel, BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> setCenter(AddUserPanel.create()));
        btnDesactivar.addActionListener(e -> setCenter(DesactivarUserPanel.create()));
        btnAgregarTrofeo.addActionListener(e -> setCenter(AddTrophyPanel.create()));
        btnInfo.addActionListener(e -> setCenter(InfoPlayerPanel.create()));
        btnSalir.addActionListener(e -> dispose());
    }

    private void setCenter(JPanel p) {
        centerPanel.removeAll();
        centerPanel.add(p, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void showWelcome() {
        centerPanel.removeAll();

        JLabel lbl = new JLabel("Selecciona una opción en el menú", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        centerPanel.add(lbl, BorderLayout.CENTER);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
