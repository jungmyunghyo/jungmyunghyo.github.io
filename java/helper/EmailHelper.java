import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailHelper {
	private static final class Constant {
		private static final String CHAR_SET = "UTF-8";
		
		private static final Map<Integer, String> CACHED_HEADER_TEMPLATES = new ConcurrentHashMap<>();
		private static final Map<Integer, String> CACHED_BODY_TEMPLATES = new ConcurrentHashMap<>();
		private static final Map<Integer, String> CACHED_NOTICE_TEMPLATES = new ConcurrentHashMap<>();
		private static final Map<Integer, String> CACHED_FOOTER_TEMPLATES = new ConcurrentHashMap<>();
		
		private static final String IMG_SRC_PREFIX = null;
		private static final String IMG_ALT = "alt=''";
		private static final String IMG_ONERROR = "onerror='this.style.display=\"none\"'";
	}
	private static final class Type {
		private enum Target {SMTP, JAVA, HTTP}
	}
	public static final class Vo {
		@Getter
		@Setter
		@NoArgsConstructor(access=AccessLevel.PUBLIC)
		@AllArgsConstructor(access=AccessLevel.PRIVATE)
		public static final class Email {
			private List<String> addressList;
			private List<String> ccList;
			private List<File> fileList;
			private int seq;
			private String subject;
			private String template;
			private Map<String, String> replacements;
			private Map<String, FormatHelper.Type.Format> formats;
			
			private List<String> getSafeCcList() {return this.ccList != null && !this.ccList.isEmpty() ? this.ccList : Collections.emptyList();}
			private List<File> getAttachList() {
				if (this.fileList != null && !this.fileList.isEmpty()) {
					int fileSize = this.fileList.size();
					List<File> attachList = new ArrayList<>(fileSize);
					for (int attachNumber=0; attachNumber<fileSize; attachNumber++) {
						File attach = this.fileList.get(attachNumber);
						if (attach != null && attach.exists() && attach.isFile()) {
							attachList.add(attach);
						}
					}
					return attachList;
				}
				return Collections.emptyList();
			}
			private String getContent() {
				LocalDateTime now = LocalDateTime.now();
				String ver = String.format("?ver=%d%d", now.getHour(), now.getMinute());
				String content = this.template;
				
				content = content.replace("${template-header}", getHeaderTemplate(this.seq));
				content = content.replace("${template-body}", getBodyTemplate(this.seq));
				content = content.replace("${template-notice}", getNoticeTemplate(this.seq));
				content = content.replace("${template-footer}", getFooterTemplate(this.seq));
				
				Map<String, String> params = this.replacements != null ? new HashMap<>(this.replacements) : new HashMap<>();
				Map<String, FormatHelper.Type.Format> types = this.formats != null ? new HashMap<>(this.formats) : new HashMap<>();
				
				params.put("${img-src}", Constant.IMG_SRC_PREFIX + null + ver);
				params.put("${img-alt}", Constant.IMG_ALT);
				params.put("${img-onerror}", Constant.IMG_ONERROR);
				
				params.put("${header-subject}", this.subject);
				
				params.put("${footer-copyright}", String.valueOf(now.getYear()));
				
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					
					content = content.replace(key, FormatHelper.Util.safeParse(types.getOrDefault(key, FormatHelper.Type.Format.STRING), value));
				}
				return content;
			}
			private static final String getHeaderTemplate(int seq) {
				return Constant.CACHED_HEADER_TEMPLATES.computeIfAbsent(seq, s -> {
					StringBuilder content = new StringBuilder();
					content.append("");
					return content.toString();
				});
			}
			private static final String getBodyTemplate(int seq) {
				return Constant.CACHED_BODY_TEMPLATES.computeIfAbsent(seq, s -> {
					StringBuilder content = new StringBuilder();
					content.append("");
					return content.toString();
				});
			}
			private static final String getNoticeTemplate(int seq) {
				return Constant.CACHED_NOTICE_TEMPLATES.computeIfAbsent(seq, s -> {
					StringBuilder content = new StringBuilder();
					content.append("");
					return content.toString();
				});
			}
			private static final String getFooterTemplate(int seq) {
				return Constant.CACHED_FOOTER_TEMPLATES.computeIfAbsent(seq, s -> {
					StringBuilder content = new StringBuilder();
					content.append("");
					return content.toString();
				});
			}
		}
	}
	public static final class Util {
		public static final void sendSmtp(Vo.Email email) {
			send(email, Type.Target.SMTP);
		}
		public static final void sendJava(Vo.Email email) {
			send(email, Type.Target.JAVA);
		}
		public static final void sendHttp(Vo.Email email) {
			send(email, Type.Target.HTTP);
		}
		private static final void send(Vo.Email email, Type.Target targetType) {
			try {
				switch (targetType) {
					case SMTP:
						Smtp.send(email);
						break;
					case JAVA:
						Java.send(email);
						break;
					case HTTP:
						Http.send(email);
						break;
					default:
						return;
				}
			} catch (Exception e) {
				log.error("Exception in call [send] data.", e);
			}
		}
		private static final String encode(String text) {
			try {
				return MimeUtility.encodeText(text, Constant.CHAR_SET, "B");
			} catch (UnsupportedEncodingException e) {
				log.error("UnsupportedEncodingException in call [send] data.", e);
				return text;
			}
		}
		private static final class Smtp {
			private static final boolean AUTH_REQUIRED = true;
			private static final String HOST = null;
			private static final Integer PORT = null;
			private static final String AUTH = String.valueOf(AUTH_REQUIRED);
			private static final String STARTTLS = "true";
			private static final String SSL_PROTOCOLS = "TLSv1.2";
			
			private static final String FROM_EMAIL = null;
			private static final String FROM_PASSWORD = null;
			
			private static final Properties PROPERTIES;
			private static final Session SESSION;
			private static final InternetAddress FROM_ADDRESS;
			
			static {
				PROPERTIES = getProperties();
				SESSION = getSession(PROPERTIES);
				try {
					FROM_ADDRESS = getInternetAddress(FROM_EMAIL);
				} catch (AddressException e) {
					throw new RuntimeException(e);
				}
			}
			
			private static final void send(Vo.Email email) throws MessagingException {
				List<File> attachList = email.getAttachList();
				
				Message message = new MimeMessage(SESSION);
				
				message.setFrom(FROM_ADDRESS);
				message.setRecipients(Message.RecipientType.TO, getInternetAddress(email.getAddressList()));
				message.setRecipients(Message.RecipientType.CC, getInternetAddress(email.getSafeCcList()));
				message.setSubject(Util.encode(email.getSubject()));
				
				MimeMultipart multiPart = new MimeMultipart();
				MimeBodyPart bodyPart = new MimeBodyPart();
				
				bodyPart.setContent(email.getContent(), "text/html; charset=" + Constant.CHAR_SET);
				
				multiPart.addBodyPart(bodyPart);
				
				for (File attach : attachList) {
					MimeBodyPart attachPart = new MimeBodyPart();
					
					attachPart.setDataHandler(new javax.activation.DataHandler(new FileDataSource(attach)));
					attachPart.setFileName(Util.encode(attach.getName()));
					
					multiPart.addBodyPart(attachPart);
				}
				message.setContent(multiPart);
				
				Transport.send(message);
			}
			private static final Properties getProperties() {
				Properties properties = new Properties();
				
				properties.put("mail.smtp.host", HOST);
				properties.put("mail.smtp.port", PORT);
				if (AUTH_REQUIRED) {
					properties.put("mail.smtp.auth", AUTH);
					properties.put("mail.smtp.starttls.enable", STARTTLS);
					properties.put("mail.smtp.ssl.protocols", SSL_PROTOCOLS);
				}
				return properties;
			}
			private static final Session getSession(Properties properties) {
				if (AUTH_REQUIRED) {
					return Session.getInstance(properties, new javax.mail.Authenticator() { 
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
						}
					});
				}
				return Session.getInstance(properties, new javax.mail.Authenticator() {});
			}
			private static final InternetAddress getInternetAddress(String address) throws AddressException {
				return new InternetAddress(address);
			}
			private static final InternetAddress[] getInternetAddress(List<String> addressList) throws AddressException {
				int addressSize = addressList.size();
				InternetAddress[] addressArray = new InternetAddress[addressSize];
				for (int addressNumber=0; addressNumber<addressSize; addressNumber++) {
					addressArray[addressNumber] = getInternetAddress(addressList.get(addressNumber));
				}
				return addressArray;
			}
		}
		private static final class Java {
			private static final boolean AUTH_REQUIRED = true;
			private static final String HOST = null;
			private static final Integer PORT = null;
			private static final String AUTH = String.valueOf(AUTH_REQUIRED);
			private static final String STARTTLS = "true";
			private static final String SSL_PROTOCOLS = "TLSv1.2";
			
			private static final String FROM_EMAIL = null;
			private static final String FROM_PASSWORD = null;
			
			private static final JavaMailSender JAVA_MAIL_SENDER;
			
			static {
				JAVA_MAIL_SENDER = getSender();
			}
			
			private static final void send(Vo.Email email) throws MessagingException {
				List<String> addressList = email.getAddressList();
				List<String> ccList = email.getSafeCcList();
				List<File> attachList = email.getAttachList();
				
				MimeMessage message = JAVA_MAIL_SENDER.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, !attachList.isEmpty(), Constant.CHAR_SET);
				
				helper.setFrom(new InternetAddress(FROM_EMAIL));
				helper.setTo(addressList.toArray(new String[addressList.size()]));
				helper.setCc(ccList.toArray(new String[ccList.size()]));
				helper.setSubject(Util.encode(email.getSubject()));
				helper.setText(email.getContent(), true);
				
				for (File attach : attachList) {
					helper.addAttachment(Util.encode(attach.getName()), new FileDataSource(attach));
				}
				JAVA_MAIL_SENDER.send(message);
			}
			private static final JavaMailSenderImpl getSender() {
				JavaMailSenderImpl sender = new JavaMailSenderImpl();
				
				sender.setHost(HOST);
				sender.setPort(PORT);
				if (AUTH_REQUIRED) {
					sender.setUsername(FROM_EMAIL);
					sender.setPassword(FROM_PASSWORD);
				}
				Properties properties = sender.getJavaMailProperties();
				
				properties.put("mail.transport.protocol", "smtp");
				if (AUTH_REQUIRED) {
					properties.put("mail.smtp.auth", AUTH);
					properties.put("mail.smtp.starttls.enable", STARTTLS);
					properties.put("mail.smtp.ssl.protocols", SSL_PROTOCOLS);
				}
				return sender;
			}
		}
		private static final class Http {
			private static final boolean TOKEN_REQUIRED = true;
			private static final boolean KEY_REQUIRED = true;
			private static final String URL = null;
			private static final String TOKEN = null;
			private static final String KEY = null;
			
			private static final int CONNECT_TIMEOUT_MS = 15_000;
			private static final int READ_TIMEOUT_MS = 25_000;
			
			private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
			
			private static final void send(Vo.Email email) throws IOException {
				HttpURLConnection conn = (HttpURLConnection) new URL(URL).openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json; charset=" + Constant.CHAR_SET);
				if (TOKEN_REQUIRED) {
					conn.setRequestProperty("Authorization", "Bearer" + " " + TOKEN);
				}
				if (KEY_REQUIRED) {
					conn.setRequestProperty("X-Secret-Key", KEY);
				}
				conn.setConnectTimeout(CONNECT_TIMEOUT_MS);
				conn.setReadTimeout(READ_TIMEOUT_MS);
				conn.setDoOutput(true);
				
				ArrayNode recipients = OBJECT_MAPPER.createArrayNode();
				for (String address : email.getAddressList()) {
					recipients.add(OBJECT_MAPPER.createObjectNode().put("to", address));
				}
				for (String address : email.getSafeCcList()) {
					recipients.add(OBJECT_MAPPER.createObjectNode().put("cc", address));
				}
				ObjectNode data = OBJECT_MAPPER.createObjectNode();
				
				data.set("recipients", recipients);
				data.put("subject", email.getSubject());
				data.put("content", email.getContent());
				
				try (OutputStream out = conn.getOutputStream()) {
					out.write(OBJECT_MAPPER.writeValueAsBytes(data));
					out.flush();
				} finally {
					conn.disconnect();
				}
			}
		}
	}
}