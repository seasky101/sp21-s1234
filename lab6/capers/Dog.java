package capers;

import java.io.File;
import java.io.Serializable;

/** Represents a dog that can be serialized.
 * @author Haitian Li
*/
public class Dog implements Serializable {

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = new File(".capers/dogs"); // TODO (hint: look at the `join` function in Utils)

    /** Age of dog. */
    private int age;
    /** Breed of dog. */
    private String breed;
    /** Name of dog. */
    private String name;

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        this.age = age;
        this.breed = breed;
        this.name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        // TODO (hint: look at the Utils file)
        Dog d = Utils.readObject(Utils.join(DOG_FOLDER, name), Dog.class);
        return d;
    }

    /** Increases a dog's age and celebrates! */
    public void haveBirthday() {
        age += 1;
        System.out.println(this);
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /** Saves a dog to a file for future use. */
    public void saveDog() {
        // TODO (hint: don't forget dog names are unique)
        File f = Utils.join(DOG_FOLDER, name);
        Utils.writeObject(f, this);
    }

    @Override
    public String toString() {
        return String.format("Woof! My name is %s and I am a %s! I am %d years old! Woof!", name, breed, age);
    }
}
