package eu.rpm.encoding;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Roman {

    private Roman() {
        throw new UnsupportedOperationException();
    }

    private static LinkedHashMap<String, Integer> s2v;

    static {
        s2v = new LinkedHashMap<>();
        s2v.put("M", 1000);
        s2v.put("CM", 900);
        s2v.put("D", 500);
        s2v.put("CD", 400);
        s2v.put("C", 100);
        s2v.put("XC", 90);
        s2v.put("L", 50);
        s2v.put("XL", 40);
        s2v.put("X", 10);
        s2v.put("IX", 9);
        s2v.put("V", 5);
        s2v.put("IV", 4);
        s2v.put("I", 1);
    }

    public static String encode(int c) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : s2v.entrySet()) {
            int matches = c / entry.getValue();
            sb.append(repeatEncode(entry.getKey(), matches));
            c = c % entry.getValue();
        }
        return sb.toString();
    }

    public static String repeatEncode(String s, int n) {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }


    public static int decode(String s) {
        int acc = 0;

        while (!StringUtils.isEmpty(s)) {
            if (s.length() > 1) {
                String pre = s.substring(0, 2);
                Integer preValue = s2v.get(pre);
                if (preValue != null) {
                    acc += preValue;
                    s = s.substring(2);
                    continue;
                }
            }

            String pre = s.substring(0, 1);
            Integer preValue = s2v.get(pre);
            if (preValue != null) {
                acc += preValue;
                s = s.substring(1);
            }
        }

        return acc;
    }


}

