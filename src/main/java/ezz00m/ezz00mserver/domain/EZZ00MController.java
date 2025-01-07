package ezz00m.ezz00mserver.domain;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/api/ezzoom")
@Tag(name="줌로그 분석 API", description="줌 로그 파일을 업로드하여 분석 파일을 받을 수 있습니다.")
public class EZZ00MController {

    @Autowired
    private EZZ00MService ezz00MService;

    @ResponseBody
    @PostMapping(value="/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="줌 로그 분석 (1회차 전용)")
    public ResponseEntity<?> getAnalyzedZoomLogForSingleSession(@RequestPart(value="zoomLogFile") MultipartFile zoomLogFile,
                                                                @RequestParam(value="completionTime") int completionTime) throws IOException {
        String analyzedZoomLog = ezz00MService.getAnalyzedZoomLogForSingleSession(zoomLogFile, completionTime);
        return ResponseEntity.ok(analyzedZoomLog);
    }

    @ResponseBody
    @PostMapping(value="/multi", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="줌 로그 분석 (n회차 전용)")
    public ResponseEntity<?> getAnalyzedZoomLogForMultiSession(@RequestPart(value="zoomLogFileList") List<MultipartFile> zoomLogFileList,
                                                               @RequestParam(value="completionCount") int completionCount,
                                                               @RequestParam(value="completionTime") int completionTime) throws IOException {
        List<String> analyzedZoomLog = ezz00MService.getAnalyzedZoomLogForMultiSession(zoomLogFileList, completionCount, completionTime);
        return ResponseEntity.ok(analyzedZoomLog);
    }

}
