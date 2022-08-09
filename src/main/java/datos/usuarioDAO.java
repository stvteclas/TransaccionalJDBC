package datos;

import domain.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static datos.Conexion.close;
import static datos.Conexion.getConnection;

public class usuarioDAO {

    private Connection conexionTransaccional;
    private static final String SQL_SELECT= "SELECT id_usuario, usuario, password FROM usuario";
    private static final String SQL_INSERT=" INSERT INTO usuario(usuario, password) VALUES(?, ?)";
    private static final String SQL_UPDATE="UPDATE usuario SET usuario=?, password=? WHERE id_usuario=?";
    private static final String SQL_DELETE= "DELETE FROM usuario WHERE id_usuario=?";


    public usuarioDAO() {
    }

    public usuarioDAO(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

    public List<Usuario> seleccionar(){
        Connection conn=null;
        PreparedStatement stmt= null;
        ResultSet rs = null;
        Usuario user= null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            conn =this.conexionTransaccional!=null? this.conexionTransaccional: getConnection();
            stmt =conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()){
                int idUsuario=rs.getInt("id_usuario");
                String usuario= rs.getString("usuario");
                String password=rs.getString("password");

                user= new Usuario(idUsuario,usuario,password);

                usuarios.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        finally {
            try {
                close(rs);
                close(stmt);
                if (this.conexionTransaccional==null){
                    Conexion.close(conn);
                }

            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }

        }
        return usuarios;


    }

    public int insertar(Usuario usuario){
        Connection conn=null;
        PreparedStatement stmt=null;
        int registros=0;
        try {
            conn =this.conexionTransaccional!=null? this.conexionTransaccional: getConnection();
            stmt=conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            registros= stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        finally {
            try {
                close(stmt);
                if (this.conexionTransaccional==null){
                    Conexion.close(conn);
                }

            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }

        }
        return registros;
    }

    public int actualizar(Usuario usuario){
        Connection conn=null;
        PreparedStatement stmt=null;
        int registros=0;
        try {
            conn =this.conexionTransaccional!=null? this.conexionTransaccional: getConnection();
            stmt=conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            stmt.setInt(3, usuario.getIdUsuario());
            registros= stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        finally {
            try {
                close(stmt);
                if (this.conexionTransaccional==null){
                    Conexion.close(conn);
                }

            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }

        }
        return registros;
    }

    public int eliminar(Usuario usuario){
        Connection conn=null;
        PreparedStatement stmt=null;
        int registros=0;
        try {
            conn =this.conexionTransaccional!=null? this.conexionTransaccional: getConnection();
            stmt=conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, usuario.getIdUsuario());
            registros= stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        finally {
            try {
                close(stmt);
                if (this.conexionTransaccional==null){
                    Conexion.close(conn);
                }

            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }

        }
        return registros;
    }
}
