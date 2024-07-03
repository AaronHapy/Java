package Collections;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class UsingSets {

    /*
    * Set allows no duplicates.
    * TreeSet- sorted
    * HashSet - hashing, unordered.
    * LinkedHashSet - hashing, insertion order
    * */

    public static void main(String[] args) {
        //factoryMethods();
        //treeSet();
        //hashSet();
        linkedHashSet();
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
    
    public static void hashSet() {
        // HashSet
        Set<Contact> contacts = new HashSet<>();
        contacts.add(new Contact(26, "Aaron Cerceda"));
        contacts.add(new Contact(26, "Aaron Cerceda")); // Aaron only added once (Set)
        contacts.add(new Contact(22, "William"));
        contacts.add(new Contact(18, "Alice"));

        for(Contact contact : contacts){
            System.out.println(contact);
        }

        System.out.println();

    }

    public static void linkedHashSet() {
        /*
        * LinkedHashSet
        * API: This implementation differs from HashSet in that it maintainsa doubly-linked list
        * running through all of its entries. This linked list defines the iteration ordering, which is the order
        * in which element were inserted into the Set (insertion order).
        * This implementation spares its clients from the unspecified, generally chaotic ordering provided by
        * HashSet, without incurring the increase cost associated with TreeSet
        * */

        Set<Contact> contacts = new LinkedHashSet<>();
        contacts.add(new Contact(45, "zoe"));
        contacts.add(new Contact(45, "zoe")); // zoe only added once (Set)
        contacts.add(new Contact(34, "alice"));
        contacts.add(new Contact(35, "andrew"));

        for(Contact contact : contacts){
            System.out.println(contact);
        }

    }

}
