package ezz00m.ezz00mserver.global.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access=PRIVATE)
public class CompletionDto {
    private final Map<Integer, Integer> completionTimes;

    public static CompletionDto of(Map<Integer, Integer> completionTimes) {
        return new CompletionDto(completionTimes);
    }

}
