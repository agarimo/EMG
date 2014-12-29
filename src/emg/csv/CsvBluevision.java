package emg.csv;

import main.entidades.Producto;
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
        System.out.println("Iniciando actualizacion de stock y tarifa BLUEVISION");
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
        aux=split[3].trim();
        ip.setStock(Integer.parseInt(aux));
        aux=split[2].trim();
//        aux=aux.replace(".", "");
//        aux=aux.replace(",", ".");
        ip.setPrecio(Double.parseDouble(aux));
                
        creaProducto(producto,str);
    }
    
    private void creaProducto(Producto producto,String linea) throws SQLException{
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
