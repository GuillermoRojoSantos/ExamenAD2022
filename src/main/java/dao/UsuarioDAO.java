package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.Usuario;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UsuarioDAO {

    private Connection connection;

    /* Completar consultas */
    static final String INSERT_QUERY = "INSERT INTO `usuario` (`id`, `nombre`, `apellidos`, `dni`) VALUES (NULL, ?, ?, ?);";
    static final String LIST_QUERY = "SELECT * FROM `usuario`;";
    static final String GET_BY_DNI = "SELECT * FROM `usuario` WHERE usuario.dni=?;";
    static final String URL = "jdbc:mysql://localhost:3306/examenad2022";
    static final String USER = "root";
    static final String PASSWORD = "";


    public void connect() {
        try {

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectada correctamente");
        } catch (Exception ex) {
            System.out.println("Error al conectar a la base de datos");
            System.out.println("ex");
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception ex) {
            System.out.println("Error al cerrar la base de datos");
        }
    }

    public void save(Usuario user) {

        /**
         * Completar código para guardar un usuario 
         * Este método no debe generar el id del usuario, ya que la base
         * de datos es la que lo genera.
         * Una vez obtenido el id del usuario tras la consulta sql,
         * hay que modificarlo en el objeto que se pasa como parámetro 
         * de forma que pase a tener el id correcto.
         */
        try (var pst = connection.prepareStatement(INSERT_QUERY, RETURN_GENERATED_KEYS)){
            pst.setString(1,user.getNombre());
            pst.setString(2,user.getApellidos());
            pst.setString(3, user.getDni());

            if (pst.executeUpdate()>0){
                var keys =pst.getGeneratedKeys();
                keys.next();
                user.setId(keys.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Usuario> list() {
        var salida = new ArrayList<Usuario>(0);
        try (var pst = connection.prepareStatement(LIST_QUERY)) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                var us = new Usuario();
                us.setId(resultSet.getLong("id"));
                us.setNombre(resultSet.getString("nombre"));
                us.setApellidos(resultSet.getString("apellidos"));
                us.setDni(resultSet.getString("dni"));
                salida.add(us);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /* Completar código para devolver un arraylist con todos los usuarios */

        return salida;
    }

    public Usuario getByDNI(String dni) {

        Usuario salida = new Usuario();

        try (var pst = connection.prepareStatement(GET_BY_DNI)) {
            pst.setString(1, dni);
            var resultSet = pst.executeQuery();
                while (resultSet.next()){
                    salida.setId(resultSet.getLong("id"));
                    salida.setNombre(resultSet.getString("nombre"));
                    salida.setApellidos(resultSet.getString("apellidos"));
                    salida.setDni(resultSet.getString("dni"));
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /**
         * Completar código para devolver el usuario que tenga ese dni.
         * Si no existe, se debe devolver null
         */

        return salida;
    }
}
