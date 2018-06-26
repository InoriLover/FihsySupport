package fishy.support.util;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 该类分为三块，主要考虑情景
 * <h2>不可逆编码，如MD5,SHA-1等</h2>
 * <h2>对称加密，如DES等</h2>
 * <h2>非对称加密，如RSA等</h2>
 * <h3>
 * 编辑记录
 * </h3>
 * <p>
 * 第一次完成<br/>
 * edited by fishy on 2018/6/26
 * </p>
 *
 * @author fishy
 * @version 1.0.0
 */

public class EncryptHelper {

    /**
     * 输出指定字符的md5指纹值
     * {@link MessageDigest#getInstance(String)}
     *
     * @param origin
     * @return can be null,if error happened
     */
    public static byte[] MD5Encode(String origin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(origin.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算指定文件的md5指纹值
     *
     * @param file
     * @return can be null,if error happened
     */
    public static byte[] MD5Encode(File file) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            FileInputStream in = new FileInputStream(file);
            DigestInputStream digestStream = new DigestInputStream(in, md);
            //2k buffer
            byte[] buffer = new byte[4096];
            //流数据全部读完
            while (digestStream.read(buffer) > -1) {

            }
            MessageDigest digest = digestStream.getMessageDigest();
            //输入流需要关闭
            digestStream.close();
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 输出指定字符的base64编码
     * <p>默认使用{@link Base64#DEFAULT}</p>
     *
     * @param origin
     * @return can be null,if error happened
     */
    public static byte[] BASE64Encode(String origin) {
        return Base64.encode(origin.getBytes(), Base64.DEFAULT);
    }

    /**
     * 输出指定字符的base64编码
     *
     * @param origin
     * @param mode   参考{@link Base64#DEFAULT},{@link Base64#NO_PADDING},
     *               {@link Base64#CRLF} etc
     * @return can be null,if error happened
     */
    public static byte[] BASE64Encode(String origin, int mode) {
        return Base64.encode(origin.getBytes(), mode);
    }

    /**
     * 输出base64的解码结果
     *
     * @param origin
     * @return can be null,if error happened
     */
    public static byte[] BASE64Decode(String origin) {
        return Base64.decode(origin.getBytes(), Base64.DEFAULT);
    }

    /**
     * 输出base64的解码结果
     *
     * @param origin
     * @param mode   参考{@link Base64#DEFAULT},{@link Base64#NO_PADDING},
     *               {@link Base64#CRLF} etc
     * @return can be null,if error happened
     */
    public static byte[] BASE64Decode(String origin, int mode) {
        return Base64.decode(origin.getBytes(), mode);
    }

    /**
     * @param origin
     * @return can be null,if error happened
     */
    public static byte[] SHA_1_Encode(String origin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(origin.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param file
     * @return can be null,if error happened
     */
    public static byte[] SHA_1_Encode(File file) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            FileInputStream in = new FileInputStream(file);
            DigestInputStream digestStream = new DigestInputStream(in, md);
            //2k buffer
            byte[] buffer = new byte[4096];
            //流数据全部读完
            while (digestStream.read(buffer) > -1) {

            }
            MessageDigest digest = digestStream.getMessageDigest();
            //输入流需要关闭
            digestStream.close();
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param origin
     * @return can be null,if error happened
     */
    public static byte[] SHA_256_Encode(String origin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(origin.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param file
     * @return can be null,if error happened
     */
    public static byte[] SHA_256_Encode(File file) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            FileInputStream in = new FileInputStream(file);
            DigestInputStream digestStream = new DigestInputStream(in, md);
            //2k buffer
            byte[] buffer = new byte[4096];
            //流数据全部读完
            while (digestStream.read(buffer) > -1) {

            }
            MessageDigest digest = digestStream.getMessageDigest();
            //输入流需要关闭
            digestStream.close();
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}
