package lambdas;

import java.time.LocalTime;
import java.util.*;
import java.util.function.*;

interface Evaluate<T>{
    boolean isNegative(T t); //similar to Predicate
}

public class TestPredicate {

    String fullName = "";

    public void predicate() {
        Predicate<String> pStr = s -> s.contains("City");
        System.out.println(pStr.test("Vatican City"));

        BiPredicate<String, Integer> checkLength = (str, length) -> str.length() == length;
        System.out.println(checkLength.test("Vatican City", 8));
    }

    public void supplier() {
        //Interface: Supplier<T>         Functional method: T get()       used for: when you wanto to supply values without any input
        Supplier<StringBuilder> supSB = () -> new StringBuilder();
        System.out.println("Supplier SB: " + supSB.get().append("SK"));

        Supplier<LocalTime> supTime = () -> LocalTime.now();
        System.out.println("Supplier time: " + supTime.get());

        Supplier<Double> sRandom = () -> Math.random();
        System.out.println(sRandom.get());
    }

    public void consumer() {
        /*
        * Interface: Consumer<T> and BiConsumer<T>
        * Functional method: void accept(T t) and void accept (T t, U u)
        * Used for: Use the parameter but not interested in the return value
        * */

        // Consumer
        Consumer<String> printC = s -> System.out.println(s); // lambda
        printC.accept("I need to go the restroom");

        List<String> names = new ArrayList<>();
        names.add("Drew"); names.add("Mary");
        names.forEach(printC);

        // BiConsumer
        var mapCapitalCities = new HashMap<String, String>();
        BiConsumer<String, String> biCon = (key, value) -> mapCapitalCities.put(key, value);
        biCon.accept("Dublin", "Ireland");
        biCon.accept("Washington D.C.", "USA");
        System.out.println(mapCapitalCities);

        BiConsumer<String, String> mapPrint = (key, value) -> System.out.println(key + " is the capital of: " + value);

        mapCapitalCities.forEach(mapPrint);

    }

    public void function() {
        /*
        *
        * Interface: Function<T, R> and BiFunction<T, U, R>
        * Functional method: R apply(T t) and R apply(T t, U u)
        * Used for: Transform the input into an output (Types can be different)
        *
        * */


        Function<String, Integer> fn2 = s -> s.length();
        System.out.println("Function: " + fn2.apply("Moscow"));

        BiFunction<String, String, Integer> biFn = (s1, s2) -> s1.length() + s2.length();
        System.out.println("BiFunction: "+ biFn.apply("William", "Shakespeare"));

        BiFunction<String, String, String> biFn2 = (s1, s2) -> s1.concat(s2);
        System.out.println("BiFunction: " + biFn2.apply("William", "Shakespeare"));

    }

    public void unaryBinaryOperator() {
        /*
        * Interface: UnaryOperator<T> and BinaryOperator<T>
        * Functional method: T apply(T t) and T apply(Tt1, T t2)
        * used For: transform the input into an output (types are the same)
        * */

        UnaryOperator<String> unaryOp = name -> "My name is " + name;
        System.out.println("UnaryOperator: " + unaryOp.apply("Mary"));

        BinaryOperator<String> binaryOp = (s1, s2) -> s1.concat(s2);
        System.out.println("Binary operator: " + binaryOp.apply("William", "cerceda"));
    }

    public void boundMethodReference() {
        String name = "Mr. Aaron cerceda";

        Supplier<String> lowerL = () -> name.toLowerCase(); // lambda
        Supplier<String> upperMR = name::toLowerCase; // method reference

        // No need to say which instance to call it on - the supplier is bound to name
        System.out.println(lowerL.get());
        System.out.println(upperMR.get());

        // Predicate <T>
        /*
        * Even though startWidth is overloaded. boolean startsWith(String) and boolean startsWith(String, int), because
        * we are creating a Predicate which has a functional method of test(T t), the startsWith(String) is used.
        * This is where "context" is important!
        * */

        Predicate<String> titleL = (title) -> name.startsWith(title);
        Predicate<String> titleMR = name::startsWith;

        System.out.println(titleL.test("Mr.")); // true
        System.out.println(titleMR.test("Ms.")); // false

    }

    public static void main(String[] args) {
        /*
         *
         * Introduction:
         * - interfaces
         * - functional intefaces
         * - relationship of functional interfaces to lambdas
         *
         * Functional interfaces from the API:
         * - Predicate and BiPredicate
         * - Supplier
         * - Consumer and BiConsumer
         * - Function and BiFunction
         * - UnaryOperator and BinaryOperator
         *
         *  ======= Interfaces =========
         *
         * In General, when you create an interface, you are defining a contract for what
         * a class can do; without saying anything about how the class will do it.
         *
         * A class 'signs' the contract with the keyword implements.
         *
         * When implementing an interface, you are agreeing to adhere (obey) to the contract
         * defined in the interface.
         *
         * If a concrete (non-abstract) class is implementing an interface, the compiler will ensure
         * that the class has implementation code for each abstract method in the interface.
         *
         * - all interfaces methods are implicitly public.
         * - All interfaces methods are implicitly abstract (unless declared as default or static,
         * the new features introducedin Java 8).
         *
         * - All variables declared in an interfaces must be public, static and final i.e. interfaces
         * can only declare constants (not instance variable)
         *
         * - as with abstract classes you cannot new  an interface type but they can be used as references:
         * Printeable p = new Printer(); // Printeable is an interface
         *
         * ========== Functional Interfaces =========
         * a functional interface is an interface that has only abstract method. This is known as the SAM
         * (single abstract method) rule.
         * - default methods do not count.
         * - static methods do not count.
         * - methods inherited from Object do not count.
         *
         *
         * ======= Lambdas =============
         * - a lambda expression is just a block of code that helps in making your code
         * more concise.
         *
         * - A lambda expression only works with functional interfaces.
         * - A lambda expression is an instance of a class that implements a functional interface.
         *
         * - Lambdas look a lot like methods and in some quartes are called "anonymous methods".
         * However, it is an instance with everything but the method stripped away.
         *
         * - A lot can be inferred (By the compiler) from the interface definition (which remember, has only
         * one abstract method). The lambda expression is the instance that implements the interface that has
         * been boiled down to the bare essentials.
         *
         * */

        // Evaluate<T> is a functional interface i.e. one abstract method;
        Evaluate<Integer> lambda = i -> i < 0;
        System.out.println("Evaluate: " + lambda.isNegative(-1));
        System.out.println("Evaluate: " + lambda.isNegative(+1));

        Predicate<Integer> predicate = i -> i < 0;
        System.out.println("Predicate: " + predicate.test(-1));
        System.out.println("Predicate: " + predicate.test(+1));

        int x = 4;
        System.out.println("Is " + x + " even? " + check(4, n -> n % 2 == 0)); // true

        x =7;

        System.out.println("is " + x + " even? " + check(7, n -> n % 2 == 0)); // false

        String name = "Mr. Joe Bloggs";
        System.out.println("Does " + name + " start with Mr. ? " + check("Mr. Joe Bloggs", s -> s.startsWith("Mr.")));

        name = "Ms. Ann Bloggs";
        System.out.println("Does " + name + " start with Mr. ? " + check("Ms. Ann Bloggs", s -> s.startsWith("Mr.")));

        TestPredicate testPredicate = new TestPredicate();

        System.out.println("------------------------------");

        testPredicate.predicate();

        System.out.println("-------------------------");

        testPredicate.supplier();

        System.out.println("-----------------------------");
        testPredicate.consumer();

        System.out.println("-----------------------------");
        testPredicate.function();

        System.out.println("------------------------------");
        testPredicate.unaryBinaryOperator();

        System.out.println("------------------------------");

        ArrayList<String> al = new ArrayList<>();
        al.add("John");

        int z = 12; // final or effectively final

        // Lambdas take a snapshot/picture of local variables; these local variables MUST NOT change. Only setting up lambda here!
        Predicate<String> lambdaFinal = s -> {
            new TestPredicate().fullName = "Kennedy"; // instance/class vars are ok
            System.out.println("z ==" + z);
            return s.isEmpty() && z%2 == 0;
        };

        filterData(al, lambdaFinal);
        System.out.println(al);

        new TestPredicate().fullName = "Sean"; // instance/class vars are ok
        // if z was allowed to change, then the method and the lambda would have 2 different views of 'z'
        filterData(al, lambdaFinal);


        System.out.println("------------------");

        /*
        * Method Reference
        * - Lambda expression help in making your code more concise
        * - Method references, can, in certain situations, help in making your lambda expression
        * even more concise
        *
        * - If all your lambdas expressions does is call one method, then that is an opportunity to use a method
        * reference.
        *
        * - If a lambda parameter is simply passes to another method, then the redundancy of specifying the
        * variable twice can be removed.
        *
        * interface: Consumer <T>
        * functional method: void accept(T t)
        *
        * There are four different styles/types:
        * 1.- Bound - instance known at compile-time
        * 2.- Unbound - instance provided at runtime
        * 3.- Static
        * 4.- Constructor
        *
        * With method references, context is key!
        * - the function interface type being created is hugely important in determining context.
        *
        * */

        List<String> names= Arrays.asList("Sean", "Mary", "John");
        names.forEach(System.out::println); // method reference

        testPredicate.boundMethodReference();

    }

    public static void filterData(List<String> list, Predicate<String> lambda) {
        Iterator<String> i = list.iterator();
        while(i.hasNext()) {
            if(lambda.test(i.next())) { // execution lambda here
                i.remove();
            }
        }
    }

    public static <T> boolean check(T t, Predicate<T> lambda) {
        return lambda.test(t);
    }

}
