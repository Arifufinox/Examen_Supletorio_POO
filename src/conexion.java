import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    public static Connection conectar() {

        Connection con = null;

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ventas",
                    "root",
                    "root"
            );
            System.out.println("Conectado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

}