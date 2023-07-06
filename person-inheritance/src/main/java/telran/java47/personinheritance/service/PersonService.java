package telran.java47.personinheritance.service;

import java.util.List;

import telran.java47.personinheritance.dto.EmployeeDto;
import telran.java47.personinheritance.dto.PersonDto;
import telran.java47.personinheritance.model.Employee;

public interface PersonService {

	Boolean addPerson(PersonDto personDto) throws ClassNotFoundException;
	
	Iterable<PersonDto> findChildren() throws ClassNotFoundException, InstantiationException, IllegalAccessException;
	
	List<Employee> findEmployeeBySalary(Integer from, Integer to) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
}
