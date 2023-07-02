package telran.java47.person.service;

import java.util.List;
import java.util.Map;

import telran.java47.person.dto.AddressDto;
import telran.java47.person.dto.CityPopulationDto;
import telran.java47.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson(PersonDto personDto);

	PersonDto findPersonById(Integer id);

	Iterable<PersonDto> findByCity(String city);
	
	Iterable<PersonDto> findByAgeBeetween(Integer ageFrom, Integer ageTo);

	PersonDto updatePerson(Integer id, String newName);

	Iterable<PersonDto> findPersonsByName(String searchName);

	 List<CityPopulationDto> findCityPopulation();

	PersonDto updateAddress(Integer id, AddressDto addressDto);

	PersonDto deletePerson(Integer id);



}