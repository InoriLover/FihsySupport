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
}
