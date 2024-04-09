package eu.rpm.encoding;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum EncodeEnum {
    RAW,
    BIN,
    OCT,
    HEX,
    CUSTOM,
    ROMAN;

    private Object encodeInt(int c, Integer shift) {
        switch (this) {
            case RAW:
                return String.valueOf(c);
            case BIN:
                return Integer.toBinaryString(c);
            case OCT:
                return Integer.toOctalString(c);
            case HEX:
                return Integer.toHexString(c);
            case ROMAN:
                return Roman.encode(c);
            case CUSTOM:
                return Integer.toUnsignedString(c, shift);
            default:
                return null;
        }
    }

    public String encode(String str) {
        return encode(str, Character.MIN_RADIX);
    }

    public String encode(String str, Integer shift) {
        return StringUtils.join(str.chars().mapToObj(i -> encodeInt(i, shift)).collect(Collectors.toList()), StringUtils.SPACE);
    }

    //
    private String decodeChar(String s, Integer radix) {
        switch (this) {
            case RAW:
                return new Character((char) Integer.parseInt(s)).toString();
            case BIN:
                return new Character((char) Integer.parseInt(s, 2)).toString();
            case OCT:
                return new Character((char) Integer.parseInt(s, 8)).toString();
            case HEX:
                return new Character((char) Integer.parseInt(s, 16)).toString();
            case ROMAN:
                return new Character((char) Roman.decode(s)).toString();
            case CUSTOM:
                return new Character((char) Integer.parseInt(s, radix)).toString();
            default:
                return null;
        }
    }

    public String decode(String str, Integer radix) {
        return Arrays.stream(str.split(StringUtils.SPACE))
            .parallel()
            .map(encChar -> decodeChar(encChar, radix))
            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
            .toString();
    }

    public String decode(String str) {
        return decode(str, Character.MIN_RADIX);
    }
}
