/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.s7.lab7;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 *
 * @author esteb
 */

public class AddTrophyPanel {

    public static JPanel create() {
        JPanel root = new JPanel(new BorderLayout(18, 18));
        root.setBackground(Color.WHITE);
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel header = new JLabel("Agregar trofeo", SwingConstants.LEFT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        root.add(header, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12, 12, 12, 12);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;
        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        grid.add(lblUser, c);

        c.gridx = 1;
        JTextField txtUser = new JTextField(28);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtUser.setPreferredSize(new Dimension(420, 36));
        grid.add(txtUser, c);

        c.gridx = 0;
        c.gridy = 1;
        JLabel lblJuego = new JLabel("Nombre del juego:");
        lblJuego.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        grid.add(lblJuego, c);

        c.gridx = 1;
        JTextField txtJuego = new JTextField(28);
        txtJuego.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtJuego.setPreferredSize(new Dimension(420, 36));
        grid.add(txtJuego, c);

        c.gridx = 0;
        c.gridy = 2;
        JLabel lblNombre = new JLabel("Nombre / Descripción:");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        grid.add(lblNombre, c);

        c.gridx = 1;
        JTextField txtNombre = new JTextField(28);
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtNombre.setPreferredSize(new Dimension(420, 36));
        grid.add(txtNombre, c);

        c.gridx = 0;
        c.gridy = 3;
        JLabel lblTipo = new JLabel("Tipo de trofeo:");
        lblTipo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        grid.add(lblTipo, c);

        c.gridx = 1;

        c.gridx = 0;
        c.gridy = 4;
        JLabel lblImagen = new JLabel("Imagen (opcional):");
        lblImagen.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        grid.add(lblImagen, c);

        c.gridx = 1;
        JPanel imagenPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        imagenPanel.setBackground(Color.WHITE);
        JButton btnSeleccionar = new JButton("Seleccionar imagen...");
        btnSeleccionar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel lblNombreArchivo = new JLabel("Ningún archivo seleccionado");
        lblNombreArchivo.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        imagenPanel.add(btnSeleccionar);
        imagenPanel.add(lblNombreArchivo);
        grid.add(imagenPanel, c);

        c.gridx = 1;
        c.gridy = 5;
        c.anchor = GridBagConstraints.EAST;
        JButton btnAgregar = new JButton("Agregar trofeo");
        btnAgregar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnAgregar.setPreferredSize(new Dimension(200, 44));
        grid.add(btnAgregar, c);

        root.add(grid, BorderLayout.CENTER);

        return root;
    }
}