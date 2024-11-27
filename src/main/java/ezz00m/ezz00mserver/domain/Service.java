package ezz00m.ezz00mserver.domain;

import ezz00m.ezz00mserver.global.component.CsvReader;
import ezz00m.ezz00mserver.global.component.ExcelGenerator;
import ezz00m.ezz00mserver.global.component.WorkbookToMultipartFileConverter;
import ezz00m.ezz00mserver.global.config.S3Uploader;
import ezz00m.ezz00mserver.global.domain.CsvResultDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Service {

    @Autowired
    private final CsvReader csvReader;
    @Autowired
    private final ExcelGenerator excelGenerator;
    @Autowired
    private final WorkbookToMultipartFileConverter workbookToMultipartFileConverter;
    @Autowired
    private final S3Uploader s3Uploader;

    public String getAnalyzedZoomLog(MultipartFile zoomLogFile) throws IOException {
        CsvResultDto csvResultDto = csvReader.parseCsvFile(zoomLogFile);
        Workbook workbook = excelGenerator.generateOnlineAttendaceExcel(csvResultDto.getSuccessedTimeMap(), csvResultDto.getFailedTimeMap());
        MultipartFile onlineAttendanceFile = workbookToMultipartFileConverter.convert(workbook, zoomLogFile.getName()+"_EZZ00M.xlsx");
        return s3Uploader.saveFile(onlineAttendanceFile);
    }
}
