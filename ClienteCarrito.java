/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// package com.ipn.mx.practica1aplicaciones;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author erikg
 */
public class ClienteCarrito {
    public static void main(String[] args) {
        try {
            BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("\nEscriba la dirección del servidor:\n");
            String host = br.readLine();
            System.out.printf("\n\n Escriba el puerto:\n");
            int pto= Integer.parseInt(br.readLine());
            Socket cl = new Socket(host, pto);
            System.out.println("\n\nHola este es el catalogo\n:");
            
            //Aqui puse el menu pues porque xd no
            Scanner sn = new Scanner(System.in);
            boolean salir = false;
            int opcion; //Guardaremos la opcion del usuario
            while(!salir){
                System.out.println("1. Agregar Producto");
                System.out.println("2. Eliminar Producto");
                System.out.println("3. Modificar Producto");
                System.out.println("4. Salir");
                System.out.println("Escribe una de las opciones");
                opcion = sn.nextInt();
                switch(opcion){
                    case 1:
                        System.out.println("Has seleccionado la opcion 1");
                        //Aqui pues va lo de agregar xd
                        break;
                    case 2:
                        System.out.println("Has seleccionado la opcion 2");
                        //Aqui va lo de eliminar xd
                        break;
                    case 3:
                        System.out.println("Has seleccionado la opcion 3");
                        //Aqui va lo de modificar xd
                        break;
                    case 4:
                        salir=true;
                        //pues aqui se sale no sea pendejo
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 4");
                        //Aqui pues te dice que no sea pendejo 
                }
            }
            //Aqui acaba el menu jsjs
            //Aqui recibe pero pues xd no jala
            DataInputStream dis= new DataInputStream(cl.getInputStream());
            byte[] b = new byte[1024];
            String nombre = dis.readUTF();
            System.out.println("Recibimos el archivo:"+nombre);
            ObjectInputStream entrada=new ObjectInputStream(new FileInputStream(nombre));
            List<Producto> lista2 = new ArrayList<Producto>();
            lista2 = (List<Producto>) entrada.readObject();
            for(int i=0;i<lista2.size();i++){
                lista2.get(i).imprimir();
            }
            entrada.close();
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
