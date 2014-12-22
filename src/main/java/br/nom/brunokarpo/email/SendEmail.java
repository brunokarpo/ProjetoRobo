package br.nom.brunokarpo.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.URL;
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

import br.com.oobj.util.Arquivo;
import br.com.oobj.util.config.Config;
import br.com.oobj.util.config.ConfigPool;

public class SendEmail {

	public static void main(String[] args) {
		Config config = ConfigPool.getConfig("configuracores-email.properties");
		enviarEmail("arquivo-1.txt", config);
	}

	public static void enviarEmail(String nomeArquivoConcluido, Config config) {
		final Properties prop = System.getProperties();

		try {
			URL urlProperties = Arquivo.getURLRelativaClasspath("/configuracoes-email.properties");
			prop.load(new FileInputStream(new File( urlProperties.toString().replace("file:/", "") )));
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
			ve.init(ClassLoader.getSystemResource("configuracoes-email.properties").getFile());

			Template t = ve.getTemplate("email.vm");
			VelocityContext context = new VelocityContext();
			context.put("nomeHost", InetAddress.getLocalHost().getHostName());
			context.put("ipHost", InetAddress.getLocalHost().getHostAddress());
			context.put("nomeArquivo", nomeArquivoConcluido);
			context.put("certificado", config.getProperty("path-certificado"));

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