package main.entidades;



/**
 *
 * @author Agárimo
 */
public class Imagen {

    private int id;
    private int id_producto;
    private String nombre;
    
    public Imagen(int id){
        this.id=id;
    }
    
    public Imagen(int id_producto,String nombre){
        this.id_producto=id_producto;
        this.nombre=nombre;
    }
    
    public Imagen(int id, int id_producto, String nombre){
        this.id=id;
        this.id_producto=id_producto;
        this.nombre=nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEnlace(){
        return "http://electromegusta.es/img/publicacion/"+this.nombre;
    }
    
    public String SQLCrear() {
        String query = "INSERT into electromegusta.imagen (id_producto,nombre_imagen) values("
                + this.id_producto + ","
                + util.Varios.entrecomillar(this.nombre)
                + ")";
        return query;
    }
    
    public String SQLBuscar(){
        String query="SELECT * from electromegusta.imagen where nombre_imagen="+util.Varios.entrecomillar(this.nombre);
        
        return query;
    }
}
