package telran.java47.person.dao;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.java47.person.dto.CityPopulationDto;
import telran.java47.person.dto.PersonDto;
import telran.java47.person.model.Address;
import telran.java47.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
	
//	@Query("select p from Person p where p.address.city=:city")
	Stream<Person> findByAddressCityIgnoreCase(@Param("city") String city);
	
	Stream<Person> findByBirthDateBetween(LocalDate dateFrom, LocalDate dateTo);
	
//	@Query ("select p from Person p where p.name=?1")
	Stream<Person> findPersonsByNameIgnoreCase(String searchName);

	@Query("SELECT new telran.java47.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p GROUP BY p.address.city order by count(p)")
	List<CityPopulationDto> getCitiesPopulation();
	
	
	


}