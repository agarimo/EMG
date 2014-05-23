package emg.csv;

import main.entidades.Categoria;
import main.entidades.Producto;
import main.entidades.Proveedor;
import main.entidades.SubCategoria;
import main.entidades.SubCategoria2;
import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import main.Inicio;
import main.Log;
import util.Conexion;
import util.Varios;

/**
 *
 * @author Agárimo
 */
public class CsvActiva extends Csv {

    public CsvActiva(File archivo, Conexion con) {
        super(archivo, con);
        super.cargar();
        logProducto = new Log("ACTIVA PRODUCTO ");
        logCategoria = new Log("ACTIVA CATEGORIA ");
    }

    @Override
    public void run() {
        super.conectar();
        procesar();
        super.desconectar();
    }

    private void procesar() {
        System.out.println("Iniciando actualizacion ACTIVA 2000");
        int contador = 1;
        String str;
        Iterator it = super.list.iterator();

        while (it.hasNext()) {
            str = (String) it.next();
            try {
                split(str);
            } catch (SQLException ex) {
                Inicio.log.escribeError("SQLException", ex.getMessage());
            }
            System.out.print("\rProcesando " + Varios.calculaProgreso(contador, super.list.size()) + "%");
            contador++;
        }
        System.out.print("\rActualizacion completada                            ");
        System.out.println();
    }

    private void split(String str) throws SQLException {
        String[] split = str.split(";");

        if (!"Articulos destacados".equals(split[0].trim())) {
            Producto producto = new Producto();
            producto.setIdProveedor(creaProveedor(new Proveedor("ACTIVA 2000")));
            producto.setIdCategoria(creaCategoria(split[0].trim()));
            producto.setIdSubCategoria(creaSubCategoria(split[1].trim(), producto.getIdCategoria()));
            producto.setIdSubCategoria2(creaSubCategoria2(split[2].trim(), producto.getIdSubCategoria()));
            producto.setReferenciaProveedor(split[3].trim());
            producto.setReferenciaFabricante(split[4].trim());
            producto.setNombre(split[7].trim().replace("'", "´"));
            this.ip.setPrecio(Double.parseDouble(split[8].trim()));
            this.ip.setStock(Integer.parseInt(split[9].trim()));
            
            creaProducto(producto, str);
        }
    }

    private int creaProveedor(Proveedor pr) throws SQLException {
        int aux = bd.buscar(pr.SQLBuscar());
        if (aux < 0) {
            bd.ejecutar(pr.SQLCrear());
            aux = bd.ultimoRegistro();
        }
        return aux;
    }

    private int creaCategoria(String nombre) throws SQLException {
        Categoria aux = new Categoria(nombre);
        int id = bd.buscar(aux.SQLBuscar());

        if (id < 1) {
            logCategoria.escribeMsg("Nueva Categoria: " + aux.getNombre());
            bd.ejecutar(aux.SQLCrear());
            id = bd.ultimoRegistro();
        }
        return id;
    }

    private int creaSubCategoria(String nombre, int id_cat) throws SQLException {
        SubCategoria aux = new SubCategoria(id_cat, nombre);
        int id = bd.buscar(aux.SQLBuscar());

        if (id < 1) {
            logCategoria.escribeMsg("Nueva SubCategoria: " + aux.getNombre());
            bd.ejecutar(aux.SQLCrear());
            id = bd.ultimoRegistro();
        }

        return id;
    }

    private int creaSubCategoria2(String nombre, int id_cat) throws SQLException {
        SubCategoria2 aux = new SubCategoria2(id_cat, nombre);
        int id = bd.buscar(aux.SQLBuscar());

        if (id < 1) {
            logCategoria.escribeMsg("Nueva SubCategoria2: " + aux.getNombre());
            bd.ejecutar(aux.SQLCrear());
            id = bd.ultimoRegistro();
        }
        return id;
    }

    private void creaProducto(Producto producto, String linea) throws SQLException {
        int id = bd.buscar(producto.SQLBuscarProveedor());

        if (id < 0) {
            logProducto.escribeMsg("Nuevo producto: " + linea);
            bd.ejecutar(producto.SQLCrear());
            setInfo(bd.ultimoRegistro());
        } else {
            ip.setIdInfo(id);
            actualizaInfo(id);
        }
    }
}