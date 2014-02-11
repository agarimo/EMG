package emg.csv;

import main.entidades.Producto;
import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import main.Inicio;
import main.Log;
import main.entidades.ProductoFinal;
import util.Conexion;
import util.Dates;
import util.Varios;

/**
 *
 * @author Agárimo
 */
public class CsvBluevision extends Csv {

    public CsvBluevision(File archivo, Conexion con) {
        super(archivo, con);
        super.cargar();
        logProducto= new Log("BLUEVISION ");
    }

    @Override
    public void run() {
        super.conectar();
        procesar();
        super.desconectar();
    }

    private void procesar() {
        System.out.println("Iniciando actualizacion de stock y tarifa");
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
        String aux;
        String[] split = str.split(";");

        Producto producto = new Producto();
        producto.setIdProveedor(4);
        producto.setIdCategoria(14);
        producto.setIdSubCategoria(65);
        producto.setIdSubCategoria2(151);
        producto.setReferenciaProveedor(split[0].trim());
        producto.setReferenciaFabricante(split[0].trim());
        producto.setNombre(split[1].trim());
        aux=split[2].trim();
        this.stock=Integer.parseInt(aux);
        aux=split[3].trim();
        aux=aux.replace(".", "");
        aux=aux.replace(",", ".");
        this.precio=Double.parseDouble(aux);
                
        creaProducto(producto,str);
    }
    
    private void creaProducto(Producto producto,String linea) throws SQLException{
        int id = bd.buscar(producto.SQLBuscarProveedor());

        if (id < 0) {
            logProducto.escribeMsg("Nuevo producto: " + linea);
            producto.setPrecioCoste(setPrecio(id));
            producto.setStock(setStock(id));
            bd.ejecutar(producto.SQLCrear());
        } else {
            actualizaTarifa(id);
            actualizaStock(id);
        }
    }
    
    private void actualizaTarifa(int id) throws SQLException {
        String query = "UPDATE electromegusta.precio_coste SET "
                + "precio=" + this.precio + ","
                + "last_update=" + Varios.entrecomillar(Dates.imprimeFechaCompleta(Dates.curdate()))+" "
                + "WHERE id_precio=" + id;
        bd.ejecutar(query);
    }
    
    private void actualizaStock(int id) throws SQLException{
        String query = "UPDATE electromegusta.stock SET "
                + "stock=" + this.stock + ","
                + "last_update=" + Varios.entrecomillar(Dates.imprimeFechaCompleta(Dates.curdate()))+" "
                + "WHERE id_stock=" + id;
        bd.ejecutar(query);
    }
}
