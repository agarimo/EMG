package emg.load;

import main.entidades.Producto;
import main.entidades.ProductoFinal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Inicio;
import main.Main;
import util.Sql;
import main.SqlEmg;
import util.Varios;

/**
 *
 * @author Agárimo
 */
public class Trasvase {

    private Sql bd;
    private List<Producto> productos;

    public Trasvase() {
        productos = new ArrayList();
    }

    public void run() {
        System.out.println("Iniciando trasvase de Productos");

        try {
            bd = new Sql(Main.conEmg);
            productos = cargaProductos();
            procesaProductos();
            System.out.println("Trasvase de Productos finalizado");
            bd.close();
        } catch (SQLException ex) {
            Inicio.log.escribeError("SQLException", ex.getMessage());
            Logger.getLogger(Trasvase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void procesaProductos() throws SQLException {
        Iterator it = productos.iterator();
        Producto producto;
        int contador = 1;

        while (it.hasNext()) {
            producto = (Producto) it.next();
            System.out.print("\rTrasvasando " + Varios.calculaProgreso(contador, productos.size()) + "%");
            contador++;
            creaProductoFinal(producto);
        }
        System.out.print("\rTrasvase Completo                            ");
        System.out.println();
    }

    private void creaProductoFinal(Producto producto) throws SQLException {
        ProductoFinal pf = new ProductoFinal();
        pf.setId(producto.getId());
        pf.setIdProveedor(producto.getIdProveedor());
        pf.setIdCategoria(2);
        pf.setNombre(producto.getNombre());
        pf.setReferenciaFabricante(producto.getReferenciaFabricante());
        pf.setReferenciaProveedor(producto.getReferenciaProveedor());
        pf.setPorcentaje(13);
        pf.setPorte(7);
        pf.setActivo(false);

        if (bd.buscar(pf.SQLBuscarId()) < 1) {
            bd.ejecutar(pf.SQLCrear());
            bd.ejecutar(pf.SQLCreaPendiente());
        }
    }

    private List<Producto> cargaProductos() {
        List<Producto> list = SqlEmg.listaProducto("SELECT * FROM electromegusta.producto WHERE id_producto NOT IN (select id_producto from electromegusta.producto_final) AND publicado=1");
        return list;
    }
}
