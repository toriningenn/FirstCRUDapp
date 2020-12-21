package ru.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Bob","bobkek@yandex.com",32));
        people.add(new Person(++PEOPLE_COUNT, "Tom","tomkek@yandex.com",32));
        people.add(new Person(++PEOPLE_COUNT, "Lol","lolkek@yandex.com",21));
        people.add(new Person(++PEOPLE_COUNT, "John","johnkek@yandex.com",54));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person person) {
        Person personToBeUpdated = show(id);
personToBeUpdated.setName(person.getName());
personToBeUpdated.setAge(person.getAge());
personToBeUpdated.setEmail(person.getEmail());
    }

    public void delete(int id) {
        people.removeIf(p -> p.getId()==id);
    }
}
