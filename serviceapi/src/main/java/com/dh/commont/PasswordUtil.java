package com.dh.commont;

import com.dh.configure.Consts;
import com.dh.entity.User;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import static com.dh.service.impl.UserServiceImpl.HASH_INTERATIONS;

/**
 * Created by Coolkid on 2016/9/14.
 */
public class PasswordUtil {
    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private static final int SALT_SIZE = 8;
    public static void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }
}
