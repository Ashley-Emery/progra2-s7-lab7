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
import java.io.FileNotFoundException;

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
        //reloadHashTable();
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
    
    
    
    
}
