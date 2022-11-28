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
public class Servercarrito {
    public static void main(String[] args) {
//        producto camisa=new producto("camisa",172,"Una camisa chida chida",10);
//        producto pantalon=new producto("pantalon",150,"Para toda ocasion",10);
//        producto sueter=new producto("sueter",250,"Para el frio",10);
//        camisa.imprimir();
//        pantalon.imprimir();
//        sueter.imprimir();
//        ArrayList <producto> lista=new ArrayList <producto>();
//        ArrayList <producto> lista2;
//        lista.add(camisa);
//        lista.add(sueter);
//        lista.add(pantalon);
//        
//        try {
//            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream("src\\main\\java\\com\\ipn\\mx\\practica1aplicaciones\\catalogo.txt"));
//            System.out.println(salida);
//            salida.writeObject(lista);
//            salida.close();
//            ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("src\\main\\java\\com\\ipn\\mx\\practica1aplicaciones\\catalogo.txt"));
//            lista2=(ArrayList <producto>)entrada.readObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//todo lo que esta comentado nomas era para serializar las primeras cosas, no le hagas caso
        try{
            ServerSocket s = new ServerSocket(7000);
            for(;;){
                Socket cl = s.accept();
                System.out.println("Conexión establecida desde"+cl.getInetAddress()+":"+cl.getPort());
                //Aqui pues hace lo del envio xd
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
