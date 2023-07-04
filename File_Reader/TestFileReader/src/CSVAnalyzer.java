import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CSVAnalyzer {
    public static void main(String[] args) {
        String folderPath = "C:\\Users\\Boyan\\Documents\\File_Reader";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter column:");
        int columnNumber = scanner.nextInt() - 1;

        analyzeCSVFiles(folderPath, columnNumber);
    }

    public static void analyzeCSVFiles(String folderPath, int columnNumber) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null){
            for (File file: files) {
                if (file.isFile() && file.getName().endsWith(".csv")){
                    analyzeCSVFile(file, columnNumber);
                }
            }
        }
    }

    public static void analyzeCSVFile(File file, int columnNumber) {
        String fileName = file.getName();
        List<Double> values = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(",");

                if (columnNumber < columns.length) {
                    String valueStr = columns[columnNumber].trim();
                    try {
                        double value = Double.parseDouble(valueStr);
                        values.add(value);
                    } catch (NumberFormatException e) {}
                }else{
                    System.out.println("Invalid column!");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!values.isEmpty()){
            double min = Collections.min(values);
            double max = Collections.max(values);
            double sum = 0.0;

            for (Double value: values) {
                sum = sum + value;
            }

            double avg = sum/values.size();
            int count = values.size();

            String result = String.format("%s -> Column %d: Min = %.0f, Max = %.0f, Avg = %.2f, Count = %d",
                                            fileName, columnNumber + 1, min, max, avg, count);

            saveToFile(result);
        }
    }

    public static void saveToFile(String result) {
        String outputFilePath = "C:\\Users\\Boyan\\Documents\\File_Reader\\output.txt";

        try {
            Files.write(Paths.get(outputFilePath), (result + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
