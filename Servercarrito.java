/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.practica1aplicaciones;

import java.io.*;
import java.net.*;
import javax.swing.*;
/**
 *
 * @author erikg
 */
public class Servercarrito {
    public static void main(String[] args) {
        try {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream("cliente.txt"));
            System.out.println(salida);
            salida.writeObject(producto);
            salida.close();
            ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("cliente.txt"));
            carrito=(Carro)entrada.readObject();
            System.out.println(carrito.getColor());
            System.out.println(carrito.getPlaca());
            System.out.println(carrito.getMarca());
            System.out.println(carrito.getAño());
            System.out.println(carrito.getNpuertas());
            System.out.println(carrito.getPrecio());
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ServerSocket s = new ServerSocket(3014);
            for(;;){
                Socket cl = s.accept();
                System.out.println("Conexión establecida desde"+cl.getInetAddress()+":"+cl.getPort());
                JFileChooser jf= new JFileChooser();
                int r = jf.showOpenDialog(null);
                if(r==JFileChooser.APPROVE_OPTION){
                    File f = jf.getSelectedFile();//Manejador
                    String archivo = f.getAbsolutePath(); //Dirección
                    String nombre = f.getName(); //Nombre
                    long tam= f.length();  //Tamaño
                    DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                    DataInputStream dis= new DataInputStream(new FileInputStream(archivo));
                    dos.writeUTF(nombre);
                    dos.flush();               
                    dos.writeLong(tam);
                    dos.flush();
                    byte[] b = new byte[1024];
                    long enviados = 0;
                    int porcentaje, n;
                    while(enviados < tam){
                        n = dis.read(b);
                        dos.write(b,0,n);
                        dos.flush();
                        enviados = enviados+n;
                        porcentaje = (int)(enviados*100/tam);
                        System.out.print("Enviado: "+porcentaje+"%\r");
                    }//While
                    System.out.print("\n\n Archivo enviado");
                    dos.close();
                    dis.close();
                    cl.close();
                }  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
