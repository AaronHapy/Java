package streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;

public class Streams {

    /*
    *
    * - Like Lambdas and functional interfaces, streams were another new addition in Java 8.
    * - A stream is a sequence of data that can be processed with operations.
    * - Streams are not another way of organising data, like an array or a Collection.
    * Streams do not hold data; streams are all about processing data efficiently.
    *
    * A stream pipeline consists of the operation that run on a stream to produce a result.
    *
    * - Source: where the stream comes from e.g. array, collection or file
    * - Intermediate operations: transforms the stream into another one. There can as few or
    * as many required.
    *
    * - Terminal operation: required to start the whole process and produces the result.
    * Streams can only be used once i.e. streams are no longer usable after a terminal operation
    * completes (re-generate the stream if necessary)
    *
    * - filter() is an intermediate operation and such can filter the stream and pass on the
    * filtered stream to the next operation (another intermediate operation or a terminal
    * operation).
    *
    * - count() and forEach() are both terminal operations that end the stream.
    *
    * - The pipeline operations are the way in which we specify how and in what order
    * we want the data in the source manipulated. Remember, streams don't hold any data.
    *
    *  ===== Streams are Lazy ========
    * The principle of "Lazy" evaluation is that you get what you need only when you need it.
    * for example:, if you were displaying 10,000 records to a user, the principle of lazy
    * evaluation would be to retrieve 50 and while the user is viewing these, retrieve
    * another 50 in the background.
    *
    * - Eager evaluation would be to retrieve all 10,000 records in one go.
    *
    * - With regard to streams, this means that nothing happens until the terminal operation
    * occurs.
    *
    * ==== Creating a Stream fron a File
    *
    * - The Files.lines() method can be used to stream a file. It provides one line at a time
    * from the file as data element in the stream
    *
    * public static Stream<String> lines(Path path) throws IOException
    *
    * - To process the data from the stream, we use the Stream interfaces forEach() method
    * , which is a terminal operation.
    *
    * - Similar to forEach() for collections, it takes a Consumer, which enables us to process
    * each line from the file.
    *
    * */

    public void reduce() {
        /*
        * The reduce() method combines a stream into a single object. It is a reduction,
        * which means it processes all elements. The most common way of doing a reduction
        * is to start with an initial value and keep merging it with the next value;
        *
        *
        * T reduce (T identofy, BinaryOperator<T> accumulator)
        * BinaryOperator<T> functional method: T apply(T, T);
        *
        * The "identity" is the initial value of the reduction and also what is returned if
        * the stream is empty. This means that there will always be a result and thus Optional
        * is not the return type (on this version of reduce()).
        *
        * The "accumulator" combines the current result with the current value in the stream.
        *
        * */

        String name = Stream.of("s", "e", "a", "n")
                .reduce("", (s, c) -> s + c);

        System.out.println(name); // sean

        Integer product = Stream.of(2,3,4)
                .reduce(1, (a,b) -> a * b);

        System.out.println(product); // 24

        /*
        * Use this version when we are dealing with different types, allowing us to create
        * intermediate reductions and then combine them at the end. this is useful when working
        * with parallel streams - the streams can be decomposed and reassembled by separate
        * threads.
        * */

        Stream<String> stream = Stream.of("car", "bus", "train", "aeroplane");
        int length = stream.reduce(0,
                (n, str) -> n + str.length(),
                (n1, n2) -> n1 + n2);

        System.out.println(length); // 20

    }

    private void collection() {
        /*
        * collect() is a very useful method as it lets us get data out of streams and into
        * other formats e.g. Map's. List's and Set's
        *
        *   StringBuilder collect(Supplier<StringBuilder> supplier,
        *       BiConsumer<StringBuilder, String> accumulator
        *       BiConsumer<StringBuilder, StringBuilder> combiner)
        *
        * This version is used when you want complete control over how collecting should work.
        * The accumulator adds an element to the collection e.g.  the next String to the
        * StringBuilder. The combiner takes two collections and merges them. It is useful in
        * parallel processing.
        * */

        StringBuilder word = Stream.of("ad", "jud", "i", "cate")
                .collect(() -> new StringBuilder(), // StringBuilder::new
                        (sb, str) -> sb.append(str), // StringBuilder::append
                        (sb1, sb2) -> sb1.append(sb2)); // StringBuilder::append

        System.out.println(word); // adjudicate

        String s = Stream.of("cake", "biscuits", "apple tart")
                .collect(Collectors.joining(", "));

        System.out.println(s); // cake, biscuits, apple tart

        Double avg = Stream.of("cake", "biscuits", "apple tart")
                .collect(Collectors.averagingInt(str -> str.length()));

        System.out.println(avg); // 7.3333

        /*
        *
        * - Collecting into Maps:
        * two functions required: the first function tells the collector how to create the key;
        * the second function tells the collector how to create the value
        *
        * */

        Map<String, Integer> map = Stream.of("cake", "biscuit", "apple tart")
                .collect(Collectors.toMap(string -> string, // function for the key
                        string -> string.length())); // function for the value

        System.out.println(map);

        /*
        * We want a map: number of characters in dessert name -> dessert name
        * However, 2 of the desserts have the same length (cake and tart) and as length
        * is our key and we can't have duplicates keys, this leads to an exception as Java
        * does not know what to do...
        * IllegalStateException: duplicate key 4 (attempted merging values cake and start
        *
        * to get around this, we can supply a merge function, whereby we append the colliding
        * keys values together.
        * */

        Map<Integer, String> mapNum2 = Stream.of("cake", "biscuits", "apple tart")
                .collect(Collectors.toMap( str2 -> str2.length(), // key is the length
                        str2 -> str2, // value is the string
                        (st1, st2) -> st1 + "," + st2)); // merge function - what to do if we have duplicate keys - append the value

        System.out.println(mapNum2);

    }

    public void groupBy() {
        /*
        * - groupingBy() tells collect() to group all of the elements into a Map
        * - groupingBy() takes a function which determines the keys in the map
        * - Each value is a List of all entries that match that key. The List is a default,
        * which can be changed
        * */

        Stream<String> names = Stream.of("Joe", "Tom", "Tom", "Alan", "Peter");
        Map<Integer, List<String>> map =
                names.collect(
                        Collectors.groupingBy(String::length)
                );
        System.out.println(map);

        /*
        * - What is we wanted to Set instead of a List as the value in the map (to remove the
        * duplication of Tom)?
        *
        * - groupingBy() is overloaded to allow us to pass down "downstream collector". This is a
        * collector that does something special with the values.
        * */

        Stream<String> names2 = Stream.of("Joe", "Tom", "Tom", "Alan", "Peter");
        Map<Integer, Set<String>> map2 =
                names2.collect(
                        Collectors.groupingBy(
                                String::length, // key function
                                Collectors.toSet() // what to do with the values
                        )
                );

        System.out.println(map2);

    }

    public void partitioningBy() {
        /*
        * Partitioning is a special case of grouping where there are only two possible groups - true
        * and false.
        *
        * The keys will be the booleans - true and false
        *
        * */

        Stream<String> names = Stream.of("Thomas", "Teresa", "Mike", "Alan", "Peter");
        Map<Boolean, List<String>> map =
                names.collect(
                        // pass in a predicate
                        Collectors.partitioningBy(s -> s.startsWith("T"))
                );
        System.out.println(map); // {false=[Mike, Alan, Peter], true={Thomas, Teresa}

        Stream<String> names2 = Stream.of("Thomas", "Teresa", "Mike", "Alan", "Peter");
        Map<Boolean, List<String>> map2 =
                names2.collect(
                        // pass in a predicate
                        Collectors.partitioningBy(s2 -> s2.length() > 4)
                );

        System.out.println(map2);// {false=[Mike, Alan], true=[Thomas, Teresa, Peter}}

    }

    public static void main(String[] args) {
        List<String> animalList = Arrays.asList("cat", "dog", "sheep");

        Stream<String> streamAnimals = animalList.stream();
        System.out.println("Number of elements: " + streamAnimals.count()); // 3

        /*
        * stream() is a default method in the Collection interface and therefore is
        * inherited by all classes that implement Collection. Map is NOT one of those i.e.
        * Map is not a Collection. To bridge between the two, we use the Map method entrySet()
        * to return a Set view of the Map (set IS-A Collection)
        * */

        Map<String, Integer> namesToAges = new HashMap<>();
        namesToAges.put("Mike", 22);
        namesToAges.put("Mary", 24);
        namesToAges.put("Alice", 31);

        namesToAges
                .entrySet() // get a Set (i.e. Collection) view of the Map
                .stream() // stream() is a default method in Collection
                .count(); // 3

        /*
        * Stream.of() is a static generically-typed utility method that accepts
        * a varargs parameter and returns an ordered stream of those values.
        * */
        Stream<Integer> stream1 = Stream.of(1,2,3);
        System.out.println(stream1.count());

        List<Cat> cats = loadCats("Cats.txt");
        cats.forEach(System.out::println); // just print the cat

        Streams streamInstance = new Streams();
        streamInstance.reduce();

        streamInstance.collection();

        streamInstance.groupBy();

        streamInstance.partitioningBy();

        /*
        * filter()
        * -Unlike a terminal operation, an intermediate operation produces a stream as a result.
        *
        * distinct() returns a stream with duplicate values removed.
        *   equals() is used i.e. case sensitive.
        *
        * distinct() is a stateful intermediate operation
        *
        * limit() is a short-circuit stateful intermediate operation
        * */

        Stream.of("galway", "mayo", "roscommon")
                .filter(countyName -> countyName.length() > 5)
                .forEach(System.out::println);

        Stream.of("eagle", "eagle", "EAGLE")
                .peek(s -> System.out.println(" 1. " + s))
                .distinct()
                .forEach(System.out::println);

        Stream.of(11, 22, 33, 44, 55, 66, 77, 88, 99)
                .peek(n -> System.out.println(" A - " + n))
                .filter(n -> n > 40)
                .peek(n -> System.out.println(" B - " + n))
                .limit(2)
                .forEach(n -> System.out.println(" C - " + n));

        /*
        *
        * Intermediate Operations
        * map()
        *
        * map() creates a one to one mapping between elements in the stream and elements in the
        * next stage of the stream
        *
        * map() is for transofmr data
        *
        * map(Function<T,R> mapper)
        *
        * */

        Stream.of("book", "pen", "ruler")
                .map(s -> s.length()) // string:length
                .forEach(System.out::print);

        System.out.println("----------------------------------");

        /*
        * flatMap() takes each element in the stream e.g. Stream <List<String>> and makes
        * any elements in contains tio-level elements in a single stream e.g. Stream<String>
        * */

        List<String> list1 = Arrays.asList("sean", "desmond");
        List<String> list2 = Arrays.asList("mary", "ann");
        Stream<List<String>> streamOfLists = Stream.of(list1, list2);

        streamOfLists.flatMap(list -> list.stream())
                .forEach(System.out::println);

        System.out.println("------------------------------");

        Person john = new Person("John", 23);
        Person mary = new Person("mary", 25);

        Stream.of(mary, john)
                .sorted(Comparator.comparing(p -> p.getAge()))
                .forEach(System.out::println);

        Stream.of("Tim", "Jim", "Peter", "Ann", "Mary")
                .peek(name -> System.out.println(" 0." + name)) // Tim, Jim, Peter, Ann, Mary
                .filter(name -> name.length() == 3)
                .peek(name -> System.out.println(" 1." + name)) // Tim, Jim, Ann
                .sorted()
                .peek(name -> System.out.println(" 2." + name))
                .limit(2)
                .forEach(name -> System.out.println(" 3." + name)); // Ann, Jim

        Stream<Integer> nums = Stream.of(1,2,3);
        System.out.println(nums.reduce(0, (n1, n2) -> n1 + n2)); // 6

        IntStream intS = Stream.of(1,2,3)
                .mapToInt(n -> n); // unboxed

        int total = intS.sum();
        System.out.println(total); // 6

        OptionalInt max = IntStream.of(10, 20, 30)
                .max();
        max.ifPresent(System.out::println); // 30

        OptionalDouble min = DoubleStream.of(10.0, 20.0, 30.0)
                .min();

        System.out.println(min.orElseThrow());// 10.0

        OptionalDouble average = LongStream.of(10L, 20L, 30L)
                .average();

        System.out.println(average.orElseGet(() -> Math.random())); // 20

    }

    public static List<Cat> loadCats(String fileName) {
        List<Cat> cats = new ArrayList<>();
        try(Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(line -> {
                String[] catsArray = line.split("/");
                cats.add(new Cat(catsArray[0], catsArray[1]));
            });

        }catch(IOException ioe) {
            ioe.printStackTrace();
        }

        return cats;

        /*
        * NOTE: inside the lambda expression, variables from the enclosing scope are either
        * final or effectively final. This means that while we can add elements to "cats"
        * we cannot chanfe what "cats" refers to i.e. we cannot say cats = new ArrayList
        * */

    }

}
