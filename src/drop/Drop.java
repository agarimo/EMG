package drop;

import drop.entidades.Pedido;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import main.SqlDrop;
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
                notifica();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Drop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean checkPedidos() throws SQLException {
        int a = checkEmg();
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
            bd.ejecutar(aux.SQLCrear());
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
