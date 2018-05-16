package fishy.support.util;

import android.graphics.Color;

import java.util.Random;
import java.util.UUID;

/**
 * 生成各种随机值的工具类
 * <h3>
 * 编辑记录
 * </h3>
 * <p>
 * 第一次完成<br/>
 * edited by shallowFish on 2018/5/3
 * </p>
 *
 * @author ShallowFish
 * @version 1.0.0
 */

public class RandomUtil {

    /**
     * 得到随机颜色，a通道默认是满值
     *
     * @return
     */
    public static int getRandomColor() {
        int alpha = 255;
        int red = new Random().nextInt(256);
        int green = new Random().nextInt(256);
        int blue = new Random().nextInt(256);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * 得到随机的uuid
     *
     * @param deleteLine 是否去除-
     * @return
     */
    public static String getRandomUUID(boolean deleteLine) {
        String uuid = UUID.randomUUID().toString();
        String line = "-";
        if (deleteLine) {
            return uuid.replace(line, "");
        } else {
            return uuid;
        }
    }

    /**
     * 得到随机的一个范围内的随机值
     *
     * @param start range的开始
     * @param end   range的结束
     * @return 随机值，int型
     */
    public static int getRandomInRange(int start, int end) {
        if (end <= start) {
            throw new IllegalArgumentException("range is invalid!");
        }
        int range = end - start;
        Random random = new Random();
        random.nextInt(range + 1);
        return start + range;
    }

    /**
     * 得到随机的String，可能包含A~Z,a~z,0~9
     *
     * @param length 生成的string的长度
     * @return 生成的随机String
     */
    public static String getRandomString(int length) {
        String charPool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(charPool.length());

            sb.append(charPool.charAt(number));
        }
        return sb.toString().toLowerCase();
    }
}
