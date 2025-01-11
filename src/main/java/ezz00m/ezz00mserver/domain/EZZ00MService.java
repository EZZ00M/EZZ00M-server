package ezz00m.ezz00mserver.domain;

import ezz00m.ezz00mserver.global.component.CsvReader;
import ezz00m.ezz00mserver.global.component.ExcelGenerator;
import ezz00m.ezz00mserver.global.component.WorkbookToMultipartFileConverter;
import ezz00m.ezz00mserver.global.config.S3Uploader;
import ezz00m.ezz00mserver.global.dto.CsvResultDto;
import ezz00m.ezz00mserver.global.dto.MultiResponseDto;
import ezz00m.ezz00mserver.global.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ezz00m.ezz00mserver.global.dto.CompletionDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EZZ00MService {

    private final CsvReader csvReader;
    private final ExcelGenerator excelGenerator;
    private final WorkbookToMultipartFileConverter workbookToMultipartFileConverter;
    private final S3Uploader s3Uploader;

    public SingleResponseDto getAnalyzedZoomLogForSingleSession(MultipartFile zoomLogFile, int completionTime) throws IOException {
        System.out.println(zoomLogFile.getOriginalFilename());
        CsvResultDto csvResultDto = csvReader.parseCsvFile(zoomLogFile);
        Workbook workbook = excelGenerator.generateOnlineAttendanceExcel(csvResultDto.getSuccessedTimeMap(), csvResultDto.getFailedTimeMap(), completionTime);
        MultipartFile onlineAttendanceFile = workbookToMultipartFileConverter.convert(workbook, FilenameUtils.removeExtension(zoomLogFile.getOriginalFilename())+"_EZZ00M_analyzed.xlsx");
        return SingleResponseDto.of(s3Uploader.saveFile(onlineAttendanceFile));
    }

    public MultiResponseDto getAnalyzedZoomLogForMultiSession(List<MultipartFile> zoomLogFileList, int completionCount, int completionTime) throws IOException {
        List<String> detailAnalyzedFileList = new ArrayList<>();
        CompletionDto completionDto = CompletionDto.of(new HashMap<>());
        int time = 0;
        String eventTitle = FilenameUtils.removeExtension(zoomLogFileList.get(0).getOriginalFilename());

        for(MultipartFile zoomLogFile : zoomLogFileList) {
            time++;
            CsvResultDto csvResultDto = csvReader.parseCsvFile(zoomLogFile);

            for (Map.Entry<Integer, Integer> entry : csvResultDto.getSuccessedTimeMap().entrySet()) {
                Integer studentId = entry.getKey();
                Integer currentCompletionCount = completionDto.getCompletionTimes().getOrDefault(studentId, 0);
                completionDto.getCompletionTimes().put(studentId, currentCompletionCount + 1);
            }

            Workbook workbook = excelGenerator.generateOnlineAttendanceExcel(csvResultDto.getSuccessedTimeMap(), csvResultDto.getFailedTimeMap(), completionTime);
            MultipartFile onlineAttendanceFile = workbookToMultipartFileConverter.convert(workbook, FilenameUtils.removeExtension(zoomLogFile.getOriginalFilename())+"_"+time+"회차"+"_EZZ00M_analyzed.xlsx");
            detailAnalyzedFileList.add(s3Uploader.saveFile(onlineAttendanceFile));
        }
        Workbook workbook = excelGenerator.generateTotalOnlineAttendanceExcel(completionDto.getCompletionTimes(), completionCount);
        MultipartFile onlineAttendanceFile = workbookToMultipartFileConverter.convert(workbook, eventTitle+"total_EZZ00M_analyzed.xlsx");
        String totalAnalyzedFile = s3Uploader.saveFile(onlineAttendanceFile);
        return MultiResponseDto.of(totalAnalyzedFile, detailAnalyzedFileList);
    }
}
