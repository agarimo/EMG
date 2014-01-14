package emg.csv;

import main.entidades.PrestaShop;
import main.entidades.ProductoFinal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import main.Inicio;
import main.Log;
import main.Main;
import util.Conexion;
import util.Sql;

/**
 *
 * @author Agarimo
 */
public class Csv implements Runnable {

    File archivo;
    Conexion con;
    Sql bd;
    Sql bdPresta;
    boolean activo;
    List<String> list;
    List listPublicado;
    int stock;
    double precio;
    Log logProducto;
    Log logCategoria;

    public Csv() {
    }

    public Csv(File archivo, Conexion con) {
        this.archivo = archivo;
        this.con = con;
        this.activo = false;
        list = new ArrayList();
    }

    @Override
    public void run() {
        conectar();
        desconectar();
    }

    protected void cargar() {
        FileReader fr = null;
        try {
            fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                list.add(linea);
            }
        } catch (IOException ex) {
            Inicio.log.escribeError("IOException", ex.getMessage());
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Inicio.log.escribeError("IOException", ex.getMessage());
            }
        }
        list.remove(0);
    }

    protected void cargaListPublicado() {
        this.listPublicado = null;
    }

    protected ProductoFinal buscarEnList(ProductoFinal pf) {
        ProductoFinal aux;
        Iterator it = listPublicado.iterator();

        while (it.hasNext()) {
            aux = (ProductoFinal) it.next();
            if (pf.getReferenciaProveedor().equals(aux.getReferenciaProveedor())) {
                return aux;
            }
        }
        return null;
    }

    public void listar() {
        Iterator it = list.iterator();

        while (it.hasNext()) {
            System.out.println((String) it.next());
        }
    }

    protected void conectar() {
        try {
            bd = new Sql(con);
            if (!Inicio.offline) {
                bdPresta = new Sql(Main.conPresta);
            }
            activo = true;
        } catch (SQLException ex) {
            Inicio.log.escribeError("SQLException", ex.getMessage());
        }
    }

    protected void desconectar() {
        try {
            bd.close();
            if (!Inicio.offline) {
                bdPresta.close();
            }
            activo = false;
        } catch (SQLException ex) {
            Inicio.log.escribeError("SQLException", ex.getMessage());
        }
    }

    protected int setStock() throws SQLException {
        int id;
        String query = "INSERT into electromegusta.stock (stock,last_update) values("
                + this.stock + ","
                + "curdate()"
                + ")";
        bd.ejecutar(query);
        id = bd.ultimoRegistro();
        return id;
    }

    protected int setPrecio() throws SQLException {
        int id;
        String query = "INSERT into electromegusta.precio_coste (precio,last_update) values("
                + this.precio + ","
                + "curdate()"
                + ")";
        bd.ejecutar(query);
        id = bd.ultimoRegistro();
        return id;
    }

    protected void actualizaTarifaPresta(ProductoFinal pf) throws SQLException {
        if (!Inicio.offline) {
            PrestaShop ps = new PrestaShop(pf, this.stock, this.precio);
            bdPresta.ejecutar(ps.updatePrice());
        }
    }

    protected void actualizaStockPresta(ProductoFinal pf) throws SQLException {
        if (!Inicio.offline) {
            PrestaShop ps = new PrestaShop(pf, this.stock, this.precio);
            bdPresta.ejecutar(ps.updateStock());
        }
    }
}
