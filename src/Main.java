
import dip.*;
import isp.*;

public class Main {
    public static void main(String[] args) {

        // DIP
        System.out.println("ПРИНЦИП DIP");
        System.out.println("До рефакторинга: NotificationService жестко зависел от EmailSender");
        System.out.println("После рефакторинга: Зависит от абстракции MessageSender\n");

        // Используем EmailSender
        NotificationService emailService = new NotificationService(new EmailSender());
        emailService.send("Ваш заказ готов к выдаче!");

        // Используем SmsSender
        NotificationService smsService = new NotificationService(new SmsSender());
        smsService.send("Ваш код подтверждения: 1234");

        System.out.println("DIP: Можно легко добавлять новые MessageSender без изменения кода NotificationService");

        // ISP (Исправлено)
        System.out.println("\nПРИНЦИП ISP");
        System.out.println("До рефакторинга: Один интерфейс Machine заставлял реализовывать scan() и fax()");
        System.out.println("После рефакторинга: Разделили на Printer, Scanner, Fax\n");

        // Старый принтер - реализует ТОЛЬКО печать
        Printer oldPrinter = new OldPrinter();
        oldPrinter.print("Отчёт за неделю");
        System.out.println("ISP: OldPrinter реализует ТОЛЬКО Printer, не вынужден реализовывать scan/fax");

        // Многофункциональная машина - реализует ВСЕ интерфейсы
        MultiFunctionMachine mfm = new MultiFunctionMachine();
        mfm.print("Важный документ");
        mfm.scan("Фотография");
        mfm.fax("Договор");
        System.out.println("ISP: MultiFunctionMachine может реализовать все интерфейсы, если это нужно");

        System.out.println("\nРЕФАКТОРИНГ ЗАВЕРШЕН");
        System.out.println("DIP: Устранена жесткая зависимость через абстракцию MessageSender");
        System.out.println("ISP: Большой интерфейс разделен на маленькие и специфичные");
    }
}
