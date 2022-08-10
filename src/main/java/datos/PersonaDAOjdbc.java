package datos;

import domain.PersonaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static datos.Conexion.*;

public class PersonaDAOjdbc implements PersonaDAO {
    private Connection conexionTransaccional;

    private static final String SQL_SELECT= "SELECT id_persona, nombre, apellido, email, telefono FROM persona";
    private static final String SQL_INSERT=" INSERT INTO persona(nombre, apellido, email, telefono) VALUES(?, ?, ?, ?)";
    private static final String SQL_UPDATE="UPDATE persona SET nombre=?, apellido=?, email=?, telefono=? WHERE id_persona=?";
    private static final String SQL_DELETE= "DELETE FROM persona WHERE id_persona=?";

    public PersonaDAOjdbc() {
    }

    public PersonaDAOjdbc(Connection conexionTransaccional) {

        this.conexionTransaccional = conexionTransaccional;
    }

    public List<PersonaDTO> select() throws SQLException {
        Connection conn=null;
        PreparedStatement stmt= null;
        ResultSet rs = null;
        PersonaDTO persona= null;
        List<PersonaDTO> personas = new ArrayList<PersonaDTO>();

        try {
            conn =this.conexionTransaccional!=null? this.conexionTransaccional: Conexion.getConnection();
            stmt =conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()){
                int idPersona=rs.getInt("id_persona");
                String nombre= rs.getString("nombre");
                String apellido=rs.getString("apellido");
                String email=rs.getString("email");
                String telefono=rs.getString("telefono");

                persona = new PersonaDTO();
                persona.setIdPersona(idPersona);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                persona.setEmail(email);
                persona.setTelefono(telefono);

                personas.add(persona);
            }
        }
        finally {
                close(rs);
                close(stmt);
                if (this.conexionTransaccional==null){
                    Conexion.close(conn);
                }
            }

        return personas;


    }

    public int insert(PersonaDTO persona) throws SQLException {
        Connection conn=null;
        PreparedStatement stmt=null;
        int registros=0;
        try {
            conn =this.conexionTransaccional!=null? this.conexionTransaccional: Conexion.getConnection();
            stmt=conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());

            System.out.println("ejecutando query:" + SQL_INSERT);
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

    public int update(PersonaDTO persona) throws SQLException {
        Connection conn=null;
        PreparedStatement stmt=null;
        int registros=0;
        try {
            conn =this.conexionTransaccional!=null? this.conexionTransaccional: Conexion.getConnection();
            System.out.println("ejecutando query:" + SQL_UPDATE);
            stmt=conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());
            stmt.setInt(5, persona.getIdPersona());
            registros= stmt.executeUpdate();
            System.out.println("registros actualizados = " + registros);
        }
        finally {
                close(stmt);
                if (this.conexionTransaccional==null){
                    Conexion.close(conn);
                }

            }
        return registros;
    }

    public int delete(PersonaDTO persona) throws SQLException {
        Connection conn=null;
        PreparedStatement stmt=null;
        int registros=0;
        try {
            conn =this.conexionTransaccional!=null? this.conexionTransaccional: Conexion.getConnection();
            System.out.println("ejecutando query:" + SQL_DELETE);
            stmt=conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, persona.getIdPersona());
            registros= stmt.executeUpdate();
        }
        finally {
                Conexion.close(stmt);
                if (this.conexionTransaccional==null){
                    Conexion.close(conn);
                }
            }
        return registros;
    }
}
