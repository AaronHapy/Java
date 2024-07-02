package Collections;

import java.util.Set;
import java.util.TreeSet;

public class UsingSets {

    public static void main(String[] args) {
        //factoryMethods();
        treeSet();
    }

    public static void factoryMethods(){
        // unmodifiable sets returned
        Set<String> of = Set.of("a", "b", "c");
        Set<String> copy = Set.copyOf(of);

        of.add("d"); // UnsupportedOperationException
        copy.add("d"); //UnsupportedOperationException

        of.remove("a"); // UnsupportedOperationException
    }

    public static void treeSet() {
        // SUU - Sets are Unique and unordered
        Set<String> names = new TreeSet<String>();
        names.add("John");
        names.add("John");
        names.add("Helen");
        names.add("Anne");

        // No duplicates, elements are sorted alpabetically
        System.out.println(names); // [Anne, Helen, John]

        Set<Integer> numbers = new TreeSet<>();
        numbers.add(23);
        numbers.add(Integer.valueOf("21"));
        numbers.add(Integer.valueOf("11"));
        numbers.add(99);

        // No duplicates, elements are sorted numerically
        System.out.println(numbers);
    }

}
