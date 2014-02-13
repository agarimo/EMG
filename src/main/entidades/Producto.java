package main.entidades;

import java.util.Objects;


/**
 *
 * @author Agárimo
 */
public class Producto {

    int id;
    int idProveedor;
    int idCategoria;
    int idSubCategoria;
    int idSubCategoria2;
    String referenciaProveedor;
    String referenciaFabricante;
    String nombre;

    public Producto() {
    }

    public Producto(int id) {
        this.id = id;
    }

    public Producto(int id, int idProveedor, int idCategoria, int idSubCategoria, int idSubCategoria2, String referenciaProveedor,
            String referenciaFabricante,String nombre) {
        this.id = id;
        this.idProveedor = idProveedor;
        this.idCategoria = idCategoria;
        this.idSubCategoria = idSubCategoria;
        this.idSubCategoria2 = idSubCategoria2;
        this.referenciaProveedor = referenciaProveedor;
        this.referenciaFabricante = referenciaFabricante;
        this.nombre = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public int getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public int getIdSubCategoria2() {
        return idSubCategoria2;
    }

    public void setIdSubCategoria2(int idSubCategoria2) {
        this.idSubCategoria2 = idSubCategoria2;
    }

    public String getReferenciaProveedor() {
        return referenciaProveedor;
    }

    public void setReferenciaProveedor(String referenciaProveedor) {
        this.referenciaProveedor = referenciaProveedor;
    }

    public String getReferenciaFabricante() {
        return referenciaFabricante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setReferenciaFabricante(String referenciaFabricante) {
        this.referenciaFabricante = referenciaFabricante;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.idProveedor;
        hash = 19 * hash + Objects.hashCode(this.referenciaFabricante);
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
        final Producto other = (Producto) obj;
        if (this.idProveedor != other.idProveedor) {
            return false;
        }
        if (!Objects.equals(this.referenciaFabricante, other.referenciaFabricante)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.referenciaFabricante;
    }

    public String SQLCrear() {
        String query = "INSERT into electromegusta.producto (id_proveedor,id_categoria,id_subcategoria,id_subcategoria2,"
                + "referencia_proveedor,referencia_fabricante,nombre_producto,fecha_entrada) values("
                + this.idProveedor + ","
                + this.idCategoria + ","
                + this.idSubCategoria + ","
                + this.idSubCategoria2 + ","
                + util.Varios.entrecomillar(this.referenciaProveedor) + ","
                + util.Varios.entrecomillar(this.referenciaFabricante) + ","
                + util.Varios.entrecomillar(this.nombre) + ","
                + "Curdate()"
                + ")";
        return query;

    }
    
    public String SQLBorrar() {
        String query = "DELETE from electromegusta.producto WHERE id_producto=" + this.id;
        return query;
    }

    public String SQLBuscarFabricante() {
        String query = "SELECT * FROM electromegusta.producto WHERE referencia_fabricante=" + util.Varios.entrecomillar(this.referenciaFabricante);
        return query;
    }

    public String SQLBuscarProveedor() {
        String query = "SELECT * FROM electromegusta.producto WHERE referencia_proveedor=" + util.Varios.entrecomillar(this.referenciaProveedor) + " "
                + "AND id_proveedor=" + this.idProveedor;
        return query;
    }

    public String SQLBuscarId() {
        String query = "SELECT * FROM electromegusta.producto WHERE id_producto=" + this.id;
        return query;
    }
}
