package drop;

import drop.entidades.Pedido;
import drop.entidades.PedidoDetalle;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import main.SqlDrop;
import main.SqlEmg;
import util.Sql;

/**
 *
 * @author Agárimo
 */
public class Drop {

    public Drop() {
    }

    public void run() {
        try {
            if (checkPedidos()) {
                sincronizaPedidos();
                sincronizaDetallePedidos();
                notifica();
                SqlEmg.actualizaActivos();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Drop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean checkPedidos() throws SQLException {
        int a = checkEmg();
        a=a-2;
        int b = checkPresta();

        if (b != a) {
            return true;
        } else {
            return false;
        }
    }

    private void notifica() {
        Mail mail = new Mail();
        mail.run();
    }

    private void sincronizaPedidos() throws SQLException {
        Iterator it = SqlDrop.listaPedidoPrestaShop().iterator();
        Pedido aux;
        Sql bd = new Sql(Main.conEmg);

        while (it.hasNext()) {
            aux = (Pedido) it.next();

            if (bd.buscar(aux.SQLBuscarCodigo()) < 0) {
                bd.ejecutar(aux.SQLCrear());
            }
        }
        bd.close();
    }
    
    private void sincronizaDetallePedidos() throws SQLException{
        Iterator it = SqlDrop.listaDetallePedido().iterator();
        PedidoDetalle aux;
        Sql bd = new Sql(Main.conEmg);

        while (it.hasNext()) {
            aux = (PedidoDetalle) it.next();

            if (bd.buscar(aux.SQLBuscar()) < 0) {
                System.out.println(aux.SQLCrear());
                bd.ejecutar(aux.SQLCrear());
                bd.ejecutar("UPDATE electromegusta.info_producto SET stock=stock-"+aux.getCantidad()+" where id_info="+aux.getProducto());
            }
        }
        bd.close();
    }

    private int checkEmg() throws SQLException {
        int aux;
        Sql bd = new Sql(Main.conEmg);
        aux = bd.buscar("SELECT count(*) from electromegusta.pedido");
        bd.close();
        return aux;
    }

    private int checkPresta() throws SQLException {
        int aux;
        Sql bd = new Sql(Main.conPresta);
        aux = bd.buscar("SELECT count(*) from admin_electropresta.EM_orders");
        bd.close();
        return aux;
    }
}
