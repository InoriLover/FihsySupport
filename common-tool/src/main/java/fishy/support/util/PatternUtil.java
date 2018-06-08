package fishy.support.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证的工具类
 * <h3>
 * 编辑记录
 * </h3>
 * <p>
 * 第一次完成<br/>
 * edited by shallowFish on 2018/5/16
 * </p>
 *
 * @author ShallowFish
 * @version 1.0.0
 */

public class PatternUtil {
    /**
     * 是否为合法邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern p = Pattern
                .compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 是否为合法手机号
     *
     * @param phone   手机号
     * @param isTight 检查是否严格
     * @return
     */
    public static boolean isPhone(String phone, boolean isTight) {
        Pattern p;
        if (isTight) {
            // 更新日期 2018年4月1日
//            移动号段：
//            134 135 136 137 138 139 147 150 151 152 157 158 159 172 178 182 183 184 187 188 198
//            联通号段：
//            130 131 132 145 155 156 166 171 175 176 185 186
//            电信号段：
//            133 149 153 173 177 180 181 189 199
//            虚拟运营商:
//            170
            p = Pattern.compile("^((13)|(14)|(15)|(16)|(17)|(18)|(19))[0-9]{9}");
        } else {
            //只检测首位是否为1
            p = Pattern.compile("^(1)[0-9]{10}");
        }
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 是否为有效的网址
     *
     * @param link
     * @return
     */
    public static boolean isValidLink(String link) {
        String pattern = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(link);
        return m.matches();
    }

}
