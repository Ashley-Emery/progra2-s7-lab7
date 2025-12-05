/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.s7.lab7;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 *
 * @author esteb
 */

public class AddTrophyPanel {

    public static JPanel create(PSNUsers psn) {
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
        JComboBox<Trophy> comboTipo = new JComboBox<>(Trophy.values());
        comboTipo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboTipo.setPreferredSize(new Dimension(240, 36));
        grid.add(comboTipo, c);

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

        final byte[][] imagenBytes = new byte[1][];
        btnSeleccionar.addActionListener(ae -> {
            JFileChooser fc = new JFileChooser();
            int res = fc.showOpenDialog(root);
            if (res == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                lblNombreArchivo.setText(f.getName());
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     FileInputStream fis = new FileInputStream(f)) {

                    byte[] buffer = new byte[4096];
                    int r;
                    while ((r = fis.read(buffer)) != -1) baos.write(buffer, 0, r);
                    imagenBytes[0] = baos.toByteArray();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(root, "Error leyendo la imagen: " + ex.getMessage(),
                            "Error I/O", JOptionPane.ERROR_MESSAGE);
                    imagenBytes[0] = null;
                    lblNombreArchivo.setText("Ningún archivo seleccionado");
                }
            }
        });

        btnAgregar.addActionListener(ev -> {
            String username = txtUser.getText().trim();
            String juego = txtJuego.getText().trim();
            String nombre = txtNombre.getText().trim();
            Trophy tipo = (Trophy) comboTipo.getSelectedItem();

            if (username.isEmpty() || juego.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(root, "Complete todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                boolean ok = psn.addTrophieTo(username, juego, nombre, tipo, imagenBytes[0]);
                if (ok) {
                    JOptionPane.showMessageDialog(root, "Trofeo agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    txtUser.setText("");
                    txtJuego.setText("");
                    txtNombre.setText("");
                    comboTipo.setSelectedIndex(0);
                    imagenBytes[0] = null;
                    lblNombreArchivo.setText("Ningún archivo seleccionado");
                } else {
                    JOptionPane.showMessageDialog(root, "No se pudo agregar trofeo. Usuario inexistente o inactivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(root, "Error al agregar trofeo:\n" + ex.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
            }
        });

        return root;
    }
}
