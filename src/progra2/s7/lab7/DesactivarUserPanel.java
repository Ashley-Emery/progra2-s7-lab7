/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.s7.lab7;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * 
 * @author esteb
 */
public class DesactivarUserPanel {

    public static JPanel create(PSNUsers psn) {
        JPanel root = new JPanel(new BorderLayout(18, 18));
        root.setBackground(Color.WHITE);
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel header = new JLabel("Desactivar usuario", SwingConstants.LEFT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        root.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(14, 14, 14, 14);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        form.add(lblUsername, c);

        c.gridx = 1;
        JTextField txtUsername = new JTextField(32);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtUsername.setPreferredSize(new Dimension(420, 36));
        form.add(txtUsername, c);

        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.EAST;
        JButton btnDesactivar = new JButton("Desactivar");
        btnDesactivar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnDesactivar.setPreferredSize(new Dimension(180, 44));
        form.add(btnDesactivar, c);

        root.add(form, BorderLayout.CENTER);

        btnDesactivar.addActionListener(ev -> {
            String username = txtUsername.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(root, "Ingrese un username.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                boolean ok = psn.deactivateUser(username);
                if (ok) {
                    JOptionPane.showMessageDialog(root, "Usuario desactivado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    txtUsername.setText("");
                } else {
                    JOptionPane.showMessageDialog(root, "Usuario no encontrado o ya inactivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(root, "Error al desactivar usuario:\n" + ex.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
            }
        });

        return root;
    }
}
