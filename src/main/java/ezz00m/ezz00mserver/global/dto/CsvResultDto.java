package ezz00m.ezz00mserver.global.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access=PRIVATE)
public class CsvResultDto {
    private final Map<String, Map<Integer, Integer>> successedTimeMap;
    private final Map<String, Integer> failedTimeMap;

    public static CsvResultDto of(Map<String, Map<Integer, Integer>> studentTimeMap, Map<String, Integer> failedTimeMap) {
        return new CsvResultDto(studentTimeMap, failedTimeMap);
    }

}

