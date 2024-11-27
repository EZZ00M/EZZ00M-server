package ezz00m.ezz00mserver.domain;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.projection.Accessor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/api/ezzoom")
@Tag(name="줌로그 분석 API", description="줌 로그 파일을 업로드하여 분석 파일을 받을 수 있습니다.")
public class Controller {

    private Service service;

    @ResponseBody
    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="줌 로그 분석")
    public ResponseEntity<?> getAnalyzedZoomLog(@RequestPart(value="zoomLogFile") MultipartFile zoomLogFile) throws IOException {
        String analyzedZoomLog = service.getAnalyzedZoomLog(zoomLogFile);
        return ResponseEntity.ok(analyzedZoomLog);
    }

}
