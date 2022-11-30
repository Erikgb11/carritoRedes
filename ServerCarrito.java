/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.practica1aplicaciones;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;


/**
 *
 * @author erikg
 */
public class ServerCarrito {
  public static void main(String[] args) {
        Producto camisa=new Producto("camisa",172,"Una camisa chida chida",10);
        Producto pantalon=new Producto("pantalon",150,"Para toda ocasion",10);
        Producto sueter=new Producto("sueter",250,"Para el frio",10);
        camisa.imprimir();
        pantalon.imprimir();
        sueter.imprimir();
        ArrayList <Producto> lista=new ArrayList <Producto>();
        ArrayList <Producto> lista2;
        lista.add(camisa);
        lista.add(sueter);
        lista.add(pantalon);
        
        try {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream("/home/macpolloz/catalogo.txt"));
            System.out.println(salida);
            salida.writeObject(lista);
            salida.close();
            ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("/home/macpolloz/catalogo.txt"));
            lista2=(ArrayList <Producto>)entrada.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

//todo lo que esta comentado nomas era para serializar las primeras cosas, no le hagas caso
  try{
    int port = 3014;
    ServerSocket s = new ServerSocket(port);
    System.out.println("Escuchando por el puerto " + port + ", en espera de una conexión");
    for(;;){
      Socket cl = s.accept();
      System.out.println("Conexión establecida desde" + cl.getInetAddress() + ":" +cl.getPort());
      //Aqui pues hace lo del envio xd
      //El for es para enviar los 4 archivos, el catalogo y 3 imagenes
      DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
      DataInputStream disR = new DataInputStream(cl.getInputStream());
      for (int i = 0; i < 4; i++){
        JFileChooser jf = new JFileChooser();
        int r = jf.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
          File f = jf.getSelectedFile();//Manejador
          String archivo = f.getAbsolutePath(); //Dirección
          String nombre = f.getName(); //Nombre
          long tam = f.length();  //Tamaño
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
            enviados = enviados + n;
            porcentaje = (int)(enviados*100/tam);
            // System.out.print("Enviado: " + porcentaje + "%\r");
          }//While
          System.out.print("Archivo enviado");
          dis.close();
        }
      }    
      
//        System.out.println(disR.read());
      String nombre;
      if ((nombre = disR.readUTF()) != null){
          byte[] b = new byte[1024];
      System.out.println("Se recibio de nuevo el " + nombre + " actualizado");
      long tam = disR.readLong();
      DataOutputStream dosR = new DataOutputStream(new FileOutputStream(nombre));
      long recibidos = 0;
      int n;
      
      while(recibidos < tam){
          n = disR.read(b);
          dosR.write(b, 0, n);
          dosR.flush();
          recibidos = recibidos + n;
      }
      System.out.println("Archivo recibido");
      dosR.close();
      }
          
      
      
      disR.close();
      
      dos.close();
      cl.close(); 
    }
  }catch (Exception e) {
    e.printStackTrace();
  }
 }
}
