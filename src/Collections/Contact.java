package Collections;

/*
*
* Hashing is about using memory more efficiently e.g. making searching faster.
* There are 2 steps:
*   1.- Find the bucket using hashCode();
*   2.- Find the object using equals();
*
* Thus, hashCode() and equals() are linked when using hash based collections. Therefore,
* if you are using a collection with "hash"in its name and you override hashCode, you must
* override equals() also (and vice versa).
*
* */

public class Contact {
    private int age;
    private String name;

    public Contact(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Contact) {
            Contact otherContact = (Contact) o;
            return this.name.equals(otherContact.name) && this.age == otherContact.age;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.age;
        hash = 89 * hash + this.name.length();
        return hash;
    }

    @Override
    public String toString() {
        return name + ", " + age;
    }
}
