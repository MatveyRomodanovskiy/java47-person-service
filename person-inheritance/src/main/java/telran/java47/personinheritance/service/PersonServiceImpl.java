package telran.java47.personinheritance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.wiring.ClassNameBeanWiringInfoResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


import telran.java47.personinheritance.dao.PersonRepositoriy;
import telran.java47.personinheritance.model.Employee;
import telran.java47.personinheritance.model.Person;
import telran.java47.personinheritance.dto.EmployeeDto;
import telran.java47.personinheritance.dto.PersonDto;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{
	final PersonRepositoriy personRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public Boolean addPerson(PersonDto humanDto) throws ClassNotFoundException {
		if (personRepository.existsById(humanDto.getId())) {
			return false;
		}
		String classname = humanDto.getClass().getSimpleName();
		classname = "telran.java47.personinheritance.model." + classname.substring(0, classname.length()-3);	
		personRepository.save((Person) modelMapper.map(humanDto, Class.forName(classname)));
		return true;
	}

	@Override
	public Iterable<PersonDto> findChildren()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return findbyType("Child");
	}
	
	@Override
	public List<Employee> findEmployeeBySalary(Integer from, Integer to) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		List<Employee> employeess = new ArrayList<>();
		List<Person> persons = personRepository.findAll();
		for (int i=0; i < persons.size(); i++) {
			if (persons.get(i) instanceof Employee) {
				employeess.add((Employee) persons.get(i));
			}		
		}
		return employeess.stream()
				.filter(empl -> (empl.getSalary()>= from && empl.getSalary()<= to))
				.collect(Collectors.toList());
		 
	}
	
	private Iterable<PersonDto> findbyType(String type) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		List<Person> humans =  personRepository.findAll()
				.stream()
				.filter(person -> type.contains(person.getClass().getSimpleName()))
				.collect(Collectors.toList());
		 return createListDto(humans);
	}

	private PersonDto createDtoObj(Object obj) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String classname = obj.getClass().getSimpleName();
		classname = "telran.java47.personinheritance.dto." + classname + "Dto";
		return  (PersonDto) modelMapper.map(obj, Class.forName(classname));	
	}
	
	private Iterable<PersonDto> createListDto(List<Person> persons) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		List<PersonDto> personsDtos = new ArrayList<>();
		for (int i=0; i < persons.size(); i++) {
			personsDtos.add(createDtoObj(persons.get(i)));		
		}
		return personsDtos;
	}

}
