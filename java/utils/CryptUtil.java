import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.simple.JSONObject;

public class CryptUtil {
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_str_null					= (null);
	private static final String						_str_charset				= ("UTF-8");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final int						_sub_strt					= (0);
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final JSONObject					_jo_def						= (new JSONObject());
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final Encoder					_encr64						= (Base64.getEncoder());
	private static final Decoder					_decr64						= (Base64.getDecoder());
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_aes_256_alg				= ("AES/CBC/PKCS5Padding");
	private static final String						_aes_256_key				= ("01234567890123456789012345678901");
	private static final int						_aes_256_iv_len				= (32 / 2);
	private static final String						_aes_256_iv					= (_aes_256_key.substring(_sub_strt, _aes_256_iv_len));
	private static final SecretKeySpec				_aes_256_scrt_key			= (new SecretKeySpec(_aes_256_key.getBytes(), "AES"));
	private static final IvParameterSpec			_aes_256_spec_iv			= (new IvParameterSpec(_aes_256_iv.getBytes()));
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_sha_512_alg				= ("SHA-512");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_md_5_alg					= ("MD5");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String enc(Object o) {
		if (o != null && o.toString().trim().length() > 0) {
			try {
				return (_encr64.encodeToString(o.toString().trim().getBytes(_str_charset)));
			} catch (UnsupportedEncodingException e) {
			}
		}
		return (_str_null);
	}
	public static JSONObject joEnc(JSONObject j) {
		if (j != null && !j.isEmpty()) {
			JSONObject rJo = (new JSONObject());
			Object o;
			for (Object k : j.keySet()) {
				if (k != null && k.toString().trim().length() > 0) {
					o = (j.get(k));
					if (o != null && o.toString().trim().length() > 0) {
						try {
							rJo.put(k.toString().trim(), (_encr64.encodeToString(o.toString().trim().getBytes(_str_charset))));
						} catch (UnsupportedEncodingException e) {
						}
					}
				}
			}
			return ((rJo != null && !rJo.isEmpty()) ? (rJo) : (_jo_def));
		}
		return (_jo_def);
	}
	public static String dec(Object o) {
		if (o != null && o.toString().trim().length() > 0) {
			try {
				return (new String (_decr64.decode(o.toString().getBytes(_str_charset))).trim());
			} catch (UnsupportedEncodingException e) {
			}
		}
		return (_str_null);
	}
	public static JSONObject joDec(JSONObject j) {
		if (j != null && !j.isEmpty()) {
			JSONObject rJo = (new JSONObject());
			Object o;
			for (Object k : j.keySet()) {
				if (k != null && k.toString().trim().length() > 0) {
					o = (j.get(k));
					if (o != null && o.toString().trim().length() > 0) {
						try {
							rJo.put(k.toString().trim(), (new String (_decr64.decode(o.toString().getBytes(_str_charset))).trim()));
						} catch (UnsupportedEncodingException e) {
						}
					}
				}
			}
			return ((rJo != null && !rJo.isEmpty()) ? (rJo) : (_jo_def));
		}
		return (_jo_def);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String aesEnc(Object o) {
		if (o != null && o.toString().trim().length() > 0) {
			try {
				Cipher cph = (Cipher.getInstance(_aes_256_alg));
				try {
					cph.init(Cipher.ENCRYPT_MODE, _aes_256_scrt_key, _aes_256_spec_iv);
					try {
						return (_encr64.encodeToString(cph.doFinal(o.toString().trim().getBytes(_str_charset))));
					} catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
					}
				} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
				}
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			}
		}
		return (_str_null);
	}
	public static JSONObject joAesEnc(JSONObject j) {
		if (j != null && !j.isEmpty()) {
			try {
				Cipher cph = (Cipher.getInstance(_aes_256_alg));
				try {
					cph.init(Cipher.ENCRYPT_MODE, _aes_256_scrt_key, _aes_256_spec_iv);
					JSONObject rJo = (new JSONObject());
					Object o;
					for (Object k : j.keySet()) {
						if (k != null && k.toString().trim().length() > 0) {
							o = (j.get(k));
							if (o != null && o.toString().trim().length() > 0) {
								try {
									rJo.put(k.toString().trim(), (_encr64.encodeToString(cph.doFinal(o.toString().trim().getBytes(_str_charset)))));
								} catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
								}
							}
						}
					}
					return ((rJo != null && !rJo.isEmpty()) ? (rJo) : (_jo_def));
				} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
				}
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			}
		}
		return (_jo_def);
	}
	public static String aesDec(Object o) {
		if (o != null && o.toString().trim().length() > 0) {
			try {
				Cipher cph = (Cipher.getInstance(_aes_256_alg));
				try {
					cph.init(Cipher.DECRYPT_MODE, _aes_256_scrt_key, _aes_256_spec_iv);
					try {
						return ((new String (cph.doFinal(_decr64.decode(o.toString().trim().getBytes(_str_charset))), _str_charset)).trim());
					} catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
					}
				} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
				}
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			}
		}
		return (_str_null);
	}
	public static JSONObject joAesDec(JSONObject j) {
		if (j != null && !j.isEmpty()) {
			try {
				Cipher cph = (Cipher.getInstance(_aes_256_alg));
				try {
					cph.init(Cipher.DECRYPT_MODE, _aes_256_scrt_key, _aes_256_spec_iv);
					JSONObject rJo = (new JSONObject());
					Object o;
					for (Object k : j.keySet()) {
						if (k != null && k.toString().trim().length() > 0) {
							o = (j.get(k));
							if (o != null && o.toString().trim().length() > 0) {
								try {
									rJo.put(k.toString().trim(), ((new String (cph.doFinal(_decr64.decode(o.toString().trim().getBytes(_str_charset))), _str_charset)).trim()));
								} catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
								}
							}
						}
					}
					return ((rJo != null && !rJo.isEmpty()) ? (rJo) : (_jo_def));
				} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
				}
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			}
		}
		return (_jo_def);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String shaEnc(Object o) {
		if (o != null && o.toString().trim().length() > 0) {
			try {
				MessageDigest md = MessageDigest.getInstance(_sha_512_alg);
				try {
					md.update(o.toString().trim().getBytes(_str_charset));
					return (_encr64.encodeToString(md.digest()));
				} catch (UnsupportedEncodingException e) {
				}
			} catch (NoSuchAlgorithmException e) {
			}
		}
		return (_str_null);
	}
	public static JSONObject joShaEnc(JSONObject j) {
		if (j != null && !j.isEmpty()) {
			try {
				MessageDigest md = MessageDigest.getInstance(_sha_512_alg);
				JSONObject rJo = (new JSONObject());
				Object o;
				for (Object k : j.keySet()) {
					if (k != null && k.toString().trim().length() > 0) {
						o = (j.get(k));
						if (o != null && o.toString().trim().length() > 0) {
							try {
								md.update(o.toString().trim().getBytes(_str_charset));
								rJo.put(k.toString().trim(), (_encr64.encodeToString(md.digest())));
							} catch (UnsupportedEncodingException e) {
							}
						}
					}
				}
				return ((rJo != null && !rJo.isEmpty()) ? (rJo) : (_jo_def));
			} catch (NoSuchAlgorithmException e) {
			}
		}
		return (_jo_def);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String mdEnc(Object o) {
		if (o != null && o.toString().trim().length() > 0) {
			try {
				MessageDigest md = MessageDigest.getInstance(_md_5_alg);
				try {
					md.update(o.toString().trim().getBytes(_str_charset));
					return (_encr64.encodeToString(md.digest()));
				} catch (UnsupportedEncodingException e) {
				}
			} catch (NoSuchAlgorithmException e) {
			}
		}
		return (_str_null);
	}
	public static JSONObject joMdEnc(JSONObject j) {
		if (j != null && !j.isEmpty()) {
			try {
				MessageDigest md = MessageDigest.getInstance(_md_5_alg);
				JSONObject rJo = (new JSONObject());
				Object o;
				for (Object k : j.keySet()) {
					if (k != null && k.toString().trim().length() > 0) {
						o = (j.get(k));
						if (o != null && o.toString().trim().length() > 0) {
							try {
								md.update(o.toString().trim().getBytes(_str_charset));
								rJo.put(k.toString().trim(), (_encr64.encodeToString(md.digest())));
							} catch (UnsupportedEncodingException e) {
							}
						}
					}
				}
				return ((rJo != null && !rJo.isEmpty()) ? (rJo) : (_jo_def));
			} catch (NoSuchAlgorithmException e) {
			}
		}
		return (_jo_def);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
}