import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class crud extends JFrame{
    private JPanel panel1;
    private JTable table1;
    private JButton buscarButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton crearButton;
    private JTextField textField1;


    public crud() {
        setTitle("Crud");
        setSize(700,500);
        setContentPane(panel1);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cargar_tabla();


        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = JOptionPane.showInputDialog("Ingrese un cedula");
                String nombre = JOptionPane.showInputDialog("Ingrese un nombre");
                String apellido = JOptionPane.showInputDialog("Ingrese un apellido");
                String email = JOptionPane.showInputDialog("Ingrese un email");
                String telefono = JOptionPane.showInputDialog("Ingrese un telefono");
                String rol = JOptionPane.showInputDialog("Ingrese un rol");
                String estado = JOptionPane.showInputDialog("Ingrese un estado");

                if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || rol.isEmpty() || estado.isEmpty()
                || telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "No dejes ningun campo vacio!"
                    );
                }

                try {
                    Boolean estadox = Boolean.parseBoolean(estado);

                    Connection con = conexion.conectar();

                    String sql = "insert into cliente (cedula, nombre, apellidos, email, telefono, rol, estado) values (?,?,?,?,?,?,?)";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, cedula);
                    ps.setString(2, nombre);
                    ps.setString(3, apellido);
                    ps.setString(4, email);
                    ps.setString(5, telefono);
                    ps.setString(6, rol);
                    ps.setBoolean(7, estadox);

                    ps.executeUpdate();

                    ps.close();
                    con.close();

                    cargar_tabla();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = table1.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Seleccione una fila por favor."
                    );
                    return;
                }

                int oid = Integer.parseInt(table1.getValueAt(fila, 0).toString());
                String ocedula = table1.getValueAt(fila, 1).toString();
                String onombre = table1.getValueAt(fila, 2).toString();
                String oapellido = table1.getValueAt(fila, 3).toString();
                String oemail = table1.getValueAt(fila, 4).toString();
                String otelefono = table1.getValueAt(fila, 5).toString();
                String orol = table1.getValueAt(fila, 6).toString();
                String oestado = table1.getValueAt(fila, 7).toString();

                String ncedula = JOptionPane.showInputDialog("Ingrese un cedula", ocedula);
                String nnombre = JOptionPane.showInputDialog("Ingrese un nombre", onombre);
                String napellido = JOptionPane.showInputDialog("Ingrese un apellido", oapellido);
                String nemail = JOptionPane.showInputDialog("Ingrese un email", oemail);
                String ntelefono = JOptionPane.showInputDialog("Ingrese un telefono", otelefono);
                String nrol = JOptionPane.showInputDialog("Ingrese su rol", orol);
                String nestado = JOptionPane.showInputDialog("Ingrese un estado", oestado);

                if (ncedula.isEmpty() || nnombre.isEmpty() || napellido.isEmpty()
                    || nemail.isEmpty() || ntelefono.isEmpty() || nrol.isEmpty() || nestado.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "No dejes ningun campo vacio!"
                    );
                    return;
                }


                try {
                    Boolean estadox = Boolean.parseBoolean(nestado);

                    Connection con = conexion.conectar();

                    String sql = "update cliente set cedula = ?, nombre = ?, apellidos = ?, email = ?, telefono = ?, rol = ?, estado = ? where id_cliente = ?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, ncedula);
                    ps.setString(2, nnombre);
                    ps.setString(3, napellido);
                    ps.setString(4, nemail);
                    ps.setString(5, ntelefono);
                    ps.setString(6, nrol);
                    ps.setBoolean(7, estadox);
                    ps.setInt(8, oid);

                    ps.executeUpdate();

                    ps.close();
                    con.close();

                    cargar_tabla();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = table1.getSelectedRow();

                if (fila == -1) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Seleccione una fila por favor."
                    );
                    return;
                }

                int id = Integer.parseInt(table1.getValueAt(fila, 0).toString());

                int confirmacion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro de eliminar?",
                        "Confirmacion",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        Connection con = conexion.conectar();

                        String sql = "delete from cliente where id_cliente = ?";
                        PreparedStatement ps = con.prepareStatement(sql);

                        ps.setInt(1, id);

                        ps.executeUpdate();

                        ps.close();
                        con.close();

                        cargar_tabla();

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String texto = textField1.getText();

                if (texto.isEmpty()) {
                    cargar_tabla();
                    return;
                }

                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("id_cliente");
                model.addColumn("cedula");
                model.addColumn("nombre");
                model.addColumn("apellidos");
                model.addColumn("email");
                model.addColumn("telefono");
                model.addColumn("rol");
                model.addColumn("estado");

                table1.setModel(model);

                try {
                    Connection con = conexion.conectar();

                    String sql = "select * from cliente where cedula like ?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, "%" + texto + "%");

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()){
                        Object[] fila = new Object[8];
                        fila [0] = rs.getInt("id_cliente");
                        fila [1] = rs.getString("cedula");
                        fila [2] = rs.getString("nombre");
                        fila [3] = rs.getString("apellidos");
                        fila [4] = rs.getString("email");
                        fila [5] = rs.getString("telefono");
                        fila [6] = rs.getString("rol");
                        fila [7] = rs.getBoolean("estado");

                        model.addRow(fila);

                    }

                    rs.close();
                    ps.close();
                    con.close();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void cargar_tabla(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id_cliente");
        model.addColumn("cedula");
        model.addColumn("nombre");
        model.addColumn("apellidos");
        model.addColumn("email");
        model.addColumn("telefono");
        model.addColumn("rol");
        model.addColumn("estado");

        table1.setModel(model);

        try {
            Connection con = conexion.conectar();

            String sql = "select * from cliente";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Object[] fila = new Object[8];
                fila [0] = rs.getInt("id_cliente");
                fila [1] = rs.getString("cedula");
                fila [2] = rs.getString("nombre");
                fila [3] = rs.getString("apellidos");
                fila [4] = rs.getString("email");
                fila [5] = rs.getString("telefono");
                fila [6] = rs.getString("rol");
                fila [7] = rs.getBoolean("estado");

                model.addRow(fila);

            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
