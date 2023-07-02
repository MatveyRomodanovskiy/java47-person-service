package telran.java47.person.dao;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java47.person.dto.CityPopulationDto;
import telran.java47.person.dto.PersonDto;
import telran.java47.person.model.Address;
import telran.java47.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
	
	List<Person> findByAddressCity(String city);
	List<Person> findByBirthDateBetween(LocalDate dateFrom, LocalDate dateTo);
	List<Person> findPersonsByName(String searchName);
//	@Query("SELECT CITY, Count(id) AS population GROUP BY CITY")
//	List findCityPopulation();
	

	


}