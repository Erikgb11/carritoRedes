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
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("\nEscriba la dirección del servidor:\n");
            String host = br.readLine();
            System.out.printf("\n\n Escriba el puerto:\n");
            int pto = Integer.parseInt(br.readLine());
            Socket cl = new Socket(host, pto);
            ArrayList<Producto> lista2 = new ArrayList<Producto>();
            System.out.println("\n\nHola este es el catalogo\n:");
            //Aqui recibe pero pues xd si jala
            //De nuevo, el for es para recibir los 4 archivos
            DataInputStream dis = new DataInputStream(cl.getInputStream());
            DataOutputStream dosR = new DataOutputStream(cl.getOutputStream());
            for (int i = 0; i < 4; i++) {
                byte[] b = new byte[1024];
                String nombre = dis.readUTF();
                System.out.println("Recibimos el archivo:" + nombre);
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
                }
                System.out.println("Archivo recibido");
                dos.close();
            }

            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("catalogo.txt"));
            lista2 = (ArrayList) entrada.readObject();
            for (int i = 0; i < lista2.size(); i++) {
                lista2.get(i).imprimir();
            }
            entrada.close();

            //Aqui puse el menu pues porque xd no
            Scanner sn = new Scanner(System.in);
            boolean salir = false;
            int opcion; //Guardaremos la opcion del usuario
            ArrayList<Producto> lista = new ArrayList<Producto>();
            ArrayList<Integer> cantidades = new ArrayList<Integer>();
            while (!salir) {
                System.out.println("1. Comprar producto");
                System.out.println("2. Eliminar producto");
                System.out.println("3. Modificar producto");
                System.out.println("4. Salir");
                System.out.println("Escribe una de las opciones");
                opcion = sn.nextInt();
                switch (opcion) {
                    case 1:
                        int comprar = 1;
                        int conc = 0;
                        do {
                            System.out.println("\nIngrese el nombre del producto a comprar:\n");
                            String nombrec = br.readLine();
                            System.out.printf("\nEscriba la cantidad de productos\n");
                            int cantidadcom = Integer.parseInt(br.readLine());
                            for (int i = 0; i < lista2.size(); i++) {
                                if (nombrec.equals(lista2.get(i).getNombre())) {
                                    conc = 1;
                                    if (cantidadcom <= lista2.get(i).getExistencia()) {
                                        lista.add(lista2.get(i));
                                        lista2.get(i).disminuirstock(cantidadcom);
                                        cantidades.add(cantidadcom);

                                    } else {
                                        System.out.println("\nLo siento no tenemos tantas\n");
                                    }
                                }
                            }
                            if (conc != 1) {
                                System.out.println("\nLo siento no tenemos ese producto a la venta\n");
                            }
                            System.out.println("\nSu carrito esta de la siguiente forma");
                            for (int i = 0; i < lista.size(); i++) {
                                lista.get(i).imprimircarr(cantidades.get(i));
                            }
                            System.out.println("\nDesea comprar mas? 1:si 2:no :\n");
                            comprar = sn.nextInt();
                        } while (comprar <= 1);
                        break;

                    case 2:
                        int cone = 0;
                        System.out.println("\nIngrese el nombre del producto a eliminar:\n");
                        String nombree = br.readLine();
                        for (int i = 0; i < lista.size(); i++) {
                            if (nombree.equals(lista.get(i).getNombre())) {
                                lista.remove(i);
                                lista2.get(i).setExistencia(lista2.get(i).getExistencia()+cantidades.get(i));
                                cone = 1;
                            }
                        }
                        if (cone != 1) {
                            System.out.println("\nNo existe ese producto en su carrito, lo siento\n");
                        }
                        System.out.println("\nSu carrito esta de la siguiente forma");
                        for (int i = 0; i < lista.size(); i++) {
                            lista.get(i).imprimircarr(cantidades.get(i));
                        }
                        break;

                    case 3:
                        int nuevacan = 0;
                        int conm = 0;
                        System.out.println("\nIngrese el nombre del producto a modificar:\n");
                        String nombrem = br.readLine();
                        for (int i = 0; i < lista.size(); i++) {
                            if (nombrem.equals(lista.get(i).getNombre())) {
                                System.out.println("\n Ingrese la cantidad de cosas a comprar");
                                nuevacan = sn.nextInt();
                                lista2.get(i).setExistencia(lista2.get(i).getExistencia()+cantidades.get(i)-nuevacan);
                                cantidades.set(i, nuevacan);
                                cone = 1;
                            }
                        }
                        if (conm != 1) {
                            System.out.println("\nNo existe ese producto en su carrito, lo siento\n");
                        }
                        System.out.println("\nSu carrito esta de la siguiente forma");
                        for (int i = 0; i < lista.size(); i++) {
                            lista.get(i).imprimircarr(cantidades.get(i));
                        }
                        break;

                    case 4:
                        salir = true;
                        //pues aqui se sale no sea pendejo
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 4");
                    //Aqui pues te dice que no sea pendejo 
                }
            }

            try {
                ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("src\\\\main\\\\java\\\\com\\\\ipn\\\\mx\\\\practica1aplicaciones\\\\catalogo.txt"));
                System.out.println(salida);
                salida.writeObject(lista);
                salida.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            File f2 = new File("catalogo.txt");
            String archivo2 = f2.getAbsolutePath();
            String nombre2 = f2.getName();
            long tam2 = f2.length();
            
            DataInputStream disR = new DataInputStream(new FileInputStream(archivo2));
            dosR.writeUTF(nombre2);
            dosR.flush();
            dosR.writeLong(tam2);
            dosR.flush();
            
            byte[] b2 = new byte[1024];
            long enviados2 = 0;
            int n2;
            
            while (enviados2 < tam2){
                n2 = disR.read(b2);
                dosR.write(b2, 0, n2);
                dosR.flush();
                enviados2 = enviados2 + n2;
            }
            System.out.println("Archivo enviado");
            disR.close();
            dosR.close();
            dis.close();
            cl.close();
            
            GeneratePDFFileIText generatePDFFileIText = new GeneratePDFFileIText();
            generatePDFFileIText.createPDF(new File("src\\\\main\\\\java\\\\com\\\\ipn\\\\mx\\\\practica1aplicaciones\\\\GeneratePDFFileIText.pdf"), lista, cantidades);
            //Aqui acaba el menu jsjs
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
