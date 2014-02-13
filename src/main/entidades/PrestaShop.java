package main.entidades;

import main.SqlEmg;

/**
 *
 * @author Agárimo
 */
public final class PrestaShop {

    private ProductoFinal pf;
    private InfoProducto ip;
    private String descripcion;

    public PrestaShop(ProductoFinal pf) {
        this.pf = SqlEmg.cargaProductoFinal(pf, 0);
        getInfo();
    }

    public PrestaShop(ProductoFinal pf, String descripcion) {
        this.pf = SqlEmg.cargaProductoFinal(pf, 0);
        this.descripcion = descripcion;
    }

    public PrestaShop(ProductoFinal pf, InfoProducto ip) {
        this.pf = pf;
        this.ip = ip;
    }

    public ProductoFinal getId() {
        return pf;
    }

    public void setId(ProductoFinal pf) {
        this.pf = pf;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double calculaPrecioFinal() {
        double aux = ip.getPrecio();
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
                + "quantity=" + ip.getStock() + " "
                + "WHERE id_product=" + this.pf.getId();
        return query;
    }

    public String updateActivo() {
        String query = "UPDATE admin_electropresta.EM_product_shop SET "
                + "active=" + this.pf.isActivo() + " "
                + "where id_product=" + this.pf.getId();
        return query;
    }

    public void getInfo() {
        ip=SqlEmg.cargaInfoProducto(new InfoProducto(pf.getId()));
    }
    
    //Métdodo para cuándo haya que actualizar objetos individuales en la bbdd
//    public String updateDescripcion(){
//        String query="UPDATE admin_electropresta.EM_product_lang SET "
//                + "description="+Util.entrecomillar(this.descripcion)+" "
//                + "WHERE id_product="+this.pf.getId()+" AND id_lang=1";
//        return query;
//    }
}
