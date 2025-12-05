/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.s7.lab7;

/**
 *
 * @author ashley
 */

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.EOFException;

import java.time.LocalDate;

public class PSNUsers {
    
    private String ARCHIVO_USUARIOS = "users.psn";
    private String ARCHIVO_TROFEOS = "trophies.psn";
    
    private RandomAccessFile archivo;
    private RandomAccessFile archivoTrophies;
    private HashTable users;
    
    public PSNUsers() throws IOException {
        
        archivo = new RandomAccessFile(ARCHIVO_USUARIOS, "rw");
        archivoTrophies = new RandomAccessFile(ARCHIVO_TROFEOS, "rw");
        users = new HashTable();
        reloadHashTable();
    }
    
    private void reloadHashTable() throws IOException {
        
        users = new HashTable();
        archivo.seek(0);
        
        try {
            while (true) {
                
                String username = archivo.readUTF();
                long posPuntos = archivo.getFilePointer();
                int puntos = archivo.readInt();
                int contadorTrofeos = archivo.readInt();
                boolean activo = archivo.readBoolean();

                if (activo) {
                    users.add(username, posPuntos);
                }
            }
        } catch (EOFException e) {
            System.out.println("Lectura completada (EOF)");
        }
        
    }
    
    public boolean addUser(String username) throws IOException {
        
        if (users.search(username) != -1) {
            return false;
        }

        archivo.seek(archivo.length());
        archivo.writeUTF(username);
        long posPuntos = archivo.getFilePointer();
        archivo.writeInt(0);
        archivo.writeInt(0);
        archivo.writeBoolean(true);

        users.add(username, posPuntos);
        return true;
    }
    
    public boolean deactivateUser(String username) throws IOException {
        
        long posPuntos = users.search(username);
        if (posPuntos == -1) {
            return false;
        }
        
        long posActivo = posPuntos + 4 + 4;
        archivo.seek(posActivo);
        archivo.writeBoolean(false);

        users.remove(username);
        return true;
    }
    
    public boolean addTrophieTo(String username, String trophyGame, String trophyName, Trophy type, byte[] trophyImageBytes) throws IOException {

        long posPuntos = users.search(username);
        
        if (posPuntos == -1) {
            return false;
        }
        
        archivoTrophies.seek(archivoTrophies.length());
        archivoTrophies.writeUTF(username);
        archivoTrophies.writeUTF(type.name());
        archivoTrophies.writeUTF(trophyGame);
        archivoTrophies.writeUTF(trophyName);
        String fechaTexto = LocalDate.now().toString();
        archivoTrophies.writeUTF(fechaTexto);
        
        if (trophyImageBytes != null) {
            archivoTrophies.writeInt(trophyImageBytes.length);
            archivoTrophies.write(trophyImageBytes);
        } else {
            archivoTrophies.writeInt(0);
        }
        
        archivo.seek(posPuntos);
        int puntos = archivo.readInt();
        int contadorTrofeos = archivo.readInt();
        boolean activo = archivo.readBoolean();
        
        if (!activo) {
            return false;
        }
        
        puntos += type.points;
        contadorTrofeos++;
        
        archivo.seek(posPuntos);
        archivo.writeInt(puntos);
        archivo.writeInt(contadorTrofeos);
        
        return true;
    }
    
    public String playerInfo(String username) throws IOException {
        
        String usernameLeido = null;
        int puntos = 0;
        int contadorTrofeos = 0;
        boolean activo = false;
        
        archivo.seek(0);
        
        try {
            while (true) {
                String u = archivo.readUTF();
                long posPuntos = archivo.getFilePointer();
                int pts = archivo.readInt();
                int cont = archivo.readInt();
                boolean act = archivo.readBoolean();
                
                if (u.equals(username)) {
                    usernameLeido = u;
                    puntos = pts;
                    contadorTrofeos = cont;
                    activo = act;
                    break;
                }
            }
        } catch (EOFException e) {
            System.out.println("Fin del archivo de usuarios alcanzado durante busqueda");
        }
        
        if (usernameLeido == null || !activo) {
            return "Usuario no encontrado o inactivo";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Usuario: ").append(usernameLeido).append("\n");
        sb.append("Puntos acumulados: ").append(puntos).append("\n");
        sb.append("Cantidad de trofeos: ").append(contadorTrofeos).append("\n");
        sb.append("Estado: ").append(activo ? "ACTIVO" : "INACTIVO").append("\n\n");

        sb.append("TROFEOS:\n");
        
        archivoTrophies.seek(0);
        
        try {
            while (true) {
                String uTrofeo = archivoTrophies.readUTF();
                String tipo = archivoTrophies.readUTF();
                String juego = archivoTrophies.readUTF();
                String nombre = archivoTrophies.readUTF();
                String fecha = archivoTrophies.readUTF();
                int lenImagen = archivoTrophies.readInt();

                if (lenImagen > 0) {
                    archivoTrophies.skipBytes(lenImagen);
                }

                if (uTrofeo.equals(username)) {
                    sb.append(fecha).append(" - ")
                      .append(tipo).append(" - ")
                      .append(juego).append(" - ")
                      .append(nombre).append(" - [IMAGEN]\n");
                }
            }
        } catch (EOFException e) {
            System.out.println("Fin del archivo de trofeos alcanzado");
        }

        return sb.toString();
    }
    
    public byte[] getUltimaImagenTrofeo(String username) throws IOException {
        byte[] ultimaImagen = null;

        archivoTrophies.seek(0);

        try {
            while (true) {
                String uTrofeo = archivoTrophies.readUTF();
                String tipo = archivoTrophies.readUTF();
                String juego = archivoTrophies.readUTF();
                String nombre = archivoTrophies.readUTF();
                String fecha = archivoTrophies.readUTF();
                int lenImagen = archivoTrophies.readInt();

                byte[] imagenActual = null;
                if (lenImagen > 0) {
                    imagenActual = new byte[lenImagen];
                    archivoTrophies.readFully(imagenActual);
                }

                if (uTrofeo.equals(username)) {
                    // nos quedamos con la última imagen encontrada
                    ultimaImagen = imagenActual;
                }
            }
        } catch (EOFException e) {
            // fin de archivo, no pasa nada
        }

        return ultimaImagen;
    }
    
    public void close() {

        try {
            if (archivo != null) {
                archivo.close();
            }

        } catch (IOException e) {

            System.err.println("Error al cerrar el archivo de usuarios: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            if (archivoTrophies != null) {
                archivoTrophies.close();
            }

        } catch (IOException e) {

            System.err.println("Error al cerrar el archivo de trofeos: " + e.getMessage());
            e.printStackTrace();

        }
    }
    
}
