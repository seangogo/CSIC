package common.utils.password;

import common.utils.Digests;
import common.utils.Encodes;
import static common.utils.StringCodeEnum.HASH_INTERATIONS;

/**
 * Created by Coolkid on 2016/9/14.
 */
public class PasswordUtil {
    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private static final int SALT_SIZE = 8;
    public static String[] entryptSaltAndPassword(String password) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(password.getBytes(), salt, HASH_INTERATIONS);
        String[] result={Encodes.encodeHex(salt),Encodes.encodeHex(hashPassword)};
        return result;
    }
}
