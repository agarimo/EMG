package emg.load;

import main.entidades.Descripcion;
import main.entidades.Imagen;
import main.entidades.ProductoFinal;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import main.Inicio;
import main.Main;
import util.Sql;
import main.SqlEmg;
import main.entidades.InfoProducto;
import util.Split;
import util.Varios;

/**
 *
 * @author Agárimo
 */
public class CsvInsercion {

    private String[] cabecera;
    private List<ProductoFinal> list;
    private ProductoFinal producto;
    private InfoProducto ip;
    private File directorio = new File("insercion");
    private File csv;

    public CsvInsercion() {
        cabecera = new String[]{"id_producto", "proveedor", "categoria", "nombre", "ref_fabricante", "ref_proveedor", "stock", "precio_venta", "imagenes", "descripcion"};
        creaArchivos();
        cargaProductos();
    }

    public void run() {
        int contador = 1;
        System.out.println("Generando CSV INSERCION");
        Iterator it = list.iterator();

        while (it.hasNext()) {
            producto = (ProductoFinal) it.next();
            procesar(producto);
            System.out.print("\rGenerando " + Varios.calculaProgreso(contador, list.size()) + "%");
            contador++;
        }
        Split sp = new Split(csv, 40);
        sp.split();
        System.out.print("\rTarea Finalizada                            ");
        System.out.println();
    }

    private void cargaProductos() {
        System.out.println("Iniciando datos");
        list = SqlEmg.listaProductoFinal("SELECT * FROM electromegusta.producto_final WHERE id_producto IN "
                + "(SELECT id_producto_final FROM electromegusta.pendiente_publicacion)");
        System.out.println("Datos cargados");
    }

    private void creaArchivos() {
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        csv = new File(directorio, "PendienteInsercion.csv");

        if (!csv.exists()) {
            try {
                csv.createNewFile();
                escribeLinea(buildLinea(cabecera));
            } catch (IOException ex) {
                Inicio.log.escribeError("IOException", ex.getMessage());
            }
        }
    }

    private void procesar(ProductoFinal pf) {
        getInfo(pf.getId());
        String linea;
        String[] aux = new String[10];
        aux[0] = Integer.toString(pf.getId());
        aux[1] = Integer.toString(pf.getIdProveedor());
        aux[2] = Integer.toString(pf.getIdCategoria());
        aux[3] = pf.getNombre();
        aux[4] = pf.getReferenciaFabricante();
        aux[5] = pf.getReferenciaProveedor();
        aux[6] = Integer.toString(ip.getStock());
        aux[7] = new DecimalFormat("0.00").format(ip.getPrecio());
        aux[8] = getImagen(pf);
//        aux[9] = getDescripcion(pf).trim().replace("\r", "").replace(";", "");
        aux[9] = "DESCRIPCION".trim().replace("\r", "").replace(";", "");

        linea = buildLinea(aux);
        escribeLinea(linea);
        borraPendiente(pf.getId());
    }

    private void borraPendiente(int idProducto) {
        try {
            Sql bd = new Sql(Main.conEmg);
            bd.ejecutar("DELETE FROM electromegusta.pendiente_publicacion WHERE id_producto_final=" + idProducto);
            bd.close();
        } catch (SQLException ex) {
            Inicio.log.escribeError("SQLException", ex.getMessage());
        }
    }
    
    private void getInfo(int id){
        ip = SqlEmg.cargaInfoProducto(new InfoProducto(id));
    }
   
    private String getDescripcion(ProductoFinal pf) {
        Descripcion de = SqlEmg.cargarDescripcion(new Descripcion(pf.getId()));
        if (de == null) {
            return "";
        } else {
            return de.getDescripcion();
        }
    }

    private String getImagen(ProductoFinal pf) {
        String aux = "";
        Imagen imagen;
        List<Imagen> lista;
        Iterator it;
        lista = SqlEmg.listarImagenes("SELECT * FROM electromegusta.imagen WHERE id_producto=" + pf.getId());
        it = lista.iterator();

        while (it.hasNext()) {
            imagen = (Imagen) it.next();
            aux = aux + imagen.getEnlace() + ",";
        }
        if (aux.length() > 1) {
            return aux.substring(0, aux.length() - 1);
        } else {
            return "";
        }
    }

    private String buildLinea(String[] aux) {
        String str = "";

        for (int i = 0; i < aux.length; i++) {
            str = str + aux[i] + ";";
        }

        return str.substring(0, str.length() - 1);
    }

    public void escribeLinea(String linea) {
        try {
            try (BufferedWriter out = new BufferedWriter(new FileWriter(csv, true))) {
                out.write(linea);
                out.newLine();
            }
        } catch (IOException ex) {
            Inicio.log.escribeError("IOException", ex.getMessage());
        }
    }
}
