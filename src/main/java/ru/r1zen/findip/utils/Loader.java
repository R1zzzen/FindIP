package ru.r1zen.findip.utils;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Loader {

    public static ArrayList<String> loadDataBase(String fileName) {
        ArrayList<String> list = new ArrayList<>();
        try {
            File file = new File(fileName);
            if (file.getName().endsWith(".csv")) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "cp1251"));
                while (br.ready())
                    list.add(br.readLine());
                br.close();
            } else
                LogManager.addError("Неверный формат БД");
        } catch (IOException e) {
            LogManager.addError(e.getMessage());
        }
        return list;
    }

    public static ArrayList<String> loadRequest(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase().trim();
        ArrayList<String> list = new ArrayList<>();

        switch (fileExtension) {
            case ".xls":
                list.addAll(loadXlsFile(fileName));
                break;

            case ".xlsx":
                list.addAll(loadXlsxFile(fileName));
                break;

            case ".csv":
                list.addAll(loadTxtFile(fileName));
                break;

            case ".txt":
                list.addAll(loadTxtFile(fileName));
                break;

            default:
                LogManager.addError("Не удалось згрузить запрос");
                break;
        }
        return list;
    }

    //CSV так же обрабатывается этим методом
    private static ArrayList<String> loadTxtFile(String fileName) {
        ArrayList<String> list = new ArrayList<>();
        try {
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "cp1251"));

            while (br.ready())
                list.add(br.readLine());

            br.close();
        } catch (Exception e) {
            LogManager.addError(e.getMessage());
        }
        return list;

    }

    private static ArrayList<String> loadXlsFile(String fileName) {
        ArrayList<String> list = new ArrayList<>();
        try {
            File file = new File(fileName);
            FileInputStream inputStream = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet lastSheet = workbook.getSheetAt(0);
            Iterator iterator = lastSheet.rowIterator();
            workbook.setForceFormulaRecalculation(true);

            while (iterator.hasNext()) {
                HSSFRow nextRow = (HSSFRow) iterator.next();
                list.add(nextRow.getCell(0).getStringCellValue());
            }

            inputStream.close();
            workbook.close();

        } catch (IOException e) {
            LogManager.addError(e.getMessage());
        }
        return list;
    }

    private static ArrayList<String> loadXlsxFile(String fileName) {
        ArrayList<String> list = new ArrayList<>();
        try {
            File file = new File(fileName);

            FileInputStream inputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet LastSheet = workbook.getSheetAt(0);
            Iterator iterator = LastSheet.rowIterator();
            workbook.setForceFormulaRecalculation(true);

            while (iterator.hasNext()) {
                XSSFRow nextRow = (XSSFRow) iterator.next();
                String ip = nextRow.getCell(0).getStringCellValue();
                list.add(ip);
            }
            inputStream.close();
            workbook.close();

        } catch (IOException e) {
            LogManager.addError(e.getMessage());
        }
        return list;
    }




}
