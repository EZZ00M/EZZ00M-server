package ezz00m.ezz00mserver.global.component;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExcelGenerator {

    public Workbook generateOnlineAttendanceExcel(Map<Integer, Integer> successedTimeMap, List<Map<String, String>> failedTimeMap, int completionTime) {
        Workbook workbook = new XSSFWorkbook();

        // 성공 시간 시트 생성
        Sheet successSheet = workbook.createSheet("분석 성공한 접속 시간");
        createAttendanceSheetForSuccess(successSheet, successedTimeMap, completionTime);

        // 실패 시간 시트 생성
        Sheet failedSheet = workbook.createSheet("분석 실패한 접속 시간");
        createAttendanceSheetForFailed(failedSheet, failedTimeMap);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return workbook;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 성공 데이터 시트 생성
    private void createAttendanceSheetForSuccess(Sheet sheet, Map<Integer, Integer> timeMap, int completionTime) {
        // 헤더 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("학번");
        headerRow.createCell(1).setCellValue("접속 시간");
        headerRow.createCell(2).setCellValue("이수 여부");

        // 데이터 추가
        int rowNum = 1;
        for (Map.Entry<Integer, Integer> entry : timeMap.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
            row.createCell(2).setCellValue(completionTime < entry.getValue() ? "O" : "X");
        }
    }

    // 실패 데이터 시트 생성
    private void createAttendanceSheetForFailed(Sheet sheet, List<Map<String, String>> failedData) {
        // 헤더 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("이름");
        headerRow.createCell(1).setCellValue("접속 시간");

        // 데이터 추가
        int rowNum = 1;
        for (Map<String, String> failedRow : failedData) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(failedRow.get("name"));
            row.createCell(1).setCellValue(failedRow.get("accessTime"));
        }
    }

}
