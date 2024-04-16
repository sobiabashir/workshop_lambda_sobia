package se.lexicon;


import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;
import se.lexicon.util.PersonGenerator;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       TODO:  1.	Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);
        Predicate<Person> filter = (person) -> person.getFirstName().equals("Erik");
        List<Person> eriksNamesList = storage.findMany(filter);
        eriksNamesList.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {

        List<Person> females = storage.findMany(person -> person.getGender() == Gender.FEMALE);
        for (Person female : females) {
            System.out.println(female);
        }
        System.out.println("----------------------");

    }

    /*
        TODO:  3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);

        List<Person> bornAfter2000 = storage.findMany(person -> person.getBirthDate().isAfter(LocalDate.of(2000, 1, 1)) ||
                person.getBirthDate().isEqual(LocalDate.of(2000, 1, 1)));

        bornAfter2000.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO: 4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
       Person personWithRequiredId = storage.findOne(person -> person.getId() == 123);
        System.out.println(personWithRequiredId);
        System.out.println("----------------------");

    }

    /*
        TODO:  5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);

        Function<Person, String> personToString = (person) -> "Name: " + person.getFirstName() + " " + person.getLastName() +
                " born " + person.getBirthDate();
        System.out.println(storage.findOneAndMapToString(person -> person.getId() == 456, personToString));
        System.out.println("----------------------");
    }

    /*
        TODO:  6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);

        List<String> result = storage.findManyAndMapEachToString((person) -> person.getGender() == Gender.MALE &&
                person.getFirstName().startsWith("E"), person -> person.getFirstName() + " " + person.getLastName());
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);

        Function<Person, String> personToString = person -> person.getFirstName() + " " + person.getLastName() +
                " " + Period.between(person.getBirthDate(), LocalDate.now()).getYears() + " years";
        List<String> result = storage.findManyAndMapEachToString(person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears() < 10, personToString);
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        storage.findAndDo(person -> person.getFirstName().equals("Ulf"), System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);

        storage.findAndDo(person -> person.getLastName().contains(person.getFirstName()), System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> {
            String firstName = person.getFirstName().toLowerCase();
            return firstName.equals(new StringBuilder(firstName).reverse().toString());
        };
        Consumer<Person> consumer = person -> System.out.println(person.getFirstName() + " " + person.getLastName());
        storage.findAndDo(filter, consumer);
        System.out.println("----------------------");
    }

    /*
        TODO:  11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        List<Person> result = storage.findAndSort(person -> person.getFirstName().startsWith("A"), Comparator.comparing(Person::getBirthDate));
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> person.getBirthDate().isBefore(LocalDate.of(1950, 1, 1));
        Comparator<Person> comparator = Comparator.comparing(Person::getBirthDate).reversed();
        List<Person> result = storage.findAndSort(filter, comparator);
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        Comparator<Person> comparator = Comparator.comparing(Person::getLastName)
                .thenComparing(Person::getFirstName)
                .thenComparing(Person::getBirthDate);
        List<Person> result = storage.findAndSort(comparator);
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }
}
