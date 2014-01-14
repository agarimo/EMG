package main;

import drop.entidades.Pedido;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Sql;

/**
 *
 * @author Agárimo
 */
public class SqlDrop {

    private static Sql bd;
    private static ResultSet rs;

    public static List<Pedido> listaPedidoPrestaShop() {
        List<Pedido> list = new ArrayList();
        Pedido aux;

        try {
            bd = new Sql(Main.conPresta);
            rs = bd.ejecutarQueryRs("SELECT * FROM admin_electropresta.EM_orders;");

            while (rs.next()) {
                aux = new Pedido(rs.getInt("id_order"), rs.getString("reference"), rs.getInt("id_customer"),
                        rs.getInt("id_address_delivery"), rs.getInt("current_state"));
                list.add(aux);
            }

            rs.close();
            bd.close();

        } catch (SQLException ex) {
            Logger.getLogger(SqlEmg.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}