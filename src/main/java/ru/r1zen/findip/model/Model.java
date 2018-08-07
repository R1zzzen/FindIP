package ru.r1zen.findip.model;

public interface Model {
    boolean loadDataBase(String fileName);
    boolean loadRequest(String fileName);
    void findIp(String resultPath);
    void writeResult(String fileName);
}
