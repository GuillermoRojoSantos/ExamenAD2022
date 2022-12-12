package com.cesur.examenaddicc22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Ejercicio1 {

    /**
     * Enunciado:
     * 
     * Completar el método estadísticasDeArchivo de manera que lea el archivo
     * de texto que se le pasa como parámetro, lo analice y muestre por consola 
     * el número de caracteres, palabras y líneas total.
     * 
     * Modificar solo el código del método.
     * 
     */
    
    static void solucion() {

        estadísticasDeArchivo("pom.xml");
    }

    private static void estadísticasDeArchivo(String archivo) {
        
        /* añadir código */
        int numLetras=0;
        int numPalabras=1;
        int numLineas=1;
        Scanner sc = null;
        try {
            sc = new Scanner(new File(archivo));
            sc.useDelimiter("\n");
            while (sc.hasNext()){
                String s = sc.nextLine();
                for (int i=0;i<s.length();i++){
                    if((int)s.charAt(i)>97 || (int)s.charAt(i)<122 && (int)s.charAt(i)!=32){
                        numLetras++;
                    }else if(s.charAt(i)==32){
                        numPalabras++;
                    }
                }
            }
            System.out.printf("El número de letras es %d%n" +
                    "El número de palabras es %d%n" +
                    "Y el numero de lineas es %d%n",
                    numLetras,numPalabras,numLineas);
            //System.out.println("Ejercicio no resuelto");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    
}
