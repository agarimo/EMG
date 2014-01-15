package drop;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Agárimo
 */
public class Mail {

    private String usuario;
    private String pass;
    private String from;
    private String to;
    private String subjet;
    private String msg;

    public Mail() {
        this.usuario = "notify@electromegusta.es";
        this.pass = "IkuinenK@@m.s84";
        this.from = "notify@electromegusta.es";
        this.to = "b2b@electromegusta.es";
        this.subjet = "NUEVO PEDIDO!";
        this.msg = "Existe un nuevo pedido en la tienda"
                + "\nPara entrar en el panel pulse el link"
                + "\n http://electromegusta.es/admin5570/";
    }

    public Mail(String from, String to, String subjet, String msg) {
        this.usuario = "notify@electromegusta.es";
        this.pass = "IkuinenK@@m.s84";
        this.from = from;
        this.to = to;
        this.subjet = subjet;
        this.msg = msg;
    }
    
    public Mail(String usuario,String pass,String from, String to, String subjet, String msg) {
        this.usuario = usuario;
        this.pass = pass;
        this.from = from;
        this.to = to;
        this.subjet = subjet;
        this.msg = msg;
    }

    public boolean run() {
        try {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "mail.electromegusta.es");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "25");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            //Obtenemos los destinatarios
            String destinos[] = this.to.split(",");

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(this.from));

            Address[] receptores = new Address[destinos.length];
            int j = 0;
            while (j < destinos.length) {
                receptores[j] = new InternetAddress(destinos[j]);
                j++;
            }

            //receptores.
            message.addRecipients(Message.RecipientType.TO, receptores);
            message.setSubject(this.subjet);
            message.setText(this.msg);

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect(this.usuario, this.pass);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));

            // Cierre de la conexion.
            t.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
