package srp;

import java.time.LocalDateTime;

public class ReportData {
    private final int sum;
    private final double average;
    private final LocalDateTime generatedAt;

    public ReportData(int sum, double average) {
        this.sum = sum;
        this.average = average;
        this.generatedAt = LocalDateTime.now();
    }

    public int getSum() {
        return sum;
    }

    public double getAverage() {
        return average;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
}