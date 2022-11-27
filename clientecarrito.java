/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.practica1aplicaciones;

import javax.swing.*;
import java.net.*;
import java.io.*;
/**
 *
 * @author erikg
 */
public class clientecarrito {
    public static void main(String[] args) {
        try {
            BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("\nEscriba la dirección del servidor:\n");
            String host = br.readLine();
            System.out.printf("\n\n Escriba el puerto:\n");
            int pto= Integer.parseInt(br.readLine());
            Socket cl = new Socket(host, pto);
            DataInputStream dis= new DataInputStream(cl.getInputStream());
            byte[] b = new byte[1024];
            String nombre = dis.readUTF();
            System.out.println("Recibimos el archivo:"+nombre);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
