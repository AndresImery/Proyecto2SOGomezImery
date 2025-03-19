/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SO;

import structures.*;

/**
 *
 * @author andre
 */
public class NodoTree {
    private String nombre;
    private boolean esDirectorio;
    private int tamaño; // Solo aplicable si es un archivo
    private LinkedList<NodoTree> hijos; // Para directorios

    public NodoTree(String nombre, boolean esDirectorio, int tamaño) {
        this.nombre = nombre;
        this.esDirectorio = esDirectorio;
        this.tamaño = tamaño;
        this.hijos = new LinkedList<NodoTree>();
    }

    // Métodos getters y setters
    public String getNombre() {
        return nombre;
    }

    public boolean isDirectorio() {
        return esDirectorio;
    }

    public int getTamaño() {
        return tamaño;
    }

    public LinkedList<NodoTree> getHijos() {
        return hijos;
    }

    public void agregarHijo(NodoTree hijo) {
        hijos.add(hijo);
    }
}

