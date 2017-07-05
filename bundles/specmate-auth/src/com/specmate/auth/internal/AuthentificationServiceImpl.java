package com.specmate.auth.internal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.auth.api.IAuthentificationService;
import com.specmate.common.SpecmateException;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.usermodel.User;
import com.specmate.usermodel.UsermodelPackage;

@Component
public class AuthentificationServiceImpl implements IAuthentificationService {

	private static final String HASH_ALGORITHM = "SHA-256";

	private IPersistencyService persistenceService;
	private ThreadLocal<ITransaction> transaction = new ThreadLocal<ITransaction>() {
		@Override
		protected ITransaction initialValue() {
			return persistenceService.openUserTransaction();
		};
	};
	private LogService logService;

	@Override
	public boolean authenticate(String username, String password, String url) {
		List<Object> matches = transaction.get().query(
				"usermodel::User.allInstances()->select(u:User | u.name = '" + username + "')",
				UsermodelPackage.Literals.USER);
		if (matches.size() > 1) {
			logService.log(LogService.LOG_ERROR, "Provided credentials matched more than one user: " + username);
			return false;
		} else if (matches.size() == 0) {
			logService.log(LogService.LOG_INFO, "Attempt to authenticate with unknown user: " + username);
			return false;
		} else {
			User user = (User) matches.get(0);
			String salt = user.getSalt();
			String passwordHash;
			try {
				passwordHash = hashPassword(password, salt);
			} catch (SpecmateException e) {
				return false;
			}
			if (passwordHash.equals(user.getPasswordHash())) {
				return true;
			} else {
				logService.log(LogService.LOG_INFO, "Attempt to authenticate with wrong password: " + username);
				return false;
			}
		}
	}

	private String hashPassword(String password, String salt) throws SpecmateException {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(HASH_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			logService.log(LogService.LOG_ERROR,
					"Could not perform authentication. Unknown hash algorithm: " + HASH_ALGORITHM);
			throw new SpecmateException(e);
		}

		digest.update(salt.getBytes());
		byte[] result = digest.digest(password.getBytes());
		String resultString = Hex.encodeHexString(result);
		return resultString;
	}

	@Reference
	public void setPersistencyService(IPersistencyService service) {
		this.persistenceService = service;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
