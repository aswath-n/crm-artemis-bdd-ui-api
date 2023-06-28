package com.maersk.crm.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    public  Workbook xlsWorkBook;
    public File file;
    public  FileInputStream fileInputStream;

    public ExcelReader(String datataFileName) {
        file = new File(datataFileName);
        try {
            fileInputStream = new FileInputStream(file);
            try {
                xlsWorkBook = new XSSFWorkbook(fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Map<String, String> getExcelData(String sheetName, String dataName) {
        Map<String, String> hashMap = new HashMap<String, String>();
        try {

            Sheet sheet1 = xlsWorkBook.getSheet(sheetName);
            int totalRows = sheet1.getLastRowNum() - sheet1.getFirstRowNum();
            int matchingRows = getRowNumber(sheetName, dataName);

            Row row = sheet1.getRow(matchingRows);
            for (int i = 1; i < row.getLastCellNum(); i++) {
                Cell headerCell = sheet1.getRow(0).getCell(i);
                if (headerCell != null) {
                    headerCell.setCellValue(1);
                    Cell headerValueCell = row.getCell(i);
                    String coloumnHeader = headerCell.getStringCellValue();
                    String coloumnValue;
                    if (headerValueCell != null) {
                        headerValueCell.setCellType(CellType.forInt(1));
                        coloumnValue = headerValueCell.getStringCellValue();
                    } else {
                        coloumnValue = "";
                    }
                    hashMap.put(coloumnHeader, coloumnValue);
                }
            }


        } catch (Exception e) {
            System.out.println("Something Went wrong in reading excel.Please check the parameters "
                    + sheetName + "\n Row Name :" + dataName);
        }
        return hashMap;
    }

    public int getRowNumber(String sheetName, String dataName) {
        Sheet sheet1 = xlsWorkBook.getSheet(sheetName);
        for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
            Cell cell = sheet1.getRow(i).getCell(0);
            cell.setCellType(CellType.forInt(1));
            String testData = cell.getStringCellValue();
            if (testData.equalsIgnoreCase(dataName))
                return i;

        }
        return 0;
    }

    public List<String> getColoumnData(String sheetName, String coloumnName) {
        Sheet sheet1 = xlsWorkBook.getSheet(sheetName);
        List<String> coloumnDataList = new ArrayList<String>();

        int totalNoOfRows = sheet1.getLastRowNum() - sheet1.getFirstRowNum();
        int cellNo = getCellNumber(sheetName, coloumnName);

        for (int i = 1; i <= totalNoOfRows; i++) {
            Row row1 = sheet1.getRow(i);
            Cell cellValue = row1.getCell(cellNo);
            if (cellValue != null) {
                cellValue.setCellType(CellType.forInt(1));
                coloumnDataList.add(cellValue.getStringCellValue());
            }
        }
        return coloumnDataList;

    }

    public int getCellNumber(String sheetName, String coloumnName) {
        Sheet sheet1 = xlsWorkBook.getSheet(sheetName);
        Row row = sheet1.getRow(0);
        for (int i = 0; i <= row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            cell.setCellType(CellType.forInt(1));
            String cellName = cell.getStringCellValue();
            if (cellName.equalsIgnoreCase(coloumnName))
                return i;
        }
        return 0;

    }
    public String getCellData(String sheetName, int rowNum, int colNum) {
        DataFormatter df = new DataFormatter();
        Cell cell;
        String cellData = "";
        Sheet sheet1 = xlsWorkBook.getSheet(sheetName);
        try {
            cell = sheet1.getRow(rowNum).getCell(colNum);
            cellData = df.formatCellValue(cell);

        } catch (Exception e) {
            cellData = "";
        }
        return cellData;
    }
}
