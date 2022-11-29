/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.practica1aplicaciones;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author erikg
 */
public class ClienteCarrito {

    public static void main(String[] args) {
        Producto camisa=new Producto("camisa",172,"Una camisa chida chida",10);
        Producto pantalon=new Producto("pantalon",150,"Para toda ocasion",10);
        Producto sueter=new Producto("sueter",250,"Para el frio",10);
        ArrayList <Producto> lista=new ArrayList <Producto>();
        lista.add(camisa);
        lista.add(sueter);
        lista.add(pantalon);
        ArrayList <Integer> cantidades=new ArrayList <Integer>();
        cantidades.add(2);
        cantidades.add(1);
        cantidades.add(5);
        GeneratePDFFileIText generatePDFFileIText = new GeneratePDFFileIText();
        generatePDFFileIText.createPDF(new File("src\\\\main\\\\java\\\\com\\\\ipn\\\\mx\\\\practica1aplicaciones\\\\GeneratePDFFileIText.pdf"),lista,cantidades);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("\nEscriba la dirección del servidor:\n");
            String host = br.readLine();
            System.out.printf("\n\n Escriba el puerto:\n");
            int pto = Integer.parseInt(br.readLine());
            Socket cl = new Socket(host, pto);
            ArrayList<Producto> lista2 = new ArrayList<Producto>();
            System.out.println("\n\nHola este es el catalogo\n:");

            //Aqui puse el menu pues porque xd no
            Scanner sn = new Scanner(System.in);
            boolean salir = false;
            int opcion; //Guardaremos la opcion del usuario
            while (!salir) {
                System.out.println("1. Agregar producto");
                System.out.println("2. Eliminar producto");
                System.out.println("3. Modificar producto");
                System.out.println("4. Comprar");
                System.out.println("5. Salir");
                System.out.println("Escribe una de las opciones");
                opcion = sn.nextInt();
                switch (opcion) {
                    case 1:
                        System.out.println("Agregue el producto:\n");
                        System.out.printf("\nEscriba el nombre  descripcion existencia\n");
                        String nombre = br.readLine();
                        System.out.printf("\n\n Escriba el precio:\n");
                        float precio = Float.parseFloat(br.readLine());
                        System.out.printf("\nEscriba la descripcion\n");
                        String descripcion = br.readLine();
                        System.out.printf("\nEscriba la cantidad de productos\n");
                        int cantidad = Integer.parseInt(br.readLine());
                        Producto nuevo = new Producto(nombre, precio, descripcion, cantidad);
                        lista2.add(nuevo);
                        break;
                    case 2:
                        System.out.println("\nIngrese el nombre del producto a eliminar:\n");
                        String nombree = br.readLine();
                        for (int i = 0; i < lista2.size(); i++) {
                            if (nombree.equals(lista2.get(i).getNombre())) {
                                lista2.remove(i);
                            }
                        }
                        break;
                    case 3:
                        System.out.println("\nIngrese el nombre del producto a modificar:\n");
                        String nombrem = br.readLine();
                        for (int i = 0; i < lista2.size(); i++) {
                            if (nombrem.equals(lista2.get(i).getNombre())) {
                                System.out.println("\nQue desea modificar:\n");

                            }
                        }
                        break;
                    case 4:
                        System.out.println("\nIngrese el nombre del producto a comprar:\n");
                        String nombrec = br.readLine();
                        for (int i = 0; i < lista2.size(); i++) {
                            if (nombrec.equals(lista2.get(i).getNombre())) {
                                lista2.get(i).disminuirstock();
                            }
                        }
                        break;
                    case 5:
                        salir = true;
                        //pues aqui se sale no sea pendejo
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 4");
                    //Aqui pues te dice que no sea pendejo 
                }
            }
            //Aqui acaba el menu jsjs
            //Aqui recibe pero pues xd no jala
            DataInputStream dis = new DataInputStream(cl.getInputStream());
            byte[] b = new byte[1024];
            String nombre = dis.readUTF();
            System.out.println("Recibimos el archivo:" + nombre);
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombre));
            //ArrayList <Producto> lista2;
            lista2 = (ArrayList<Producto>) entrada.readObject();
            for (int i = 0; i < lista2.size(); i++) {
                lista2.get(i).imprimir();
            }
            entrada.close();
            long tam = dis.readLong();
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
            long recibidos = 0;
            int n, porcentaje;
            while (recibidos < tam) {
                n = dis.read(b);
                dos.write(b, 0, n);
                dos.flush();
                recibidos = recibidos + n;
                porcentaje = (int) (recibidos * 100 / tam);
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
