package ru.shulbaeva.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.shulbaeva.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;
@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    //initialization
    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT,"Andey", 10, "a@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT,"Bobby", 20, "b@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT,"Charly", 30, "c@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT,"Dan", 40, "d@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT,"Evan", 50, "e@gmail.com"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id){
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person person){
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setAge(person.getAge());
        personToBeUpdated.setEmail(person.getEmail());
    }

    public void delete(int id){
        people.removeIf(p -> p.getId() == id);
    }
}
