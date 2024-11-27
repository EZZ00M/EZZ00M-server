package ezz00m.ezz00mserver.global.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    CSV_NOT_FOUND(HttpStatus.NOT_FOUND, "CSV 파일이 아닙니다."),
    FILE_READ_FAIL(HttpStatus.BAD_REQUEST, "파일 형식이 잘못 되었습니다.."),
    ANALYZED_FAIL(HttpStatus.UNPROCESSABLE_ENTITY, "분석에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
