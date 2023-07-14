package com.rupicard.assignment.service;


import com.rupicard.assignment.serviceexception.DBInsertionFailureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Slf4j
@Service
public class ExcelDBService {

    @Value("${excel.file.path}")
    private String excelFilePath;
    private static final int NAME_CELL_INDEX = 0;
    private static final int MOBILE_NUMBER_CELL_INDEX = 1;
    private static final int SHEET_INDEX = 0;

    public void saveDetails(String name, String mobileNumber) {

        try (Workbook workbook = fetchWorkbook()) {
            Sheet sheet = workbook.getSheetAt(SHEET_INDEX);
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);

            Cell nameCell = row.createCell(NAME_CELL_INDEX);
            nameCell.setCellValue(name);

            Cell mobileCell = row.createCell(MOBILE_NUMBER_CELL_INDEX);
            mobileCell.setCellValue(mobileNumber);

            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }
            log.info("Record written to excel workbook successfully!");

        } catch (IOException e) {
            log.error("Exception occurred: {}", e.getMessage());
            throw new DBInsertionFailureException("Unable to save data into the excel sheet");
        }
    }

    private Workbook fetchWorkbook() throws IOException {
        File file = new File(excelFilePath);

        if (file.exists()) {
            try (InputStream inputStream = new FileInputStream(file)) {
                return new XSSFWorkbook(inputStream);
            }
        } else {
            log.info("Creating a new Excel Workbook...");
            Workbook workbook = new XSSFWorkbook();
            workbook.createSheet();
            return workbook;
        }
    }
}
