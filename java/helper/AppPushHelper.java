import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.ApsAlert;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppPushHelper {
	private static final class Type {
		private enum Target {FCM}
	}
	public static final class Vo {
		@Getter
		@Setter
		@NoArgsConstructor(access=AccessLevel.PUBLIC)
		@AllArgsConstructor(access=AccessLevel.PRIVATE)
		public static final class AppPush {
			private List<String> tokenList;
			private int seq;
			private String subject;
			private String template;
			private String clickAction;
			private Map<String, String> replacements;
			private Map<String, FormatHelper.Type.Format> formats;
			private Boolean singleSendRequired;
			
			private boolean getSafeSingleSendRequired() {return this.singleSendRequired != null ? this.singleSendRequired : false;}
			private String getContent() {
				String content = this.template;
				
				Map<String, String> params = this.replacements != null ? new HashMap<>(this.replacements) : new HashMap<>();
				Map<String, FormatHelper.Type.Format> types = this.formats != null ? new HashMap<>(this.formats) : new HashMap<>();
				
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					
					content = content.replace(key, FormatHelper.Util.safeParse(types.getOrDefault(key, FormatHelper.Type.Format.STRING), value));
				}
				return content;
			}
		}
	}
	public static final class Util {
		public static final List<String> sendFcm(Vo.AppPush appPush) {
			return send(appPush, Type.Target.FCM);
		}
		private static final List<String> send(Vo.AppPush appPush, Type.Target targetType) {
			try {
				switch (targetType) {
					case FCM:
						return Fcm.send(appPush);
					default:
						return Collections.emptyList();
				}
			} catch (Exception e) {
				log.error("Exception in call [send] data.", e);
			}
			return Collections.emptyList();
		}
		private static final class Fcm {
			private static final String FCM_CONFIG_PATH = "fcm-config.json";
			
			private static final FirebaseMessaging FIREBASE_MESSAGING = FirebaseMessaging.getInstance();
			private static final String[] MESSAGE_KEYS = {"title", "body", "click_action"};
			
			private static final AndroidConfig.Priority ANDROID_PRIORITY = AndroidConfig.Priority.NORMAL;
			private static final long ANDROID_TIME_TO_LIVE = 60_000;
			
			private static final String IOS_HEADER_PRIORITY_KEY = "apns-priority";
			private static final String IOS_HEADER_PRIORITY_LEVEL = "5";
			private static final int IOS_BADGE_LEVEL = 0;
			
			static {
				try {
					if (FirebaseApp.getApps().isEmpty()) {
						FirebaseApp.initializeApp(
							FirebaseOptions.builder()
								.setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FCM_CONFIG_PATH).getInputStream()))
								.build()
						);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			
			private static final List<String> send(Vo.AppPush appPush) {
				List<String> tokenList = appPush.getTokenList();
				List<String> failTokenList = new ArrayList<>(tokenList.size());
				String subject = appPush.getSubject();
				String clickAction = appPush.getClickAction();
				
				if (appPush.getSafeSingleSendRequired()) {
					for (String token : tokenList) {
						String content = appPush.getContent();
						
						Message message = Message.builder()
							.setAndroidConfig(getAndroidConfig(subject, content))
							.setApnsConfig(getApnsConfig(subject, content))
							.setToken(token)
							.putData(MESSAGE_KEYS[0], subject)
							.putData(MESSAGE_KEYS[1], content)
							.putData(MESSAGE_KEYS[2], clickAction)
							.setNotification(getNotification(subject, content))
							.build();
						try {
							FIREBASE_MESSAGING.send(message);
						} catch (FirebaseMessagingException e) {
							errorHandler(e, failTokenList, token);
						}
					}
				} else {
					String content = appPush.getContent();
					
					MulticastMessage message = MulticastMessage.builder()
						.setAndroidConfig(getAndroidConfig(subject, content))
						.setApnsConfig(getApnsConfig(subject, content))
						.addAllTokens(tokenList)
						.putData(MESSAGE_KEYS[0], subject)
						.putData(MESSAGE_KEYS[1], content)
						.putData(MESSAGE_KEYS[2], clickAction)
						.setNotification(getNotification(subject, content))
						.build();
					try {
						BatchResponse batchResponse = FIREBASE_MESSAGING.sendEachForMulticast(message);
						
						if (batchResponse != null && batchResponse.getFailureCount() > 0) {
							List<SendResponse> sendResponseList = batchResponse.getResponses();
							int sendResponseSize = sendResponseList.size();
							for (int sendResponseNumber=0; sendResponseNumber<sendResponseSize; sendResponseNumber++) {
								SendResponse sendResponse = sendResponseList.get(sendResponseNumber);
								if (!sendResponse.isSuccessful()) {
									errorHandler(sendResponse.getException(), failTokenList, tokenList.get(sendResponseNumber));
								}
							}
						}
					} catch (FirebaseMessagingException e) {
						log.error("FirebaseMessagingException in call [send] data.", e);
					}
				}
				return failTokenList;
			}
			private static final AndroidConfig getAndroidConfig(String subject, String content) {
				return AndroidConfig.builder()
						.setPriority(ANDROID_PRIORITY)
						.setTtl(ANDROID_TIME_TO_LIVE)
						.setNotification(getAndroidNotification(subject, content))
						.build();
			}
			private static final AndroidNotification getAndroidNotification(String subject, String content) {
				return AndroidNotification.builder()
						.setTitle(subject)
						.setBody(content)
						.build();
			}
			private static final ApnsConfig getApnsConfig(String subject, String content) {
				return ApnsConfig.builder()
						.putHeader(IOS_HEADER_PRIORITY_KEY, IOS_HEADER_PRIORITY_LEVEL)
						.setAps(getAps(subject, content))
						.build();
			}
			private static final Aps getAps(String subject, String content) {
				return Aps.builder()
						.setBadge(IOS_BADGE_LEVEL)
						.setAlert(getApsAlert(subject, content))
						.build();
			}
			private static final ApsAlert getApsAlert(String subject, String content) {
				return ApsAlert.builder()
						.setTitle(subject)
						.setBody(content)
						.build();
			}
			private static final Notification getNotification(String subject, String content) {
				return Notification.builder()
						.setTitle(subject)
						.setBody(content)
						.build();
			}
			private static final void errorHandler(FirebaseMessagingException e, List<String> failTokenList, String token) {
				if (e != null) {
					switch (e.getMessagingErrorCode()) {
						case UNREGISTERED:
							failTokenList.add(token);
							return;
						case INVALID_ARGUMENT:
							failTokenList.add(token);
							return;
						default:
							log.error("FirebaseMessagingException in call [send] token : {}, {}:{}", token, e.getErrorCode(), e.getMessagingErrorCode());
							return;
					}
				}
			}
		}
	}
}