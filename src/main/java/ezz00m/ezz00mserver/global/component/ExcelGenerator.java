package ezz00m.ezz00mserver.global.component;

import ezz00m.ezz00mserver.global.codes.ErrorCode;
import ezz00m.ezz00mserver.global.exception.GeneralException;
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

    public Workbook generateOnlineAttendanceExcel(Map<String, Map<Integer, Integer>> successedTimeMap, Map<String, Integer> failedTimeMap, int completionTime) {
        Workbook workbook = new XSSFWorkbook();

        // 성공 시간 시트 생성
        Sheet successSheet = workbook.createSheet("분석 성공한 접속 시간");
        createAttendanceSheetForSuccess(successSheet, successedTimeMap, completionTime);

        // 실패 시간 시트 생성
        Sheet failedSheet = workbook.createSheet("분석 실패한 접속 시간");
        createAttendanceSheetForFailed(failedSheet, failedTimeMap, completionTime);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return workbook;
        } catch (IOException e) {
            throw new GeneralException(ErrorCode.ANALYZED_FAIL);
        }
    }

    // 성공 데이터 시트 생성
    private void createAttendanceSheetForSuccess(Sheet sheet, Map<String, Map<Integer, Integer>> successedTimeMap, int completionTime) {
        // 헤더 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("이름");
        headerRow.createCell(1).setCellValue("학번");
        headerRow.createCell(2).setCellValue("접속 시간");
        headerRow.createCell(3).setCellValue("이수 여부");

        // 데이터 추가
        int rowNum = 1;
        for (Map.Entry<String, Map<Integer, Integer>> outerEntry : successedTimeMap.entrySet()) {
            String nameWithId = outerEntry.getKey();
            Map<Integer, Integer> studentTimeMap = outerEntry.getValue();

            for (Map.Entry<Integer, Integer> innerEntry : studentTimeMap.entrySet()) {
                Integer studentId = innerEntry.getKey();
                Integer totalAccessTime = innerEntry.getValue();

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(nameWithId);
                row.createCell(1).setCellValue(studentId);
                row.createCell(2).setCellValue(totalAccessTime);
                row.createCell(3).setCellValue(completionTime <= totalAccessTime ? "O" : "X");
            }
        }
    }


    // 실패 데이터 시트 생성
    private void createAttendanceSheetForFailed(Sheet sheet, Map<String, Integer> timeMap, int completionTime) {
        // 헤더 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("이름");
        headerRow.createCell(1).setCellValue("접속 시간");
        headerRow.createCell(2).setCellValue("이수 여부");

        // 데이터 추가
        int rowNum = 1;
        for (Map.Entry<String, Integer> entry : timeMap.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
            row.createCell(2).setCellValue(completionTime <= entry.getValue() ? "O" : "X");
        }
    }

    //전체 이수 여부 파일 workbook 생성
    public Workbook generateTotalOnlineAttendanceExcel(Map<Integer, Integer> successedTimeMap, int completionCount) {
        Workbook workbook = new XSSFWorkbook();

        Sheet successSheet = workbook.createSheet("전체 이수 여부");
        createTotalAttendanceSheet(successSheet, successedTimeMap, completionCount);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return workbook;
        } catch (IOException e) {
            throw new GeneralException(ErrorCode.ANALYZED_FAIL);
        }
    }

    private void createTotalAttendanceSheet(Sheet sheet, Map<Integer, Integer> timeMap, int completionCount) {
        // 헤더 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("학번");
        headerRow.createCell(1).setCellValue("이수 횟수");
        headerRow.createCell(2).setCellValue("이수 여부");

        // 데이터 추가
        int rowNum = 1;
        for (Map.Entry<Integer, Integer> entry : timeMap.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
            row.createCell(2).setCellValue(completionCount <= entry.getValue() ? "O" : "X");
        }
    }

}
