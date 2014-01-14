package main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Conexion;

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
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        driverAndInit();

//        args = new String[]{"-drop"};
        if (args.length != 0) {
            inicio(args);
        }
    }

    private static void inicio(String[] aux) throws IOException {
        Inicio init = new Inicio(aux);
        init.run();
    }

    private static void driverAndInit() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            checkOs();
            keyStore();
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al cargar el driver");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        conEmg = new Conexion("nombre", "localhost", "3306", "agarimo", "IkuinenK@@m.s84");
        conPresta = new Conexion("nombre", "localhost", "3306", "appLogin", "IkuinenK@@m.s84");
    }

    private static void checkOs() {
        String so = System.getProperty("os.name");
        if (so.contains("Linux")) {
            isLinux = true;
        }
    }

    private static void keyStore() throws IOException {
        String so = System.getProperty("os.name");

        if (so.contains("Windows")) {
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
}
