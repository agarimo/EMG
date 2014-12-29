package main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.entidades.InfoProducto;
import main.entidades.Producto;
import util.Conexion;
import util.Sql;

/**
 *
 * @author Agarimo
 */
public class Main {

    public static Conexion conEmg;
    public static Conexion conPresta;
    public static boolean isLinux;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        driverAndInit();
        rutinaInsercion();
        
        args = new String[]{"-bluevision"};
        if (args.length != 0) {
            inicio(args);
        } else {
//            guiControl();
        }
    }

    private static void inicio(String[] aux) throws IOException {
        Inicio init = new Inicio(aux);
        init.run();
    }

    private static void driverAndInit() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            keyStore();
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        conEmg = new Conexion("nombre", "electromegusta.es", "3306", "agarimo", "IkuinenK@@m.s84");
        conPresta = new Conexion("nombre", "electromegusta.es", "3306", "appLogin", "IkuinenK@@m.s84");
    }

    private static void keyStore() throws IOException {
        checkOs();

        if (!isLinux) {
            System.setProperty("javax.net.ssl.keyStore", "emgkeystore");
            System.setProperty("javax.net.ssl.trustStore", "emgkeystore");
        } else {
            File aux = new File(".");
            System.setProperty("javax.net.ssl.keyStore", aux.getCanonicalPath() + "/appEMG/emgkeystore");
            System.setProperty("javax.net.ssl.trustStore", aux.getCanonicalPath() + "/appEMG/emgkeystore");
        }
        System.setProperty("javax.net.ssl.keyStorePassword", "sanchez84");
        System.setProperty("javax.net.ssl.keyStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStorePassword", "sanchez84");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
    }

    private static void checkOs() {
        String so = System.getProperty("os.name");
        if (so.contains("Linux")) {
            isLinux = true;
        }
    }

    private static void rutinaInsercion() {
        Producto pd;
        InfoProducto ip;
        List ls = SqlEmg.listaProducto("Select * from electromegusta.producto WHERE id_producto NOT IN (SELECT id_info FROM electromegusta.info_producto)");
        Iterator it = ls.iterator();

        try {
            Sql bd = new Sql(Main.conEmg);

            while (it.hasNext()) {
                pd = (Producto) it.next();

                ip = new InfoProducto(pd.getId());

                if (bd.buscar(ip.SQLBuscar()) < 1) {
                    bd.ejecutar(ip.SQLCrear());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
