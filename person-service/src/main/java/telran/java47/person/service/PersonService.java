package telran.java47.person.service;

import java.util.List;
import java.util.Map;

import telran.java47.person.dto.AddressDto;
import telran.java47.person.dto.CityPopulationDto;
import telran.java47.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson(PersonDto personDto);

	PersonDto findPersonById(Integer id) throws ClassNotFoundException, InstantiationException, IllegalAccessException;

	Iterable<PersonDto> findByCity(String city) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
	
	Iterable<PersonDto> findByAgeBeetween(Integer ageFrom, Integer ageTo) throws ClassNotFoundException, InstantiationException, IllegalAccessException;

	PersonDto updatePerson(Integer id, String newName) throws ClassNotFoundException, InstantiationException, IllegalAccessException;

	Iterable<PersonDto> findPersonsByName(String searchName) throws ClassNotFoundException, InstantiationException, IllegalAccessException;

	 List<CityPopulationDto> findCityPopulation();

	PersonDto updateAddress(Integer id, AddressDto addressDto) throws ClassNotFoundException, InstantiationException, IllegalAccessException;

	PersonDto deletePerson(Integer id) throws ClassNotFoundException, InstantiationException, IllegalAccessException;



}