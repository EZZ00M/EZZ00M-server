package ezz00m.ezz00mserver.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Schema(description = "Multi Zoom Log Analyzed Response")
public class MultiResponseDto {

    @Schema(description = "총 이수 여부 파일 주소", example = "https://aws.com")
    private final String totalAnalyzedFile;

    @Schema(description = "분석 파일 주소 리스트", example = "https://aws.com")
    private final List<String> detailAnalyzedFileList;

    public static MultiResponseDto of(String totalAnalyzedFile, List<String> detailAnalyzedFileList) {
        return new MultiResponseDto(
                totalAnalyzedFile,
                detailAnalyzedFileList
        );
    }
}