package emg.load;

import main.entidades.Producto;
import main.entidades.ProductoFinal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    private List<RelacionCategoria> relaciones;
    private List<Producto> productos;
    private RelacionCategoria aux;

    public Trasvase() {
        productos = new ArrayList();
        cargaRelaciones();
    }

    public void run() {
        System.out.println("Iniciando trasvase de Productos");
        Iterator it = relaciones.iterator();

        try {
            bd = new Sql(Main.conEmg);
            while (it.hasNext()) {
                aux = (RelacionCategoria) it.next();
                System.out.println("Iniciando Trasvase :"+aux.getNombreCategoria()+" - "+aux.getNombreCategoriaFinal());
                productos = cargaProductos(aux);
                procesaProductos();
            }
            System.out.println("Trasvase de Productos finalizado");
            bd.close();
        } catch (SQLException ex) {
            Inicio.log.escribeError("SQLException", ex.getMessage());
        }
    }

    private void procesaProductos() throws SQLException {
        Iterator it = productos.iterator();
        Producto producto;
        int contador=1;

        while (it.hasNext()) {
            producto = (Producto) it.next();
            System.out.print("\rTrasvasando "+Varios.calculaProgreso(contador, productos.size()) + "%");
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
        pf.setIdCategoria(aux.getCategoriaFinal());
        pf.setNombre(producto.getNombre());
        pf.setReferenciaFabricante(producto.getReferenciaFabricante());
        pf.setReferenciaProveedor(producto.getReferenciaProveedor());
        pf.setStock(producto.getStock());
        pf.setPrecioCoste(producto.getPrecioCoste());
        pf.setPorcentaje(13);
        pf.setPorte(7);
        pf.setActivo(false);

        if (bd.buscar(pf.SQLBuscarId()) < 1) {
            bd.ejecutar(pf.SQLCrear());
            bd.ejecutar(pf.SQLCreaPendiente());
        }
    }

    private void cargaRelaciones() {
        this.relaciones = SqlEmg.listarRelacionCategorias("SELECT * FROM electromegusta.relaciones");
    }

    private List<Producto> cargaProductos(RelacionCategoria relacion) {
        List<Producto> list = SqlEmg.listaProducto("SELECT * FROM electromegusta.producto WHERE id_subcategoria2=" + relacion.getCategoria());
        return list;
    }
}
