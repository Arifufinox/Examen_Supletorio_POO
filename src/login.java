import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;

public class login {
    protected JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton iniciarButton;

    private int intentos = 3;

    public int getIntentos() {return intentos;}
    public void setIntentos(int intentos) {this.intentos = intentos;}

    public login() {
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (intentos == 0){
                    JOptionPane.showMessageDialog(
                            null,
                            "Intentos acabados."
                    );
                    iniciarButton.setEnabled(false);
                    return;
                }

                String usuario = textField1.getText();
                String contrasena = passwordField1.getText();

                if (usuario.isEmpty() || contrasena.isEmpty()){
                    JOptionPane.showMessageDialog(
                            null,
                            "No deje ningun campo vacio."
                    );
                    return;
                }

                try {
                    Connection con = conexion.conectar();

                    String sql = "select * from usuarios where correo = ? and password = ?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, usuario);
                    ps.setString(2, contrasena);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()){
                        JOptionPane.showMessageDialog(
                                null,
                                "Bienenido!"
                        );

                        crud crud = new crud();
                        crud.setVisible(true);
                        SwingUtilities.getWindowAncestor(crud);


                    } else {
                        intentos--;
                        JOptionPane.showMessageDialog(
                                null, "Contraseña o correo incorrecta, te faltan "+ intentos +" intentos."
                        );
                    }


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}
