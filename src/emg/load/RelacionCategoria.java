package emg.load;

/**
 *
 * @author Agárimo
 */
public class RelacionCategoria {

    private int id;
    private int categoria;
    private String nombreCategoria;
    private int categoriaFinal;
    private String nombreCategoriaFinal;

    public RelacionCategoria(int categoria, int categoriaFinal) {
        this.categoria = categoria;
        this.categoriaFinal = categoriaFinal;
    }

    public RelacionCategoria(int id, int categoria, int categoriaFinal) {
        this.id = id;
        this.categoria = categoria;
        this.categoriaFinal = categoriaFinal;
    }

    public RelacionCategoria(int categoria, String nombreCategoria, int categoriaFinal, String nombreCategoriaFinal) {
        this.categoria = categoria;
        this.nombreCategoria = nombreCategoria;
        this.categoriaFinal = categoriaFinal;
        this.nombreCategoriaFinal = nombreCategoriaFinal;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNombreCategoriaFinal() {
        return nombreCategoriaFinal;
    }

    public void setNombreCategoriaFinal(String nombreCategoriaFinal) {
        this.nombreCategoriaFinal = nombreCategoriaFinal;
    }

    public int getId() {
        return id;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getCategoriaFinal() {
        return categoriaFinal;
    }

    public void setCategoriaFinal(int categoriaFinal) {
        this.categoriaFinal = categoriaFinal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.categoria;
        hash = 37 * hash + this.categoriaFinal;
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
        final RelacionCategoria other = (RelacionCategoria) obj;
        if (this.categoria != other.categoria) {
            return false;
        }
        if (this.categoriaFinal != other.categoriaFinal) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombreCategoria + "-" + this.nombreCategoriaFinal;
    }

    public String SQLCrear() {
        String query = "INSERT into electromegusta.relacion_categorias (id_categoria,id_categoria_final) values("
                + this.categoria + ","
                + this.categoriaFinal
                + ")";
        return query;
    }

    public String SQLBorrar() {
        String query = "DELETE from electromegusta.relacion_categorias WHERE id_relacion=" + this.id;
        return query;
    }

    public String SQLEditar() {
        String query = "UPDATE electromegusta.relacion_categorias SET "
                + "id_categoria=" + this.categoria + ","
                + "id_categoria_final=" + this.categoriaFinal + " "
                + "WHERE id_relacion=" + this.id;
        return query;
    }

    public String SQLBuscar() {
        String query = "SELECT * FROM electromegusta.relaciones WHERE id_categoria=" + this.categoria + " AND "
                + "id_categoria_final=" + this.categoriaFinal;
        return query;
    }

    public String SQLBuscarCategoria() {
        String query = "SELECT * FROM electromegusta.relaciones WHERE id_categoria=" + this.categoria;
        return query;
    }

    public String SQLBuscarCategoriaFinal() {
        String query = "SELECT * FROM electromegusta.relaciones WHERE id_categoria_final=" + this.categoriaFinal;
        return query;
    }
}
