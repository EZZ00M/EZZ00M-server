package ezz00m.ezz00mserver.domain;

import ezz00m.ezz00mserver.global.component.CsvReader;
import ezz00m.ezz00mserver.global.component.ExcelGenerator;
import ezz00m.ezz00mserver.global.component.WorkbookToMultipartFileConverter;
import ezz00m.ezz00mserver.global.config.S3Uploader;
import ezz00m.ezz00mserver.global.domain.CsvResultDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EZZ00MService {

    private final CsvReader csvReader;
    private final ExcelGenerator excelGenerator;
    private final WorkbookToMultipartFileConverter workbookToMultipartFileConverter;
    private final S3Uploader s3Uploader;

    public String getAnalyzedZoomLogForSingleSession(MultipartFile zoomLogFile, int completionTime) throws IOException {
        CsvResultDto csvResultDto = csvReader.parseCsvFile(zoomLogFile);
        Workbook workbook = excelGenerator.generateOnlineAttendanceExcel(csvResultDto.getSuccessedTimeMap(), csvResultDto.getFailedTimeMap(), completionTime);
        MultipartFile onlineAttendanceFile = workbookToMultipartFileConverter.convert(workbook, zoomLogFile.getName()+"_EZZ00M.xlsx");
        return s3Uploader.saveFile(onlineAttendanceFile);
    }

    public List<String> getAnalyzedZoomLogForMultiSession(List<MultipartFile> zoomLogFileList, int completionCount, int completionTime) throws IOException {
        List<String> analyzedZoomLog = new ArrayList<>();
        for(MultipartFile zoomLogFile : zoomLogFileList) {
            CsvResultDto csvResultDto = csvReader.parseCsvFile(zoomLogFile);
            Workbook workbook = excelGenerator.generateOnlineAttendanceExcel(csvResultDto.getSuccessedTimeMap(), csvResultDto.getFailedTimeMap(), completionTime);
            MultipartFile onlineAttendanceFile = workbookToMultipartFileConverter.convert(workbook, zoomLogFile.getName()+"_EZZ00M.xlsx");
            analyzedZoomLog.add(s3Uploader.saveFile(onlineAttendanceFile));
        }
        return analyzedZoomLog;
    }
}
