package ru.r1zen.findip.utils;

import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;

public abstract class Writer {
    @SuppressWarnings("deprecation")
    public static boolean writeIntoExcel(ObservableList<String> result, String resultPath){
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Результат");

        //if (!ProgramManager.badIp.isEmpty())
            //result.addAll(ProgramManager.badIp);

        for (int i = 0; i < result.size(); i++) {
            Row row = sheet.createRow(i);
            Cell ip = row.createCell(0);
            ip.setCellValue(result.get(i).substring(0, result.get(i).indexOf(";")));

            Cell index = row.createCell(1);
            index.setCellValue(result.get(i).substring(result.get(i).indexOf(";") + 1, result.get(i).lastIndexOf(";")));
            Cell address = row.createCell(2);
            address.setCellValue(result.get(i).substring(result.get(i).lastIndexOf(";") + 1));
        }

        Row row = book.getSheetAt(0).getRow(0);
        for(int j = 0; j < row.getLastCellNum(); j++)
            book.getSheetAt(0).autoSizeColumn(j);

        sheet.autoSizeColumn(1);

        if (!resultPath.endsWith(".xls"))
            resultPath = resultPath.substring(0, resultPath.indexOf(".")) + ".xls";

        try {
            FileOutputStream fos = new FileOutputStream(resultPath);
            book.write(fos);
            fos.close();
            book.close();
            LogManager.addMessage("Результат записан в файл");
            return true;
        } catch (IOException e) {
            LogManager.addError("Файл ответа не найден или недоступен");
            return false;
        }
    }
}
