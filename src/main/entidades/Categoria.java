package main.entidades;

import java.util.Objects;


/**
 *
 * @author Agárimo
 */
public class Categoria {
    private int id;
    private String nombre;
    
    public Categoria(int id){
        this.id=id;
    }
    
    public Categoria(String nombre){
        this.nombre=nombre;
    }
    
    public Categoria(int id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre_categoria) {
        this.nombre = nombre_categoria;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.nombre);
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
        final Categoria other = (Categoria) obj;
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
        String query = "INSERT into electromegusta.categoria (nombre_categoria) values("
                + util.Varios.entrecomillar(this.nombre)
                + ")";
        return query;
    }

    public String SQLBorrar() {
        String query = "DELETE from electromegusta.categoria WHERE id_categoria=" + this.id;
        return query;
    }

    public String SQLEditar() {
        String query = "UPDATE electromegusta.categoria SET "
                + "nombre=" + util.Varios.entrecomillar(this.nombre) + " "
                + "WHERE id_categoria=" + this.id;
        return query;
    }

    public String SQLBuscar() {
        String query = "SELECT * FROM electromegusta.categoria WHERE nombre_categoria="+util.Varios.entrecomillar(this.nombre);
        return query;
    }

    public String SQLBuscarId() {
        String query = "SELECT * FROM electromegusta.categoria WHERE id_categoria="+this.id;
        return query;
    }
}
