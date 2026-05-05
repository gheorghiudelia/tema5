package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class Main {

    public static void main(String[] args) throws Exception {
        ex1();
        ex2();
        ex3();
        ex4();
        ex5();
        ex6();
        ex7();
        ex8();
        ex9();
        ex10();
        ex11();
    }

    public static void ex1() {
        System.out.println("Ex 1 -  Class Information");

        Class<?> clazz = Student.class;

        System.out.println("Class name: " + clazz.getName());
        System.out.println("Simple name: " + clazz.getSimpleName());
        System.out.println("Package name: " + clazz.getPackageName());
        System.out.println("Superclass: " + clazz.getSuperclass().getName());

        System.out.println("Implemented interfaces:");
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> i : interfaces) {
            System.out.println("  - " + i.getName());
        }
        System.out.println();
    }

    public static void ex2()
    {
        System.out.println("Ex 2 -  All Fields");

        Class<?> clazz = Student.class;
        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            String modifiers = Modifier.toString(f.getModifiers());
            String type = f.getType().getSimpleName();
            String name = f.getName();
            System.out.println("  " + modifiers + " " + type + " " + name);
        }
        System.out.println();
    }
    public static void ex3() {
        System.out.println("Ex 3 - All Methods");
        Class<?> clazz = Student.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            String modifiers = Modifier.toString(m.getModifiers());
            String returnType = m.getReturnType().getSimpleName();
            String name = m.getName();
            StringBuilder params = new StringBuilder();
            Parameter[] parameters = m.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                if (i > 0) params.append(", ");
                params.append(parameters[i].getType().getSimpleName());
                params.append(" ");
                params.append(parameters[i].getName());
            }
            System.out.println("  " + modifiers + " " + returnType + " " + name + "(" + params + ")");
        }
        System.out.println();
    }
    public static void ex4() throws Exception {
        System.out.println("Ex 4 - Create object dynamically");
        Class<?> clazz = Student.class;
        Constructor<?> noArgConstructor = clazz.getDeclaredConstructor();
        Object instance = noArgConstructor.newInstance();
        System.out.println("Created instance: " + instance);
        System.out.println();
    }
    public static void ex5() throws Exception {
        System.out.println("Ex 5 - Call public method");
        Student student = new Student("Andrei", 21);
        Method sayHello = Student.class.getMethod("sayHello");
        sayHello.invoke(student);
        Method study = Student.class.getMethod("study", String.class);
        study.invoke(student, "Reflection in Java");
        System.out.println();
    }
    public static void ex6() throws Exception {
        System.out.println("Ex 6 - Access private field");
        Student student = new Student("Maria", 22);
        Field studentIdField = Student.class.getDeclaredField("studentId");
        studentIdField.setAccessible(true); // bypass private
        String currentValue = (String) studentIdField.get(student);
        System.out.println("Current studentId: " + currentValue);
        studentIdField.set(student, "S12345");
        System.out.println("After modification: " + studentIdField.get(student));
        System.out.println("Student object: " + student);
        System.out.println();
    }
    public static void ex7() throws Exception {
        System.out.println("Ex 7 - Invoke private method");
        Student student = new Student("Ion", 23, "S99999", 9.5, "CS");
        Method secret = Student.class.getDeclaredMethod("secretCalculation");
        secret.setAccessible(true); // bypass private
        secret.invoke(student);
        System.out.println();
    }
    public static void ex8() throws Exception {
        System.out.println("Ex 8: Constructor selection");
        Constructor<?> c0 = Student.class.getDeclaredConstructor();
        Object s0 = c0.newInstance();
        System.out.println("No-arg constructor: " + s0);
        Constructor<?> c1 = Student.class.getDeclaredConstructor(String.class);
        Object s1 = c1.newInstance("Elena");
        System.out.println("1-arg constructor:  " + s1);

        Constructor<?> c2 = Student.class.getDeclaredConstructor(String.class, int.class);
        Object s2 = c2.newInstance("Mihai", 20);
        System.out.println("2-arg constructor:  " + s2);
        System.out.println();
    }
    public static void ex9() throws Exception {
        System.out.println("Ex 9 - Object Inspector");

        Student s = new Student("Alex", 24, "S77777", 8.8, "Math");
        inspect(s);
        System.out.println();
    }
    public static void inspect(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        System.out.println("Inspecting object of class: " + clazz.getSimpleName());

        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            Field[] fields = current.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Object value = f.get(obj);
                System.out.println("  " + current.getSimpleName()
                        + "." + f.getName() + " = " + value);
            }
            current = current.getSuperclass();
        }
    }
    public static void ex10() throws Exception {
        System.out.println("Ex 10 - JSON serializer");

        Student s = new Student("Ana", 20, "S11111", 9.2, "Physics");
        String json = toJson(s);
        System.out.println(json);
        System.out.println();
    }
    public static String toJson(Object obj) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        Class<?> current = obj.getClass();
        boolean first = true;
        while (current != null && current != Object.class) {
            for (Field f : current.getDeclaredFields()) {
                f.setAccessible(true);
                Object value = f.get(obj);

                if (!first) sb.append(",");
                first = false;

                sb.append("\"").append(f.getName()).append("\":");
                if (value instanceof String) {
                    sb.append("\"").append(value).append("\"");
                } else {
                    sb.append(value);
                }
            }
            current = current.getSuperclass();
        }
        sb.append("}");
        return sb.toString();
    }
    public static void ex11() throws Exception {
        System.out.println("Ex 11 -  CSV mapper");

        String header = "name,age,studentId,gpa,major";
        String row= "Bogdan,25,S22222,8.5,Engineering";

        Student s = fromCsv(Student.class, header, row);
        System.out.println("Object built from CSV: " + s);
        System.out.println();
    }
    public static <T> T fromCsv(Class<T> clazz, String header, String row) throws Exception {
        String[] columns = header.split(",");
        String[] values  = row.split(",");
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T obj = constructor.newInstance();
        for (int i = 0; i < columns.length; i++) {
            String columnName = columns[i].trim();
            String rawValue = values[i].trim();

            Field field = findField(clazz, columnName);
            if (field == null) {
                System.out.println("  (no field matching column: " + columnName + ")");
                continue;
            }
            field.setAccessible(true);
            Class<?> type = field.getType();
            Object converted = convertValue(rawValue, type);
            field.set(obj, converted);
        }
        return obj;
    }

    private static Field findField(Class<?> clazz, String name) {
        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        return null;
    }
    private static Object convertValue(String raw, Class<?> type) {
        if (type == String.class) {
            return raw;
        } else if (type == int.class || type == Integer.class) {
            return Integer.parseInt(raw);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(raw);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(raw);
        } else if (type == long.class || type == Long.class) {
            return Long.parseLong(raw);
        }
        return raw;
    }
}
