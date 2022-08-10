package test;

import datos.Conexion;
import datos.PersonaDAO;
import datos.PersonaDAOjdbc;
import domain.PersonaDTO;

import java.sql.*;
import java.util.List;

public class TestManejoPersonas {
    public static void main(String[] args) {
        Connection conexion=null;
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            PersonaDAO personaDAO = new PersonaDAOjdbc(conexion);
            List<PersonaDTO> personas = personaDAO.select();

            for(PersonaDTO persona: personas){
                System.out.println("persona = " + persona);
            }
            conexion.commit();
            System.out.println("se ha hecho commit de la transaccion");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            System.out.println("Entramos al rollback");
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
}
