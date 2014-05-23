package drop.entidades;

/**
 *
 * @author Agárimo
 */
public class PedidoDetalle {

    private int id;
    private int pedido;
    private int producto;
    private int cantidad;
    
    public PedidoDetalle(int id, int pedido, int producto,int cantidad){
        this.id=id;
        this.pedido=pedido;
        this.producto=producto;
        this.cantidad=cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "PedidoDetalle{" + "id=" + id + ", pedido=" + pedido + ", producto=" + producto + '}';
    }
    
    public String SQLCrear() {
        String query = "INSERT into electromegusta.pedido_detalle (id_detalle,id_pedido,id_producto,cantidad) values("
                + this.id + ","
                + this.pedido + ","
                + this.producto + ","
                + this.cantidad
                + ")";
        return query;
    }

    public String SQLBorrar() {
        String query = "DELETE from electromegusta.pedido_detalle WHERE id_pedido=" + this.id;
        return query;
    }
    
    public String SQLBuscar(){
        String query = "SELECT * FROM electromegusta.pedido_detalle WHERE id_detalle=" + this.id;
        return query;
    }

    public String SQLBuscarPedido() {
        String query = "SELECT * FROM electromegusta.pedido_detalle WHERE codigo_pedido=" + this.pedido;
        return query;
    }

    public String SQLBuscarProducto() {
        String query = "SELECT * FROM electromegusta.pedido_detalle WHERE id_producto=" + this.producto;
        return query;
    }
    
}
