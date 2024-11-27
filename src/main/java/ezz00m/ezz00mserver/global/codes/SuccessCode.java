package ezz00m.ezz00mserver.global.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    OK(HttpStatus.OK, "요청에 성공하였습니다."),

    CSV_ATTACH_SUCCESS(HttpStatus.OK, "CSV 파일 첨부에 성공하였습니다."),
    CSV_ANALYZE_SUCCESS(HttpStatus.OK, "CSV 파일 분석에 성공하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}