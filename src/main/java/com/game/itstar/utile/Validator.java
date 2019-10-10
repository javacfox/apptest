package com.game.itstar.utile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * 请求参数验证器
 */
@Getter
public class Validator {
    public static final int SUCCESS = 0;
    public static final int ARGUMENT_NULL = 101;
    public static final int ARGUMENT_ERROR = 102;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");
    public static final Pattern FAX_PATTERN = Pattern.compile("^(\\d{3,4}-)?\\d{7,8}$");
    private static final Pattern TEL_PATTERN = Pattern.compile("^((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$");
    private static final Pattern SPACE_PATTERN = Pattern.compile("\\s*|\t|\r|\n", Pattern.CASE_INSENSITIVE);
    private static final Pattern DATE_PATTERN =
            Pattern.compile("^\\d{4}[./-]\\d{1,2}[./-]\\d{1,2}[\\d\\.\\:\\+ZT ]+$", Pattern.CASE_INSENSITIVE);
    private static final Pattern EMOJI_PATTERN =
            Pattern.compile("[\\uD800-\\uDBFF\\uDC00-\\uDFFF\\u2600-\\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
    private static final Pattern IP_PATTERN =
            Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])"
                    + "\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])"
                    + "\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])"
                    + "\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
    private static final Pattern ID_CARD_NO_PATTERN =
            Pattern.compile("^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$");
    private final List<Valid> expressions = new ArrayList<>();
    private int code;
    private String message;

    public static Validator create() {
        return new Validator();
    }

    public static Validator create(String appId) {
        return Validator.create().appId(appId);
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isPhone(String phone) {
        return !isNullOrEmpty(phone) && (TEL_PATTERN.matcher(phone).matches() || isMobile(phone));
    }

    public static boolean isMobile(String mobile) {
        return hasMobile(mobile) && mobile.length() == 11;
    }

    public static boolean hasMobile(String content) {
        return !isNullOrEmpty(content) && MOBILE_PATTERN.matcher(content).find();
    }

    public static boolean hasFax(String content) {
        return !isNullOrEmpty(content) && FAX_PATTERN.matcher(content).find();
    }

    public static boolean isFax(String fax) {
        return hasFax(fax) && fax.length() >= 7;
    }

    public static boolean isEmail(String email) {
        return hasEmail(email) && email.length() > 4;
    }

    public static boolean hasEmail(String content) {
        return !isNullOrEmpty(content) && EMAIL_PATTERN.matcher(content).find();
    }

    public static boolean isIp(String ip) {
        return !isNullOrEmpty(ip) && IP_PATTERN.matcher(ip).matches();
    }

    public static boolean hasEmoji(String text) {
        return !isNullOrEmpty(text) && EMOJI_PATTERN.matcher(text).matches();
    }

    /**
     * 是否身份证号
     */
    public static boolean isIdCardNO(String text) {
        return !isNullOrEmpty(text)
                && text.length() >= 15
                && ID_CARD_NO_PATTERN.matcher(text).matches();
    }

//    private static boolean isDate(String str) {
//        try {
//            DateTime.parse(str);
//            return true;
//        } catch (Exception ex) {
//            return false;
//        }
//    }

    /**
     * 验证经纬度
     *
     * @param lon       精度
     * @param lat       维度
     * @param checkNull 是否检查为空
     * @return 是否是有效的经纬度
     */
    public static boolean isCoordinate(BigDecimal lon, BigDecimal lat, boolean checkNull) {
        final BigDecimal nullCoordinate = new BigDecimal(9999);
        final BigDecimal maxLongitude = new BigDecimal(180);
        final BigDecimal minLongitude = new BigDecimal(-180);
        final BigDecimal maxLatitude = new BigDecimal(90);
        final BigDecimal minLatitude = new BigDecimal(-90);

        //不校验空值时，当lon,lat==9999返回真
        if (!checkNull && nullCoordinate.equals(lon) && nullCoordinate.equals(lat)) {
            return true;
        }
        return lon.compareTo(minLongitude) == 1
                && lon.compareTo(maxLongitude) == -1
                && lat.compareTo(minLatitude) == 1
                && lat.compareTo(maxLatitude) == -1;
    }

    public boolean valid() {
        for (Valid valid : expressions) {
            if (!valid.getExpression().get()) {
                this.code = valid.getCode();
                this.message = valid.getAlter();
                return false;
            }
        }
        return true;
    }

    public <T> T convert(Function<Validator, T> function) {
        return function.apply(this);
    }

    public Validator valid(int code, String alter, Supplier<Boolean> expression) {
        Valid valid = new Valid(code, alter, expression);
        this.expressions.add(valid);
        return this;
    }

    public Validator validNull(String name, Supplier<Boolean> valid) {
        String alert = "缺少必要的请求参数:" + name;
        return valid(ARGUMENT_NULL, alert, valid);
    }

    public Validator validError(String name, Supplier<Boolean> valid) {
        String alert = "请求参数格式错误:" + name;
        return valid(ARGUMENT_ERROR, alert, valid);
    }

    public Validator appId(String appId) {
        return valid(ARGUMENT_NULL, "缺少参数 _appId", () -> !isNullOrEmpty(appId));
    }

    public Validator notNull(String name, Object value) {
        return validNull(name, () -> value != null);
    }

    public Validator notEmpty(String name, String value) {
        return validNull(name, () -> !isNullOrEmpty(value));
    }

    public Validator notEmpty(String name, Collection value) {
        return validNull(name, () -> value != null && value.size() > 0);
    }
//
//    public Validator strIsEmptyOrDate(String name, String value) {
//        return validNull(name, () -> isNullOrEmpty(value) || isDate(value));
//    }
//
//    public Validator strIsDate(String name, String value) {
//        return validNull(name, () -> !isNullOrEmpty(value) && isDate(value));
//    }

    public Validator greaterThanZero(String name, Number value) {
        return greaterThan(name, value, 0);
    }

    public Validator greaterThan(String name, Number value, Number min) {
        String alert = String.format("%s 值必须大于 %s", name, min);
        return valid(ARGUMENT_ERROR, alert, () -> value != null && value.doubleValue() > min.doubleValue());
    }

    public Validator greaterThanOrEqual(String name, Number value, Number min) {
        String alert = String.format("%s 值必须大于等于 %s", name, min);
        return valid(ARGUMENT_ERROR, alert, () -> value != null && value.doubleValue() >= min.doubleValue());
    }

    public Validator lessThan(String name, Number value, Number max) {
        String alert = String.format("%s值必须小于 %s", name, max);
        return valid(ARGUMENT_ERROR, alert, () -> value != null && value.doubleValue() < max.doubleValue());
    }

    public Validator lessThanOrEqual(String name, Number value, Number max) {
        String alert = String.format("%s 值必须小于等于 %s", name, max);
        return valid(ARGUMENT_ERROR, alert, () -> value != null && value.doubleValue() <= max.doubleValue());
    }

    public Validator range(String name, Number value, Number min, Number max) {
        String alert = String.format("%s值必须在 [%s~%s] 之间", name, min, max);
        return valid(ARGUMENT_ERROR, alert, () -> value.doubleValue() >= min.doubleValue()
                && value.doubleValue() <= max.doubleValue());
    }

    public Validator regexMatches(String name, String value, String pattern) {
        String alert = String.format("%s必须满足格式：%s", name, pattern);
        return valid(ARGUMENT_ERROR, alert, () -> Pattern.matches(pattern, value));
    }

    public Validator phone(String phone) {
        String alert = String.format("电话号码格式不正确，tel:[%s]", phone);
        return valid(ARGUMENT_ERROR, alert, () -> isPhone(phone));
    }

    public Validator mobile(String mobile) {
        String alert = String.format("手机号码格式不正确，mobile:[%s]", mobile);
        return valid(ARGUMENT_ERROR, alert, () -> isMobile(mobile));
    }

    public Validator email(String email) {
        String alert = String.format("邮箱格式不正确，email:[%s]", email);
        return valid(ARGUMENT_ERROR, alert, () -> isEmail(email));
    }

    public Validator idCardNo(String idCardNo) {
        String alert = String.format("身份证号格式不正确，number:[%s]", idCardNo);
        return valid(ARGUMENT_ERROR, alert, () -> isIdCardNO(idCardNo));
    }

    public Validator coordinate(BigDecimal lon, BigDecimal lat, boolean checkNull) {
        String alert = "经纬度数值超出范围(minX=-180.0,maxX=180.0,minY=-90.0,maxY=90.0)";
        return valid(ARGUMENT_ERROR, alert, () -> isCoordinate(lon, lat, checkNull));
    }

    public Validator ip(String ip) {
        String alert = "ip地址格式不正确";
        return valid(ARGUMENT_ERROR, alert, () -> isIp(ip));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class Valid {
        private int code;
        private String alter;
        private Supplier<Boolean> expression;
    }
}
