


import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSenderService {
private final Properties properties = new Properties();
	
	private String password;

	private Session session;

	private void init() {

		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port",25);
		properties.put("mail.smtp.mail.sender","chavez20066@gmail.com");
		properties.put("mail.smtp.user", "chavez20066@gmail.com"); //quien envia
		properties.put("mail.smtp.auth", "true");
		
		/*Properties props = new Properties();

		// Nombre del host de correo, es smtp.gmail.com
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		
		// TLS si está disponible
		props.setProperty("mail.smtp.starttls.enable", "true");
		
		// Puerto de gmail para envio de correos
		props.setProperty("mail.smtp.port","587");
		
		// Nombre del usuario
		props.setProperty("mail.smtp.user", "ejemplo@gmail.com");
		
		// Si requiere o no usuario y password para conectarse.
		props.setProperty("mail.smtp.auth", "true");
		 * 
		 * */

		session = Session.getDefaultInstance(properties);
		session.setDebug(true);
	}

	public void sendEmail(){

		init();
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("dichavez@ucsm.edu.pe"));
			message.setSubject("Prueba");
			message.setText("Hola mundo esta es una prueba de mensaje");			
			Transport t = session.getTransport("smtp");
			t.connect((String)properties.get("mail.smtp.user"), "9204Intel");
			t.sendMessage(message, message.getAllRecipients());
			t.close();
		}catch (MessagingException me){
                        //Aqui se deberia o mostrar un mensaje de error o en lugar
                        //de no hacer nada con la excepcion, lanzarla para que el modulo
                        //superior la capture y avise al usuario con un popup, por ejemplo.
			return;
		}
		
	}
	public void sendEmailAdjunto() {
		init();
		
		try {
			BodyPart texto = new MimeBodyPart();
			texto.setText("Texto del mensaje");
			
			BodyPart adjunto = new MimeBodyPart();
			adjunto.setDataHandler(new DataHandler(new FileDataSource("///home/d1360/Descargas/Trade Assurance Order Details.pdf")));
			adjunto.setFileName("Trade Assurance Order Details.pdf");
			
			MimeMultipart multiParte = new MimeMultipart();

			multiParte.addBodyPart(texto);
			multiParte.addBodyPart(adjunto);
			
			MimeMessage message = new MimeMessage(session);
			// Se rellena el From
			message.setFrom(new InternetAddress("chavez20066@gmail.com"));

			// Se rellenan los destinatarios
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("chavez20066@hotmail.com"));

			// Se rellena el subject
			message.setSubject("envio pdf desde Java");

			// Se mete el texto y la foto adjunta.
			message.setContent(multiParte);
			
			Transport t = session.getTransport("smtp");
			t.connect("chavez20066@gmail.com","9204Intel");
			t.sendMessage(message,message.getAllRecipients());
			t.close();
			
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
