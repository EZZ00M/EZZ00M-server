package ezz00m.ezz00mserver.global.component;

import ezz00m.ezz00mserver.global.domain.CsvResultDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CsvReader {

    final ExcelGenerator excelGenerator;

    public CsvReader(ExcelGenerator excelGenerator) {
        this.excelGenerator = excelGenerator;
    }


    public CsvResultDto parseCsvFile(MultipartFile file) {
        Map<Integer, Integer> studentTimeMap = new HashMap<>();
        List<Map<String, String>> failedRows = new ArrayList<>();

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
                    int timeInMinutes = Integer.parseInt(columns[4]);

                    int studentId = extractStudentNumber(nameWithId);

                    studentTimeMap.merge(studentId, timeInMinutes, Integer::sum);
                } catch (Exception e) {
                    String[] columns = line.split(",");
                    Map<String, String> failedRow = new HashMap<>();
                    failedRow.put("name", columns[0]);
                    failedRow.put("accessTime", columns[4]);
                    failedRows.add(failedRow);                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return CsvResultDto.of(studentTimeMap, failedRows);
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
