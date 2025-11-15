package srp;

import java.util.List;

public class ReportCalculator {
    private final List<Integer> data;

    public ReportCalculator(List<Integer> data) {
        this.data = data;
    }

    public ReportData calculate() {
        int sum = calculateSum();
        double avg = calculateAverage();
        return new ReportData(sum, avg);
    }

    private int calculateSum() {
        int sum = 0;
        for (int n : data) {
            sum += n;
        }
        return sum;
    }

    private double calculateAverage() {
        if (data.isEmpty()) return 0;
        return (double) calculateSum() / data.size();
    }
}