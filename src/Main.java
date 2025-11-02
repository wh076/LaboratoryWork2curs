public class Main {
    public static void main(String[] args) {
        System.out.println("ЧАСТЬ 1: РЕФЛЕКСИЯ И АННОТАЦИИ");
        try {
            MyClass myObject = new MyClass();
            Invoker.invokeAnnotatedMethods(myObject);
        } catch (Exception e) {
            System.out.println("Ошибка в части 1: " + e.getMessage());
        }

        System.out.println("\nЧАСТЬ 2: ФАЙЛОВАЯ СИСТЕМА");
        try {
            FileSystemManager.createTestStructure();
            FileSystemManager.recursiveWalk();
            FileSystemManager.recursiveFileOperations();
        } catch (Exception e) {
            System.out.println("Ошибка в части 2: " + e.getMessage());
        }

        System.out.println("\nВСЕ ЗАДАНИЯ ВЫПОЛНЕНЫ");
    }
}