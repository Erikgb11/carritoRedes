/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.carroserializable;
import javax.swing.*;
import java.net.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author erikg
 */
public class Carro implements Serializable{
    private transient String Color;
    private String Placa;
    private String Marca;
    private int año;
    private transient int npuertas;
    private transient float precio;

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String Placa) {
        this.Placa = Placa;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getNpuertas() {
        return npuertas;
    }

    public void setNpuertas(int npuertas) {
        this.npuertas = npuertas;
    }

    public float getPrecio() {
        return precio;
    }
    
    public void setPrecio(float precio) {
        this.precio = precio;
    }
    public static void main(String[] args){
        Carro carrito =new Carro();
        carrito.setColor("Rojo");
        carrito.setPlaca("456-HGN");
        carrito.setMarca("Nissan");
        carrito.setAño(2001);
        carrito.setNpuertas(4);
        carrito.setPrecio(5000);
        System.out.println(carrito.getColor());
        System.out.println(carrito.getPlaca());
        System.out.println(carrito.getMarca());
        System.out.println(carrito.getAño());
        System.out.println(carrito.getNpuertas());
        System.out.println(carrito.getPrecio());
        try {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream("cliente.txt"));
            System.out.println(salida);
            salida.writeObject(carrito);
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
        try{
            BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("\nEscriba la dirección del servidor:\n");
            String host = br.readLine();
            System.out.printf("\nEscriba el puerto:\n");
            int pto= Integer.parseInt(br.readLine());
            Socket cl = new Socket(host, pto);
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
        }catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}

