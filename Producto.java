/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.practica1aplicaciones;

import javax.swing.*;
import java.net.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author erikg
 */
public class Producto implements Serializable {

    private String nombre;  
    private float precio;
    private String descripcion;
    private int existencia;

    public Producto(String string, float d, String string0, int i) {
        nombre = string;
        precio = d;
        descripcion = string0;
        existencia = i;
    }

    public void imprimir() {
        System.out.println(getNombre() +" "+getPrecio() +" "+ getDescripcion() +" "+ getExistencia());
    }
    public void imprimircarr(int cant){
        System.out.println(getNombre() +" "+getPrecio() +" "+ getDescripcion() +" "+ cant);
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the precio
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the existencia
     */
    public int getExistencia() {
        return existencia;
    }

    /**
     * @param existencia the existencia to set
     */
    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public void disminuirstock(int cantidad) {
        if (existencia >= 0) {
            existencia = existencia - cantidad;
        } else {
            System.out.println("No se puede comprar este Producto");
        }
    }

}
