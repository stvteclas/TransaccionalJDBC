package test;

import datos.Conexion;
import datos.usuarioDAO;
import datos.usuarioDAO;

import domain.Usuario;
import domain.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestManejoUsuarios {
        public static void main(String[] args) {
            Connection conexion=null;
            try {
                conexion= Conexion.getConnection();
                if (conexion.getAutoCommit()) {
                    conexion.setAutoCommit(false);
                }
                usuarioDAO usuarioDAO = new usuarioDAO(conexion);
                Usuario cambioUsuario= new Usuario();
                cambioUsuario.setIdUsuario(4);
                cambioUsuario.setUsuario("Ricardo");
                cambioUsuario.setPassword("Murphy");
                usuarioDAO.actualizar(cambioUsuario);

                Usuario nuevoUsuario= new Usuario();
               nuevoUsuario.setUsuario("Romina");
               nuevoUsuario.setPassword("tokio22");
                usuarioDAO.insertar(nuevoUsuario);

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
