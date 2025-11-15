package isp;

public class MultiFunctionMachine implements Printer, Scanner, Fax {
    @Override
    public void print(String document) {
        System.out.println("Печатаю: " + document);
    }

    @Override
    public void scan(String document) {
        System.out.println("Сканирую: " + document);
    }

    @Override
    public void fax(String document) {
        System.out.println("Отправляю факс: " + document);
    }
}