/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.s7.lab7;

/**
 *
 * @author ljmc2
 */
public class Entry {
    
    String username;
    long pos;
    Entry siguiente;

    public Entry(String username, long pos) {
        this.username = username;
        this.pos = pos;
        this.siguiente = null;
    }

    public String getUsername() {
        return username;
    }

    public long getPos() {
        return pos;
    }

    public Entry getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Entry siguiente) {
        this.siguiente = siguiente;
    }
}