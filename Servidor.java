/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.carroserializable;
import java.net.*;
import java.io.*;
/**
 *
 * @author erikg
 */
public class Servidor {
    public static void main(String[] args){
        try{
            ServerSocket s = new ServerSocket(7000);
            for(;;){
                Socket cl = s.accept();
                System.out.println("Conexión establecida desde"+cl.getInetAddress()+":"+cl.getPort());
                DataInputStream dis= new DataInputStream(cl.getInputStream());
                byte[] b = new byte[1024];
                String nombre = dis.readUTF();
                System.out.println("Recibimos el archivo:"+nombre);
                
                ObjectInputStream entrada=new ObjectInputStream(new FileInputStream(nombre));
                Carro carrito=(Carro) entrada.readObject();
                entrada.close();
                System.out.println(carrito.getColor());
                System.out.println(carrito.getPlaca());
                System.out.println(carrito.getMarca());
                System.out.println(carrito.getAño());
                System.out.println(carrito.getNpuertas());
                System.out.println(carrito.getPrecio());
                
                long tam= dis.readLong();
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
                long recibidos=0;
                int n, porcentaje;
                while(recibidos < tam){
                    n = dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    recibidos = recibidos + n;
                    porcentaje = (int)(recibidos*100/tam);
                    System.out.print("\n\n Archivo recibido.");
                }//While
                dos.close();
                dis.close();
                cl.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }
}
