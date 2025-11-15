package isp;

public class OldPrinter implements Printer {
    @Override
    public void print(String document) {
        System.out.println("Печатаю: " + document);
    }
}