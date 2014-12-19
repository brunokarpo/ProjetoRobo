package br.nom.brunokarpo.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class SendEmail {

	public static void main(String[] args) {
		enviarEmail();
	}

	public static void enviarEmail() {
		final Properties prop = System.getProperties();

		try {
			prop.load(new FileInputStream(new File("src/main/resources/configuracoes-email.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Session session = Session.getDefaultInstance(prop,
		new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(prop.getProperty("mail.user"), prop.getProperty("mail.passwd"));
			}
		});

		session.setDebug(true);

		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(prop.getProperty("mail.from")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(prop.getProperty("mail.to")));
			message.setSubject("[Rob\u00f4 Portal Nacional] Fim de Processamento das Chaves de Acesso");//Assunto

			BodyPart body = new MimeBodyPart();

			VelocityEngine ve = new VelocityEngine();
			ve.init();

			Template t = ve.getTemplate("src/main/resources/email.vm");
			VelocityContext context = new VelocityContext();
			context.put("nomeHost", InetAddress.getLocalHost().getHostName());
			context.put("ipHost", InetAddress.getLocalHost().getHostAddress());

			StringWriter out = new StringWriter();
			t.merge(context, out);

			body.setContent(out.toString(), "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(body);

			body = new MimeBodyPart();
			message.setContent(multipart, "text/html");

			Transport.send(message);

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}