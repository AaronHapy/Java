package Collections;

import java.util.*;

public class UsingLists {

    /*
    * Collections have four basic flavours:
    *
    * List - an ordered collection (sequence); provides precise
    * control over access to an element using its integer index; duplicate elements
    * are allowed
    *
    *       - ArrayList: a growable array; fast iteration and fast random access; use
    *           when you are not likely to do much insertion/deletion (Shuffle required).
    *
    *       - LinkedList - elements are doubly-linked to each other; fast insertion/deletion.
    *
    *       - Stack - represents a last-in-first-out (LIFO) stack of objects. The Deque interface
    *           and its implementations are more complete and should be used instead.
    *
    * Set - collections with no duplicated elements.
    *    - HashSet
    *       - unsorted, unordered Set; uses the hashcode of the object being inserted; the more efficient
    * your hashCode() implementation, the better access performance we will get.
    *
    *       - Use this class when you want a collection with no duplicates and you don't care about order when
    * you iterate through it.
    * */

    public static void main(String[] args) {
        //factoryMethods();

        //arrayList();

        stack();
    }

    public static void factoryMethods() {
        String[] array = new String[]{"Alpha", "Beta", "Charlie"};
        java.util.List<String> asList = Arrays.asList(array); // 'array' and 'asList' are now 'backed'
        java.util.List<String> of = java.util.List.of(array);
        java.util.List<String> copy = java.util.List.copyOf(asList);

        array[0] = "Delta";
        System.out.println(Arrays.toString(array));
        System.out.println(asList);

        asList.set(1, "Echo"); // Changes to the list 'write through' to the array
        System.out.println(Arrays.toString(array)); // Delta, Echo, Charlie
        System.out.println(asList); // Delta, Echo, Charlie

    }

    public static void arrayList() {
        java.util.List<String> list = new ArrayList<>();
        list.add("Alan");
        list.add("Alan");
        list.add(1, "Sean");
        list.add("Mary");
        list.add("Mary");

        System.out.println(list); // Alan, Sean, Alan, Mary, Mary
        System.out.println(list.get(1)); // Sean

        list.remove(0); // results in [Sean, Alan, Mary, Mary]
        list.remove("Mary"); // Only first Mary is removed: [Sean, Alan, Mary]
        System.out.println(list); // [Sean, Alan, Mary]
        list.set(0, "jack"); // [Jack, Alan, Mary]

    }

    public static void stack() {
        /*
        * Stack is a LIFO structure (Last in First Out) - we can manipulate one end only.
        * using the stack type as the reference type so we get access to the push, pop, and peek methods.
        * */


        Stack<String> stack = new Stack<>(); // legacy class, use Deque interface instead
        stack.push("Andrea");
        stack.push("Barbara");
        stack.push("Caroline");
        System.out.println(stack); // [Andrea, Barbara, Caroline]

        System.out.println("Top of stack: " + stack.peek()); // Caroline
        System.out.println("Popped: " + stack.pop()); // Caroline - [Andrea, Barbara]
        stack.push("Helen"); // [Andrea, Barbara, Helen]
        System.out.println(stack);



    }

    public static void linkedList() {
        // A doubly-linked list. we can manipulate both ends.
        LinkedList<String> names = new LinkedList<>();
        names.add("Colin"); // [Colin]
        names.add("David"); // [Colin, David]
        names.addFirst("Brian"); // [Brian, Colin, David]
        names.addLast("Edward"); // [Brian, Colin, David, Edward]
        System.out.println(names); // [Brian, Colin, David, Edward]

        names.remove("David"); // [Brian, Colin, Edward]
        names.removeFirst(); // [colin, Edward]
        names.removeLast(); // [colin]
        System.out.println(names); // [colin]
    }

}
