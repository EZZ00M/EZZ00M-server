package ezz00m.ezz00mserver.global.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access=PRIVATE)
public class CsvResultDto {
    private final Map<Integer, Integer> successedTimeMap;
    private final List<Map<String, String>> failedTimeMap;

    public static CsvResultDto of(Map<Integer, Integer> studentTimeMap, List<Map<String, String>> failedTimeMap) {
        return new CsvResultDto(studentTimeMap, failedTimeMap);
    }

}

