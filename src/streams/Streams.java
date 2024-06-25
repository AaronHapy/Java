package streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
