package test;

import datos.Conexion;
import datos.UsuarioDao;
import datos.usuarioDAOjdbc;

import domain.UsuarioDTO;

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
                UsuarioDao usuarioDao= new usuarioDAOjdbc(conexion);
                List<UsuarioDTO> usuarios= usuarioDao.select();
                for (UsuarioDTO usuario: usuarios){
                    System.out.println("usuario = " + usuario);
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
