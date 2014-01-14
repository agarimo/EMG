package main.entidades;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import util.Sql;
import main.SqlEmg;

/**
 *
 * @author Agárimo
 */
public final class PrestaShop {

    private ProductoFinal pf;
    private int stock;
    private double precio;
    private String descripcion;

    public PrestaShop(ProductoFinal pf) {
        this.pf = SqlEmg.cargaProductoFinal(pf, 0);
        cargaValores();
    }

    public PrestaShop(ProductoFinal pf, String descripcion) {
        this.pf = SqlEmg.cargaProductoFinal(pf, 0);
        this.descripcion = descripcion;
    }

    public PrestaShop(ProductoFinal pf, int stock, double precio) {
        this.pf = pf;
        this.stock = stock;
        this.precio = precio;
    }

    public ProductoFinal getId() {
        return pf;
    }

    public void setId(ProductoFinal pf) {
        this.pf = pf;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double calculaPrecioFinal() {
        double aux = this.precio;
        aux = ((aux * pf.getPorcentaje()) / 100) + aux;
        aux = aux + pf.getPorte();

        return aux;
    }

    public String updatePrice() {
        String query = "UPDATE admin_electropresta.EM_product_shop SET "
                + "price=" + calculaPrecioFinal() + " "
                + "WHERE id_product=" + this.pf.getId();
        return query;
    }

    public String updateStock() {
        String query = "UPDATE admin_electropresta.EM_stock_available SET "
                + "quantity=" + this.stock + " "
                + "WHERE id_product=" + this.pf.getId();
        return query;
    }
    
    public String updateActivo(){
        String query = "UPDATE admin_electropresta.EM_product_shop SET "
                + "active="+this.pf.isActivo() + " "
                + "where id_product="+this.pf.getId();
        return query;
    }

    public void cargaValores() {
        try {
            Sql bd = new Sql(Main.conEmg);
            this.precio = bd.getDouble("SELECT precio FROM electromegusta.precio_coste WHERE id_precio=" + this.pf.getPrecioCoste());
            this.stock = bd.buscar("SELECT stock FROM electromegusta.stock WHERE id_stock=" + this.pf.getStock());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(PrestaShop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Métdodo para cuándo haya que actualizar objetos individuales en la bbdd
//    public String updateDescripcion(){
//        String query="UPDATE admin_electropresta.EM_product_lang SET "
//                + "description="+Util.entrecomillar(this.descripcion)+" "
//                + "WHERE id_product="+this.pf.getId()+" AND id_lang=1";
//        return query;
//    }
}
