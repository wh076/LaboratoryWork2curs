package srp;

import java.io.FileWriter;
import java.io.IOException;

public class ReportSaver {
    public void save(ReportData reportData) {
        try (FileWriter writer = new FileWriter("report.txt")) {
            writer.write("REPORT\n");
            writer.write("Sum: " + reportData.getSum() + "\n");
            writer.write("Average: " + reportData.getAverage() + "\n");
            writer.write("Generated at: " + reportData.getGeneratedAt() + "\n");
            System.out.println("Report saved to report.txt");
        } catch (IOException e) {
            System.out.println("Error writing report file: " + e.getMessage());
        }
    }
}