/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.s7.lab7;

/**
 *
 * @author ljmc2
 */
public class HashTable {

    private Entry inicio;

    public HashTable() {
        inicio = null;
    }

    public void add(String username, long pos) {
        Entry nuevo = new Entry(username, pos);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            Entry tmp = inicio;
            while (tmp.siguiente != null) {
                tmp = tmp.siguiente;
            }
            tmp.siguiente = nuevo;
        }
    }

    public void remove(String username) {
        if (inicio == null) {
            return;
        }

        if (inicio.username.equals(username)) {
            inicio = inicio.siguiente;
            return;
        }

        Entry anterior = inicio;
        Entry actual = inicio.siguiente;

        while (actual != null) {
            if (actual.username.equals(username)) {
                anterior.siguiente = actual.siguiente;
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
    }

    public long search(String username) {
        Entry tmp = inicio;
        while (tmp != null) {
            if (tmp.username.equals(username)) {
                return tmp.pos;
            }
            tmp = tmp.siguiente;
        }
        return -1;
    }
}

