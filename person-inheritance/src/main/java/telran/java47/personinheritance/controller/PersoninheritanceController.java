package telran.java47.personinheritance.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java47.personinheritance.dto.EmployeeDto;
import telran.java47.personinheritance.dto.PersonDto;
import telran.java47.personinheritance.model.Employee;
import telran.java47.personinheritance.service.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersoninheritanceController {
	
	final PersonService personService;

	@PostMapping
	public Boolean addPerson(@RequestBody PersonDto humanDto) throws ClassNotFoundException {
		return personService.addPerson(humanDto);
	}


	@GetMapping("/salary/{from}/{to}")
	public List<Employee> findEmployeeBySalary(@PathVariable Integer from, @PathVariable Integer to) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return personService.findEmployeeBySalary(from, to);
	}
	

	
	@GetMapping("/children")
	public Iterable<PersonDto>  findChildren() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
			return personService.findChildren();
	}
	
	


}
