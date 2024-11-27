package ezz00m.ezz00mserver.global.exception;

import ezz00m.ezz00mserver.global.codes.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GeneralException extends RuntimeException {

    private final ErrorCode errorCode;
}
