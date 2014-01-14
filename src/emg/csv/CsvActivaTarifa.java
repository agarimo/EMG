package emg.csv;

import main.entidades.ProductoFinal;
import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import main.Inicio;
import util.Conexion;
import main.SqlEmg;
import util.Dates;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class CsvActivaTarifa extends Csv {

    public CsvActivaTarifa(File archivo, Conexion con) {
        super(archivo, con);
        super.cargar();
    }

    @Override
    public void run() {
        super.conectar();
        cargaListPublicado();
        procesar();
        super.desconectar();
    }

    private void procesar() {
        System.out.println("Iniciando actualizacion de tarifa");
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

    @Override
    protected void cargaListPublicado() {
        this.listPublicado = SqlEmg.listaProductoFinal("SELECT * FROM electromegusta.producto_final WHERE id_proveedor=3");
    }
    
    private void split(String str) throws SQLException {
        ProductoFinal pf;
        String[] split = str.split(";");

        if (!"Articulos destacados".equals(split[0].trim())) {
            pf = new ProductoFinal();
            pf.setIdProveedor(3);
            pf.setReferenciaProveedor(split[3].trim());
            pf.setReferenciaFabricante(split[4].trim());
            pf.setNombre(split[7].trim().replace("'", "´"));
            this.precio = Double.parseDouble(split[8].trim());
            this.stock = Integer.parseInt(split[9].trim());

            compruebaProducto(pf);
        }
    }

    private void compruebaProducto(ProductoFinal pf) throws SQLException {
        ProductoFinal aux=buscarEnList(pf);
        if(aux!=null){
            actualizaTarifa(aux.getId());
            actualizaTarifaPresta(aux);
        }
    }

    private void actualizaTarifa(int id) throws SQLException {
        String query = "UPDATE electromegusta.precio_coste SET "
                + "precio=" + this.precio + ","
                + "last_update=" + Varios.entrecomillar(Dates.imprimeFechaCompleta(Dates.curdate())) + " "
                + "WHERE id_precio="+id;
        bd.ejecutar(query);
    }
}
