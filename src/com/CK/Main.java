package com.CK;

public class Main {

    public static void main(String[] args) {
	// write your code here
    }
}

// Regex
public class Solution {
    public String validIPAddress(String IP) {
        if(IP.matches("(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])"))return "IPv4";
        if(IP.matches("(([0-9a-fA-F]{1,4}):){7}([0-9a-fA-F]{1,4})"))return "IPv6";
        return "Neither";
    }
}

// Factory Design Pattern
class Solution {
    public String validIPAddress(String IP) {
        Validator validator = new Factory().generateValidator(IP);
        return validator.parseAddress(IP);
    }
}

class Factory {

    Validator generateValidator(String s) {
        int dotIndex = s.indexOf("."), colonsIndex = s.indexOf(":");
        if ((dotIndex >= 0 && colonsIndex >= 0) || (dotIndex < 0 && colonsIndex < 0))
            return new InvalidAddress();
        if (dotIndex >= 0)
            return new IPv4Validator();
        else
            return new IPv6Validator();
    }
}

abstract class Validator {

    abstract String parseAddress(String s);
}

class IPv4Validator extends Validator {

    String parseAddress(String s) {
        String[] sArr = s.split("\\.");
        if (sArr.length != 4 || s.charAt(0) == '.' || s.charAt(s.length() - 1) == '.')
            return "Neither";

        for (String subS : sArr) {
            if (subS.length() == 0)
                return "Neither";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < subS.length(); i++) {
                int c = subS.charAt(i) - '0';
                if (c < 0 || c > 9 || sb.length() > 3) {
                    return "Neither";
                }

                if (sb.length() > 0 && sb.charAt(0) == '0') {
                    return "Neither";
                }

                sb.append(c);
            }
            int num = Integer.parseInt(sb.toString());
            if (num < 0 || num > 255) {
                return "Neither";
            }
        }
        return "IPv4";
    }
}

class IPv6Validator extends Validator {

    String parseAddress(String s) {
        String[] sArr = s.split(":");
        if (sArr.length != 8 || s.charAt(0) == ':' || s.charAt(s.length() - 1) == ':')
            return "Neither";
        for (String subS : sArr) {
            if (subS.length() == 0 || subS.length() > 4)
                return "Neither";
            for (int i = 0; i < subS.length(); i++) {
                int c = subS.charAt(i);
                if (Character.isDigit(c)) {
                    continue;
                }

                if (('a' <= c && c <= 'f') || ('A' <= c && c <= 'F')) {
                    continue;
                }

                return "Neither";
            }

        }
        return "IPv6";
    }
}

class InvalidAddress extends Validator {

    String parseAddress(String s) {
        return "Neither";
    }
}