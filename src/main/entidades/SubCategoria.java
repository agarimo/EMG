package main.entidades;

import java.util.Objects;


/**
 *
 * @author Agárimo
 */
public class SubCategoria {

    private int id;
    private int idCategoria;
    private String nombre;

    public SubCategoria(int id) {
        this.id = id;
    }

    public SubCategoria(int idCategoria, String nombre) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
    }

    public SubCategoria(int id, int idCategoria, String nombre) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.idCategoria;
        hash = 29 * hash + Objects.hashCode(this.nombre);
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
        final SubCategoria other = (SubCategoria) obj;
        if (this.idCategoria != other.idCategoria) {
            return false;
        }
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
        String query = "INSERT into electromegusta.subcategoria (id_categoria,nombre_subcategoria) values("
                + this.idCategoria + ","
                + util.Varios.entrecomillar(this.nombre)
                + ")";
        return query;
    }

    public String SQLBorrar() {
        String query = "DELETE from electromegusta.subcategoria WHERE id_subcategoria=" + this.id;
        return query;
    }

    public String SQLEditar() {
        String query = "UPDATE electromegusta.subcategoria SET "
                + "id_categoria=" + this.idCategoria + ","
                + "nombre_subcategoria=" + util.Varios.entrecomillar(this.nombre) + " "
                + "WHERE id_subcategoria=" + this.id;
        return query;
    }

    public String SQLBuscar() {
        String query = "SELECT * FROM electromegusta.subcategoria WHERE nombre_subcategoria=" + util.Varios.entrecomillar(this.nombre)+ " "
                + "AND id_categoria="+this.idCategoria;
        return query;
    }

    public String SQLBuscarId() {
        String query = "SELECT * FROM electromegusta.subcategoria WHERE id_subcategoria=" + this.id;
        return query;
    }
}
