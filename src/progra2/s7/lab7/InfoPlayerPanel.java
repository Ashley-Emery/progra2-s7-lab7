/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.s7.lab7;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** 
 * 
 * @author esteb
 */
public class InfoPlayerPanel extends JPanel {

    private final JTextArea infoArea;
    private final JTextField searchField;
    private final JPanel trophiesContainer;

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

        JScrollPane spInfo = new JScrollPane(infoArea);
        spInfo.setPreferredSize(new Dimension(8, 140));
        spInfo.setBorder(BorderFactory.createTitledBorder("Información básica"));

        trophiesContainer = new JPanel();
        trophiesContainer.setLayout(new BoxLayout(trophiesContainer, BoxLayout.Y_AXIS));
        trophiesContainer.setBackground(Color.WHITE);

        JScrollPane spTrophies = new JScrollPane(trophiesContainer);
        spTrophies.setBorder(BorderFactory.createTitledBorder("Trofeos"));
        spTrophies.setPreferredSize(new Dimension(8, 400));

        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.setBackground(Color.WHITE);
        center.add(spInfo, BorderLayout.NORTH);
        center.add(spTrophies, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    public static InfoPlayerPanel create(PSNUsers psn) {
        InfoPlayerPanel panel = new InfoPlayerPanel();

        JPanel top = (JPanel) panel.getComponent(1);
        JButton botonBuscar = null;
        for (Component c : top.getComponents()) {
            if (c instanceof JButton) botonBuscar = (JButton) c;
        }

        java.awt.event.ActionListener doSearch = ev -> {
            String user = panel.searchField.getText().trim();
            if (user.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Ingrese un username.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String info;
            try {
                info = psn.playerInfo(user);

                int idx = info.indexOf("TROFEOS:");
                String infoBasica = (idx == -1) ? info : info.substring(0, idx).trim();

                panel.infoArea.setText(infoBasica);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(panel, "Error leyendo info: " + ex.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
                return;
            }

            panel.trophiesContainer.removeAll();
            try {
                TrofeosRawResult res = panel.readTrophiesFileForUser(user);

                if (res.lines.isEmpty()) {
                    JLabel lbl = new JLabel("No se encontraron trofeos para el usuario.");
                    lbl.setBorder(new EmptyBorder(8, 8, 8, 8));
                    panel.trophiesContainer.add(lbl);
                } else {
                    for (int i = 0; i < res.lines.size(); i++) {

                        String trofeoLine = res.lines.get(i);
                        byte[] bytes = res.images.get(i);

                        JPanel row = new JPanel(new BorderLayout(8, 8));
                        row.setBackground(Color.WHITE);
                        row.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                                new EmptyBorder(8, 8, 8, 8)
                        ));

                        JTextArea txt = new JTextArea(trofeoLine);
                        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        txt.setEditable(false);
                        txt.setLineWrap(true);
                        txt.setWrapStyleWord(true);
                        txt.setOpaque(false);
                        txt.setFocusable(false);

                        JPanel leftWrapper = new JPanel(new BorderLayout());
                        leftWrapper.setBackground(Color.WHITE);
                        leftWrapper.add(txt, BorderLayout.CENTER);

                        row.add(leftWrapper, BorderLayout.CENTER);

                        if (bytes != null && bytes.length > 0) {
                            try {
                                BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));

                                if (img != null) {
                                    int maxDim = 96;
                                    int w = img.getWidth();
                                    int h = img.getHeight();

                                    float scale = Math.min((float) maxDim / w, (float) maxDim / h);
                                    int newW = Math.round(w * scale);
                                    int newH = Math.round(h * scale);

                                    Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                                    JLabel pic = new JLabel(new ImageIcon(scaled));
                                    pic.setPreferredSize(new Dimension(96, 96));

                                    pic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                    BufferedImage imgForPreview = img;

                                    pic.addMouseListener(new java.awt.event.MouseAdapter() {
                                        @Override
                                        public void mouseClicked(java.awt.event.MouseEvent e) {
                                            showImagePreview(panel, imgForPreview, trofeoLine);
                                        }
                                    });

                                    JPanel east = new JPanel(new BorderLayout());
                                    east.setBackground(Color.WHITE);
                                    east.add(pic, BorderLayout.EAST);
                                    row.add(east, BorderLayout.EAST);

                                } else {
                                    row.add(new JLabel("Imagen inválida"), BorderLayout.EAST);
                                }

                            } catch (Exception e) {
                                row.add(new JLabel("Error imagen"), BorderLayout.EAST);
                            }

                        } else {
                            row.add(new JLabel("Sin imagen"), BorderLayout.EAST);
                        }

                        panel.trophiesContainer.add(row);
                        panel.trophiesContainer.add(Box.createVerticalStrut(8));
                    }
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(panel, "Error al leer trofeos: " + ex.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
            }

            panel.trophiesContainer.revalidate();
            panel.trophiesContainer.repaint();
        };

        if (botonBuscar != null) botonBuscar.addActionListener(doSearch);
        panel.searchField.addActionListener(doSearch);

        return panel;
    }


    private static class TrofeosRawResult {
        final List<String> lines;
        final List<byte[]> images;
        TrofeosRawResult(List<String> l, List<byte[]> i) { lines = l; images = i; }
    }

    private TrofeosRawResult readTrophiesFileForUser(String username) throws IOException {
        List<String> lines = new ArrayList<>();
        List<byte[]> images = new ArrayList<>();

        RandomAccessFile raf = new RandomAccessFile("trophies.psn", "r");

        try {
            raf.seek(0);

            while (true) {
                String u = raf.readUTF();
                String tipo = raf.readUTF();
                String juego = raf.readUTF();
                String nombre = raf.readUTF();
                String fecha = raf.readUTF();
                int len = raf.readInt();

                byte[] imgBytes = null;

                if (len > 0) {
                    imgBytes = new byte[len];
                    raf.readFully(imgBytes);
                }

                if (u.equals(username)) {
                    String linea = fecha + " - " + tipo + " - " + juego + " - " + nombre;
                    lines.add(linea);
                    images.add(imgBytes);
                }
            }

        } catch (EOFException eof) {
        }

        raf.close();
        return new TrofeosRawResult(lines, images);
    }


    private static void showImagePreview(Component parent, BufferedImage img, String title) {
        ImageIcon icon = new ImageIcon(img);
        JLabel lbl = new JLabel(icon);

        JScrollPane sp = new JScrollPane(lbl);
        sp.setPreferredSize(new Dimension(
                Math.min(img.getWidth(), 900),
                Math.min(img.getHeight(), 700)
        ));

        JOptionPane.showMessageDialog(parent, sp, "Preview - " + title, JOptionPane.PLAIN_MESSAGE);
    }
}
