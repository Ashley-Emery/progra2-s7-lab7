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
    
    
}
