package drop.entidades;

import java.util.Objects;

/**
 *
 * @author Agárimo
 */
public class Pedido {

    private int id;
    private String codigo;
    private int id_cliente;
    private int id_direccion;
    private int estado;

    public Pedido() {
    }

    public Pedido(int id, String codigo, int id_cliente, int id_direccion, int estado) {
        this.id = id;
        this.codigo = codigo;
        this.id_cliente = id_cliente;
        this.id_direccion = id_direccion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(int id_direccion) {
        this.id_direccion = id_direccion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.codigo);
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
        final Pedido other = (Pedido) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    public String SQLCrear() {
        String query = "INSERT into electromegusta.pedido (codigo_pedido,id_cliente,id_direccion,estado) values("
                + util.Varios.entrecomillar(this.codigo) + ","
                + this.id_cliente + ","
                + this.id_direccion + ","
                + this.estado
                + ")";
        return query;
    }

    public String SQLBorrar() {
        String query = "DELETE from electromegusta.pedido WHERE id_pedido=" + this.id;
        return query;
    }

    public String SQLBuscarCodigo() {
        String query = "SELECT * FROM electromegusta.pedido WHERE codigo_pedido=" + util.Varios.entrecomillar(this.codigo);
        return query;
    }

    public String SQLBuscarCliente() {
        String query = "SELECT * FROM electromegusta.pedido WHERE id_cliente=" + this.id_cliente;
        return query;
    }
}
