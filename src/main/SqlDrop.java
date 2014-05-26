package main;

import drop.entidades.Pedido;
import drop.entidades.PedidoDetalle;
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
                        rs.getInt("id_address_delivery"), rs.getInt("current_state"),false);
                list.add(aux);
            }

            rs.close();
            bd.close();

        } catch (SQLException ex) {
            Logger.getLogger(SqlEmg.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
    
    public static List<Pedido> listaNotificacion() {
        List<Pedido> list = new ArrayList();
        Pedido aux;

        try {
            bd = new Sql(Main.conEmg);
            rs = bd.ejecutarQueryRs("SELECT * FROM electromegusta.pedido WHERE estado=2 AND notificado=false;");

            while (rs.next()) {
                aux = new Pedido(rs.getInt("id_pedido"), rs.getString("codigo_pedido"), rs.getInt("id_cliente"),
                        rs.getInt("id_direccion"), rs.getInt("estado"),rs.getBoolean("notificado"));
                list.add(aux);
            }
            rs.close();
            bd.close();

        } catch (SQLException ex) {
            Logger.getLogger(SqlEmg.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
    
    public static List<PedidoDetalle> listaDetallePedido(){
        List<PedidoDetalle> list=new ArrayList();
        PedidoDetalle aux;
        
        try {
            bd = new Sql(Main.conPresta);
            rs = bd.ejecutarQueryRs("SELECT * FROM admin_electropresta.EM_order_detail;");

            while (rs.next()) {
                aux = new PedidoDetalle(rs.getInt("id_order_detail"),rs.getInt("id_order"),rs.getInt("product_id"),rs.getInt("product_quantity"));
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