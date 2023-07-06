package telran.java47.personinheritance.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import telran.java47.personinheritance.model.Person;

public interface PersonRepositoriy extends JpaRepository<Person, Integer>{


}
