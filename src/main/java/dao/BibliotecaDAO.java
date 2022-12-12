package dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;

import models.Ejemplar;
import models.Libro;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 * @author FranciscoRomeroGuill
 */
public class BibliotecaDAO {

    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("Conexión realizada");
        } catch (Exception ex) {
            System.out.println("Error iniciando Hibernate");
            System.out.println(ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void saveLibro(Libro e) {

        /* Guarda un libro con todos sus ejemplares en la base de datos */
        try (var s = sessionFactory.openSession()) {
            var t = s.beginTransaction();
            s.save(e);
            t.commit();
            s.close();
        }

    }

    public HashSet<Libro> findByEstado(String estado) {

        HashSet<Libro> salida = new HashSet<Libro>();
        /* 
         Devuelve el conjunto de libros que tenga el estado indicado      
        */
        try (var s = sessionFactory.openSession()) {
            Query query = s.createQuery("from Ejemplar where estado =:estado");
            query.setParameter("estado", estado);
            var listado = (ArrayList<Ejemplar>) query.list();

            for( Ejemplar ej: listado){
                salida.add(ej.getLibro());
            }
        }
        System.out.println("Método findByEstado no implementado");

        return salida;

    }

    public void printInfo() {
        
        /* 
          Muestra por consola todos los libros de la biblioteca y el número
          de ejemplares disponibles de cada uno.
          
          Debe imprimirlo de esta manera:
        
          Biblioteca
          ----------
          Como aprender java en 24h. (3)
          Como ser buena persona (1)
          Aprobando exámenes fácilmente (5)
          ...
        
        */
        try(var s = sessionFactory.openSession()){
         Query q = s.createQuery("from Libro");
         ArrayList<Libro> result = (ArrayList<Libro>) q.list();
         result.forEach(libro -> {
             System.out.println(libro.getTitulo()+"."+"("+libro.getEjemplares().size()+")");
         });

        }

    }

}
