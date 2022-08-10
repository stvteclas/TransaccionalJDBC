package datos;

import domain.UsuarioDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static datos.Conexion.close;
import static datos.Conexion.getConnection;

public class usuarioDAOjdbc implements UsuarioDao{

    private Connection conexionTransaccional;
    private static final String SQL_SELECT= "SELECT id_usuario, usuario, password FROM usuario";
    private static final String SQL_INSERT=" INSERT INTO usuario(usuario, password) VALUES(?, ?)";
    private static final String SQL_UPDATE="UPDATE usuario SET usuario=?, password=? WHERE id_usuario=?";
    private static final String SQL_DELETE= "DELETE FROM usuario WHERE id_usuario=?";


    public usuarioDAOjdbc() {
    }

    public usuarioDAOjdbc(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

    public List<UsuarioDTO> select() throws SQLException{
        Connection conn=null;
        PreparedStatement stmt= null;
        ResultSet rs = null;
        UsuarioDTO user= null;
        List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();

        try {
            conn =this.conexionTransaccional!=null? this.conexionTransaccional: Conexion.getConnection();
            stmt =conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()){
                int idUsuario=rs.getInt("id_usuario");
                String usuario= rs.getString("usuario");
                String password=rs.getString("password");

                user= new UsuarioDTO();
                user.setIdUsuario(idUsuario);
                user.setUsuario(usuario);
                user.setPassword(password);

                usuarios.add(user);
            }
        }
        finally {
                close(rs);
                close(stmt);
                if (this.conexionTransaccional==null){
                    Conexion.close(conn);
                }

        }
        return usuarios;


    }

    public int insert(UsuarioDTO usuario) throws SQLException {
        Connection conn=null;
        PreparedStatement stmt=null;
        int registros=0;
        try {
            conn =this.conexionTransaccional!=null? this.conexionTransaccional: Conexion.getConnection();
            stmt=conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            System.out.println("ejecutando query= " + SQL_INSERT);
            registros= stmt.executeUpdate();
            System.out.println("registros afectados = " + registros);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        finally {
            close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }

        }
        return registros;
    }

    public int update(UsuarioDTO usuario) throws SQLException {
        Connection conn=null;
        PreparedStatement stmt=null;
        int registros=0;
        try {
            conn =this.conexionTransaccional!=null? this.conexionTransaccional: Conexion.getConnection();
            stmt=conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            stmt.setInt(3, usuario.getIdUsuario());
            System.out.println("ejecutando query= " + SQL_UPDATE);
            registros= stmt.executeUpdate();
            System.out.println("registros afectados = " + registros);
        }
        finally {
                close(stmt);
                if (this.conexionTransaccional==null){
                    Conexion.close(conn);
                }



        }
        return registros;
    }

    public int delete(UsuarioDTO usuario) throws SQLException {
        Connection conn=null;
        PreparedStatement stmt=null;
        int registros=0;
        try {
            conn =this.conexionTransaccional!=null? this.conexionTransaccional: Conexion.getConnection();
            stmt=conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, usuario.getIdUsuario());
            registros= stmt.executeUpdate();
            System.out.println("ejecutando query= " + SQL_DELETE);
        }
        finally {
                close(stmt);
                if (this.conexionTransaccional==null){
                    Conexion.close(conn);
                }


        }
        return registros;
    }
}
