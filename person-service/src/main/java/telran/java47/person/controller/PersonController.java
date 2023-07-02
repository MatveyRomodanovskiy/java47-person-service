package telran.java47.person.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java47.person.dto.AddressDto;
import telran.java47.person.dto.CityPopulationDto;
import telran.java47.person.dto.PersonDto;
import telran.java47.person.model.Address;
import telran.java47.person.service.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

	final PersonService personService;

	@PostMapping
	public Boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}

	@GetMapping("/{id}")
	public PersonDto findPersonById(@PathVariable Integer id) {
		return personService.findPersonById(id);
	}
	
	@GetMapping("/city/{city}")
	public Iterable<PersonDto> findPersonsByCity(@PathVariable String city) {
		return personService.findByCity(city);
	}

	@GetMapping("/ages/{ageFrom}/{ageTo}")
	public Iterable<PersonDto> findPersonsByAge(@PathVariable Integer ageFrom, @PathVariable Integer ageTo) {		
		return personService.findByAgeBeetween(ageFrom, ageTo);
	}
	
	@PutMapping("/{id}/name/{newName}")
	public PersonDto updatePerson(@PathVariable Integer id, @PathVariable String newName) {
		return personService.updatePerson(id, newName);
	}
	
	@GetMapping("/name/{searchName}")
	public Iterable<PersonDto> findPersonsByName(@PathVariable String searchName) {		
		return personService.findPersonsByName(searchName);
	}
	@GetMapping("/population/city")
	public  List<CityPopulationDto> findCityPopulation() {		
		return personService.findCityPopulation();
	}
	
	@PutMapping("/{id}/address")
	public PersonDto updateAddress(@PathVariable Integer id, @RequestBody AddressDto addressDto) {
		return personService.updateAddress(id, addressDto);
	}
	
	@DeleteMapping("/{id}")
	public PersonDto deletePerson(@PathVariable Integer id) {
		return personService.deletePerson(id);
	}
}