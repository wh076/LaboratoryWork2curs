import java.lang.reflect.Method;

public class Invoker {
    public static void invokeAnnotatedMethods(MyClass obj) throws Exception {
        Method[] methods = MyClass.class.getDeclaredMethods();

        System.out.println("Всего методов в классе: " + methods.length);
        System.out.println("Поиск аннотированных методов......\n");

        for (Method method : methods) {
            if (method.isAnnotationPresent(Repeat.class)) {
                Repeat annotation = method.getAnnotation(Repeat.class);
                int times = annotation.times();

                System.out.println("Найден метод: " + method.getName());
                System.out.println("Аннотация @Repeat(times = " + times + ")");
                System.out.println("Тип метода: " + getMethodType(method));

                method.setAccessible(true);

                System.out.println("Вызовы:");
                for (int i = 0; i < times; i++) {
                    Object result = invokeMethodWithParams(method, obj);
                    if (result != null) {
                        System.out.println("  Результат: " + result);
                    }
                }
                System.out.println();
            }
        }
    }

    private static String getMethodType(Method method) {
        if (method.toString().contains("private")) return "PRIVATE";
        if (method.toString().contains("protected")) return "PROTECTED";
        return "PUBLIC";
    }

    private static Object invokeMethodWithParams(Method method, MyClass obj) throws Exception {
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] params = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            if (paramTypes[i] == String.class) {
                params[i] = "test";
            } else if (paramTypes[i] == int.class) {
                params[i] = 42;
            } else if (paramTypes[i] == boolean.class) {
                params[i] = true;
            }
        }

        if (paramTypes.length == 0) {
            return method.invoke(obj);
        } else {
            return method.invoke(obj, params);
        }
    }

    public static void main(String[] args) {
        try {
            MyClass myObject = new MyClass();
            invokeAnnotatedMethods(myObject);

            System.out.println("Все аннотированные методы выполнены!");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}