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
    }

    public Mail(String from, String to, String subjet, String msg) {
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

            //Recoger los datos
            String str_from = "notify@electromegusta.es";
            String str_De = "notify@electromegusta.es";
            String str_PwRemitente = "IkuinenK@@m.s84";
            String str_Para = "b2b@electromegusta.es";
            String str_Asunto = "NUEVO PEDIDO!";
            String str_Mensaje = "Existe un nuevo pedido en la tienda"
                    + "\nPara entrar en el panel pulse el link\n http://electromegusta.es/admin5570/";
            //Obtenemos los destinatarios
            String destinos[] = str_Para.split(",");

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(str_from));

            Address[] receptores = new Address[destinos.length];
            int j = 0;
            while (j < destinos.length) {
                receptores[j] = new InternetAddress(destinos[j]);
                j++;
            }

            //receptores.
            message.addRecipients(Message.RecipientType.TO, receptores);
            message.setSubject(str_Asunto);
            message.setText(str_Mensaje);

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect(str_De, str_PwRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));

            // Cierre de la conexion.
            t.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
