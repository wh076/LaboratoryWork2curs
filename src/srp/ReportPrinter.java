package srp;

public class ReportPrinter {
    public void print(ReportData reportData) {
        System.out.println("REPORT");
        System.out.println("Sum: " + reportData.getSum());
        System.out.println("Average: " + reportData.getAverage());
        System.out.println("Generated at: " + reportData.getGeneratedAt());
    }
}