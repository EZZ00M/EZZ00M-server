package ezz00m.ezz00mserver.global.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러, 관리자에게 문의 바랍니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    IO_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "IO 예외입니다."),
    INVALID_AUTHORITY(HttpStatus.UNAUTHORIZED, "해당 리소스에 대한 접근 권한이 없습니다."),


    CSV_NOT_FOUND(HttpStatus.NOT_FOUND, "CSV 파일이 아닙니다."),
    FILE_READ_FAIL(HttpStatus.BAD_REQUEST, "파일 형식이 잘못 되었습니다.."),
    ANALYZED_FAIL(HttpStatus.UNPROCESSABLE_ENTITY, "분석에 실패하였습니다."),
    SAVE_FILE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
