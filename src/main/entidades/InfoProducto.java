package main.entidades;

import util.Dates;
import util.Varios;

/**
 *
 * @author Agárimo
 */
public class InfoProducto {

    int idInfo;
    int stock;
    double precio;
    
    public InfoProducto(){
    }
    
    public InfoProducto(int id){
        this.idInfo=id;
    }

    public InfoProducto(int id, int stock, double precio) {
        this.idInfo = id;
        this.stock = stock;
        this.precio = precio;
    }

    public int getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(int idInfo) {
        this.idInfo = idInfo;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.idInfo;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InfoProducto other = (InfoProducto) obj;
        if (this.idInfo != other.idInfo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InfoProducto{" + "idInfo=" + idInfo + ", stock=" + stock + ", precio=" + precio + '}';
    }

    public String SQLCrear() {
        String query = "INSERT into electromegusta.info_prodcuto (id_info,stock,precio,update_precio,update_stock) values("
                + this.idInfo + ","
                + this.stock + ","
                + this.precio + ","
                + "curdate(),"
                + "curdate()"
                + ")";
        return query;
    }

    public String SQLEditar() {
        String query = "UPDATE electromegusta.info_producto SET "
                + "precio=" + this.precio + ","
                + "stock=" + this.stock + ","
                + "update_precio=" + Varios.entrecomillar(Dates.imprimeFechaCompleta(Dates.curdate())) + ","
                + "update_stock=" + Varios.entrecomillar(Dates.imprimeFechaCompleta(Dates.curdate())) + " "
                + "WHERE id_info=" + this.idInfo;
        return query;
    }

    public String SQLBorrar() {
        String query = "DELETE FROM electromegusta.info_producto WHERE id_info=" + this.idInfo;
        return query;
    }

    public String SQLBuscar() {
        String query = "SELECT * FROM electromegusta.info_producto WHERE id_info=" + this.idInfo;
        return query;
    }
}
