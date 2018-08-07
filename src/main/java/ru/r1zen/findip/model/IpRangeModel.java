package ru.r1zen.findip.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.net.util.SubnetUtils;
import ru.r1zen.findip.utils.Loader;
import ru.r1zen.findip.utils.LogManager;
import ru.r1zen.findip.utils.Writer;

import java.util.ArrayList;

public class IpRangeModel implements Model {
    private static ObservableList<IpRange> ipRangeList = FXCollections.observableArrayList();
    private static ObservableList<String> requestList = FXCollections.observableArrayList();
    private static ObservableList<String> result = FXCollections.observableArrayList();
    private boolean isDatabaseLoaded = false;
    private boolean isRequestLoaded = false;

    @Override
    public boolean loadDataBase(String fileName) {
        ipRangeList.clear();
        ArrayList<String> list = Loader.loadDataBase(fileName);
        int line = 1;
        for (String ip : list) {
            try {
                String tmpIp = ip.substring(0, ip.indexOf(";"));
                String tmpPostalAddress = ip.substring(ip.indexOf(";") + 1, ip.lastIndexOf(";"));
                String tmpAddress = ip.substring(ip.lastIndexOf(";") + 1);
                if (tmpIp.contains("/"))
                    ipRangeList.add(new IpRange(tmpIp, tmpPostalAddress, tmpAddress));

            } catch (StringIndexOutOfBoundsException ex) {
                LogManager.addError("Нет IP-диапазона в строке №" + line + " (" + ip + ")");
            }catch (Exception e) {
                LogManager.addError(e.getMessage());
            }
            line++;
        }

        if (ipRangeList.size() > 0) {
            LogManager.addMessage("Файл базы данных успешно загружен");
        }

        return ipRangeList.size() > 0;
    }

    @Override
    public boolean loadRequest(String fileName) {
        requestList.clear();
        Ip.correctIpList.clear();
        Ip.incorrectIpList.clear();

        if (Ip.isIpCorrect(fileName)) {
            new Ip(fileName);
            requestList.addAll(Ip.correctIpList);
        }

        else {
            ArrayList<String> list = Loader.loadRequest(fileName);
            for (String ip : list)
                new Ip(ip);

            requestList.addAll(Ip.correctIpList);

            if (requestList.size() > 0)
                LogManager.addMessage("Файл запроса успешно загружен");
        }

        return requestList.size() > 0;
    }

    @Override
    public void findIp(String resultPath) {
        try {
            result.clear();
            int founded = 0;
            if (!requestList.isEmpty() && !ipRangeList.isEmpty()) {
                for (IpRange ipRange : ipRangeList) {
                    if (founded == requestList.size())
                        break;

                    try {
                        SubnetUtils.SubnetInfo subnetUtils = new SubnetUtils(ipRange.getIpRange()).getInfo();
                        for (String anIpListFromRequest : requestList) {
                            //проверяем входит ли IP адрес в диапазон
                            if (subnetUtils.isInRange(anIpListFromRequest)) {
                                result.add(anIpListFromRequest + ";" + ipRange.getPostalAddress() + ";" + ipRange.getAddress());
                                LogManager.addMessage("IP-адрес " + anIpListFromRequest + " найден в диапазоне " + ipRange.getIpRange());
                                founded++;
                            }
                        }
                    } catch (IllegalArgumentException ex) {
                        LogManager.addError("Неверно указан диапазон " + ipRange.getIpRange() + " (строка игнорируется) ");
                    }
                }
                check();
                writeResult(resultPath);

            }
        }catch (Exception ex) {
            LogManager.addError(ex.getMessage());
        }
    }

    private void check() {
        if (result.size() < requestList.size()) {
            ArrayList<String> tmp = new ArrayList<>();
            for (String s : result)
                tmp.add(s.substring(0, s.indexOf(";")));

            for (String string : requestList) {
                if (!tmp.contains(string)) {
                    String newStr = string + ";;IP-адрес не найден в базе";
                    result.add(newStr);
                    LogManager.addMessage(newStr.substring(0, newStr.indexOf(";")) + " IP-адрес не найден в базе");
                }
            }
        }
        if (!Ip.incorrectIpList.isEmpty()) {
            for (String s : Ip.incorrectIpList)
                result.add(s + ";;Некорректный IP-адрес");
        }
    }

    @Override
    public void writeResult(String fileName) {
        Writer.writeIntoExcel(result, fileName);
    }
}
