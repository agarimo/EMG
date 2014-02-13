package main.entidades;

import java.util.Objects;


/**
 *
 * @author Agárimo
 */
public class ProductoFinal {

    private int id;
    private int idProveedor;
    private int idCategoria;
    private String nombre;
    private String referenciaFabricante;
    private String referenciaProveedor;
    private int porcentaje;
    private double porte;
    private boolean isActivo;
    
    public ProductoFinal(){
        
    }
    
    public ProductoFinal(int id){
        this.id=id;
    }

    public ProductoFinal(int idProveedor, int idCategoria, String nombre, String referenciaFabricante, String referenciaProveedor,
            int porcentaje,double porte,boolean isActivo) {
        this.idProveedor = idProveedor;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.referenciaFabricante = referenciaFabricante;
        this.referenciaProveedor = referenciaProveedor;
        this.porcentaje = porcentaje;
        this.porte=porte;
        this.isActivo=isActivo;
    }

    public ProductoFinal(int id, int idProveedor, int idCategoria, String nombre, String referenciaFabricante, String referenciaProveedor,
           int porcentaje,double porte,boolean isActivo) {
        this.id = id;
        this.idProveedor = idProveedor;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.referenciaFabricante = referenciaFabricante;
        this.referenciaProveedor = referenciaProveedor;
        this.porcentaje = porcentaje;
        this.porte=porte;
        this.isActivo=isActivo;
    }

    public double getPorte() {
        return porte;
    }

    public void setPorte(double porte) {
        this.porte = porte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReferenciaFabricante() {
        return referenciaFabricante;
    }

    public void setReferenciaFabricante(String referenciaFabricante) {
        this.referenciaFabricante = referenciaFabricante;
    }

    public String getReferenciaProveedor() {
        return referenciaProveedor;
    }

    public void setReferenciaProveedor(String referenciaProveedor) {
        this.referenciaProveedor = referenciaProveedor;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public boolean isActivo() {
        return isActivo;
    }

    public void setActivo(boolean isActivo) {
        this.isActivo = isActivo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.idProveedor;
        hash = 71 * hash + Objects.hashCode(this.referenciaProveedor);
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
        final ProductoFinal other = (ProductoFinal) obj;
        if (this.idProveedor != other.idProveedor) {
            return false;
        }
        if (!Objects.equals(this.referenciaProveedor, other.referenciaProveedor)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Producto_final{" + "id=" + id + ", idProveedor=" + idProveedor + ", idCategoria=" + idCategoria + ", nombre=" + nombre + ", referenciaFabricante=" + referenciaFabricante + ", referenciaProveedor=" + referenciaProveedor +  ", porcentaje=" + porcentaje + '}';
    }

    public String SQLCrear() {
        String query = "INSERT into electromegusta.producto_final (id_producto,id_proveedor,id_categoria,"
                + "nombre_producto,ref_fabricante,ref_proveedor,porcentaje_venta,porte,activo) values("
                + this.id + ","
                + this.idProveedor + ","
                + this.idCategoria + ","
                + util.Varios.entrecomillar(this.nombre) + ","
                + util.Varios.entrecomillar(this.referenciaFabricante) + ","
                + util.Varios.entrecomillar(this.referenciaProveedor) + ","
                + this.porcentaje + ","
                + this.porte + ","
                + this.isActivo
                + ")";
        return query;
    }
    
    public String SQLCreaPendiente(){
        String query="INSERT into electromegusta.pendiente_publicacion (id_producto_final) values("+this.id+")";
        return query;
    }
    
    public String SQLBorrar() {
        String query = "DELETE from electromegusta.producto_final WHERE id_producto=" + this.id;
        return query;
    }
    
    public String SQLBuscarProveedor() {
        String query = "SELECT * FROM electromegusta.producto_final WHERE ref_proveedor=" + util.Varios.entrecomillar(this.referenciaProveedor)+" "
                + "AND id_proveedor="+this.idProveedor;
        return query;
    }
    
      public String SQLBuscarFabricante() {
        String query = "SELECT * FROM electromegusta.producto_final WHERE ref_fabricante=" + util.Varios.entrecomillar(this.referenciaFabricante);
        return query;
    }
    
      public String SQLBuscarId() {
        String query = "SELECT * FROM electromegusta.producto_final WHERE id_producto=" + this.id;
        return query;
    }
    

    
    
}
