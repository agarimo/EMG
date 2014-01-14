package main.entidades;

import java.util.Objects;

/**
 *
 * @author Agárimo
 */
public class Proveedor {
    private int id;
    private String nombre;
    private String web;
    
    public Proveedor(int id){
        this.id=id;
    }
    
    public Proveedor(String nombre){
        this.nombre=nombre;
    }
    
    public Proveedor(int id, String nombre, String web){
        this(id);
        this.nombre=nombre;
        this.web=web;
    }

    public int getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.nombre);
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
        final Proveedor other = (Proveedor) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    public String SQLCrear() {
        String query = "INSERT into electromegusta.proveedor (nombre_proveedor,web_proveedor) values("
                + util.Varios.entrecomillar(this.nombre) + ","
                + util.Varios.entrecomillar(this.web)
                + ")";
        return query;
    }

    public String SQLBorrar() {
        String query = "DELETE from electromegusta.proveedor WHERE id_proveedor=" + this.id;
        return query;
    }

    public String SQLEditar() {
        String query = "UPDATE electromegusta.proveedor SET "
                + "nombre_proveedor=" + util.Varios.entrecomillar(this.nombre) + ","
                + "web_proveedor=" + util.Varios.entrecomillar(this.web) + " "
                + "WHERE id_proveedor=" + this.id;
        return query;
    }

    public String SQLBuscar() {
        String query = "SELECT * FROM electromegusta.proveedor WHERE nombre_proveedor="+util.Varios.entrecomillar(this.nombre);
        return query;
    }

    public String SQLBuscarId() {
        String query = "SELECT * FROM electromegusta.proveedor WHERE id_proveedor="+this.id;
        return query;
    }
}
