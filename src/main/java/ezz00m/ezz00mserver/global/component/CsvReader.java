package ezz00m.ezz00mserver.global.component;

import ezz00m.ezz00mserver.global.dto.CsvResultDto;
import ezz00m.ezz00mserver.global.exception.GeneralException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ezz00m.ezz00mserver.global.codes.ErrorCode.ANALYZED_FAIL;

@Component
public class CsvReader {

    final ExcelGenerator excelGenerator;

    public CsvReader(ExcelGenerator excelGenerator) {
        this.excelGenerator = excelGenerator;
    }

    public CsvResultDto parseCsvFile(MultipartFile file) {
        Map<String, Map<Integer, Integer>> successRows = new HashMap<>();
        Map<String, Integer> failedRows = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                try {
                    String[] columns = line.split(",");
                    String nameWithId = columns[0];
                    int accessTime = Integer.parseInt(columns[4]);

                    int studentId = extractStudentNumber(nameWithId);

                    successRows.computeIfAbsent(nameWithId, k -> new HashMap<>())
                            .merge(studentId, accessTime, Integer::sum);
                } catch (Exception e) {
                    try {
                        String[] columns = line.split(",");
                        String name = columns[0];
                        int accessTime = Integer.parseInt(columns[4]);

                        failedRows.merge(name, accessTime, Integer::sum);
                    } catch (Exception innerException) {
                        throw new GeneralException(ANALYZED_FAIL);
                    }
                }
            }
        } catch (Exception e) {
            throw new GeneralException(ANALYZED_FAIL);
        }
        return CsvResultDto.of(successRows, failedRows);
    }

    private int extractStudentNumber(String nameWithId) {
        //숫자가 연달아 7자인 경우
        Pattern pattern = Pattern.compile("\\b\\d{7}\\b");
        Matcher matcher = pattern.matcher(nameWithId);
        if (matcher.find())
            return Integer.parseInt(matcher.group());

        //숫자가 총 7자인 경우
        String idString = nameWithId.replaceAll("[^0-9]", "");
        if(idString.length()==7)
            return Integer.parseInt(idString);

        //숫자가 총 14자인 경우
        if (idString.length() == 14 && idString.substring(0, 7).equals(idString.substring(7, 14)))
            return Integer.parseInt(idString.substring(0, 7));

        throw new IllegalArgumentException();
    }

}
