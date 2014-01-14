package main.entidades;

import java.util.Objects;


/**
 *
 * @author Agárimo
 */
public class SubCategoria2 {

    private int id;
    private int idSubCategoria;
    private String nombre;

    public SubCategoria2(int id) {
        this.id = id;
    }

    public SubCategoria2(int idSubCategoria, String nombre) {
        this.idSubCategoria = idSubCategoria;
        this.nombre = nombre;
    }

    public SubCategoria2(int id, int idSubCategoria, String nombre) {
        this.id = id;
        this.idSubCategoria = idSubCategoria;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public int getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.idSubCategoria;
        hash = 23 * hash + Objects.hashCode(this.nombre);
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
        final SubCategoria2 other = (SubCategoria2) obj;
        if (this.idSubCategoria != other.idSubCategoria) {
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
        String query = "INSERT into electromegusta.subcategoria2 (id_subcategoria,nombre_subcategoria2) values("
                + this.idSubCategoria + ","
                + util.Varios.entrecomillar(this.nombre)
                + ")";
        return query;
    }

    public String SQLBorrar() {
        String query = "DELETE from electromegusta.subcategoria2 WHERE id_subcategoria2=" + this.id;
        return query;
    }

    public String SQLEditar() {
        String query = "UPDATE electromegusta.subcategoria2 SET "
                + "id_subcategoria=" + this.idSubCategoria + ","
                + "nombre_subcategoria2=" + util.Varios.entrecomillar(this.nombre) + " "
                + "WHERE id_subcategoria2=" + this.id;
        return query;
    }

    public String SQLBuscar() {
        String query = "SELECT * FROM electromegusta.subcategoria2 WHERE nombre_subcategoria2=" + util.Varios.entrecomillar(this.nombre)+" "
                + "AND id_subcategoria="+this.idSubCategoria;
        return query;
    }

    public String SQLBuscarId() {
        String query = "SELECT * FROM electromegusta.subcategoria2 WHERE id_subcategoria2=" + this.id;
        return query;
    }
}
