package ezz00m.ezz00mserver.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Schema(description = "Single Zoom Log Analyzed Response")
public class SingleResponseDto {

    @Schema(description = "분석 파일 주소", example = "https://aws.com")
    private final String analyzedFile;

    public static SingleResponseDto of(String analyzedFile) {
        return new SingleResponseDto(
                analyzedFile
        );
    }
}