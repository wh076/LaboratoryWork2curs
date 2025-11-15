package srp;

import java.util.List;

public class ReportManager {
    private final List<Integer> data;
    private final ReportCalculator calculator;
    private final ReportPrinter printer;
    private final ReportSaver saver;

    public ReportManager(List<Integer> data) {
        this.data = data;
        this.calculator = new ReportCalculator(data);
        this.printer = new ReportPrinter();
        this.saver = new ReportSaver();
    }

    public void generateReport() {
        ReportData reportData = calculator.calculate();
        printer.print(reportData);
        saver.save(reportData);
    }
}