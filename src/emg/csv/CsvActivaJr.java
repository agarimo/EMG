package emg.csv;

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
public class CsvActivaJr extends Csv {

    public CsvActivaJr(File archivo, Conexion con) {
        super(archivo, con);
        super.cargar();
        logProducto= new Log("ACTIVA JR ");
    }

    @Override
    public void run() {
        super.conectar();
        procesar();
        super.desconectar();
    }

    private void procesar() {
        System.out.println("Iniciando actualizacion de stock y tarifa ACTIVA JR");
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
        String ref;
        Double precio;
        
        String[] split = str.split(";");
        
        ref=split[0];
        precio=Double.parseDouble(split[1]);
        
        creaProducto(ref,precio,str);
        
    }
    
    private void creaProducto(String ref,Double precio,String linea) throws SQLException{
        int id = bd.buscar("SELECT id_producto FROM electromegusta.producto_final WHERE ref_proveedor="+ref);

        if (id < 0) {
            logProducto.escribeMsg("Nueva Alta en Web: " + linea);
            bd.ejecutar("UPDATE electromegusta.producto SET publicado=1 WHERE id_proveedor=3 AND referencia_proveedor="+ref);
        } else {
            bd.ejecutar("UPDATE electromegusta.info_producto SET precio="+precio+" WHERE id_info=(SELECT id_producto FROM electromegusta.producto WHERE id_proveedor=3 AND referencia_proveedor="+util.Varios.entrecomillar(ref)+")");
        }
    }
}
