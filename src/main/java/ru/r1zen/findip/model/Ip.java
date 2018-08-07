package ru.r1zen.findip.model;

import ru.r1zen.findip.utils.LogManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ip {
    private String ip;
    static ArrayList<String> correctIpList = new ArrayList<>();
    static ArrayList<String> incorrectIpList = new ArrayList<>();

    public Ip(String ip) {
        if (isIpCorrect(ip)) {
            this.ip = ip;
            correctIpList.add(this.ip);
        } else {
            this.ip = ip;
            incorrectIpList.add(this.ip);
            LogManager.addError("Запрос содержит некорректный IP-адрес (" + this.ip + ")");
        }

    }

    //проверка корректности IP-адреса
    static boolean isIpCorrect(String ip) {
        Pattern pattern = Pattern.compile("^(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[0-9]{2}|[0-9])(\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[0-9]{2}|[0-9])){3}$");
        Matcher matcher = pattern.matcher(ip);
        boolean flag = false;

        if (matcher.find())
            flag = true;

        return flag;
    }
}
