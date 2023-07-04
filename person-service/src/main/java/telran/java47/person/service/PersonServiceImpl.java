package telran.java47.person.service;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;

import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java47.person.dao.PersonRepository;
import telran.java47.person.dto.AddressDto;
import telran.java47.person.dto.CityPopulationDto;
import telran.java47.person.dto.PersonDto;
import telran.java47.person.dto.exceptions.PersonNotFoundException;
import telran.java47.person.model.Address;
import telran.java47.person.model.Child;
import telran.java47.person.model.Employee;
import telran.java47.person.model.Person;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {
	@Value("${address.prefix}")
	String class_prefix;
	@Value("${address.postfix}")
	String class_postfix;
	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}


	@Override
	public PersonDto findPersonById(Integer id) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return createDtoObj(person);

	}

	
	@Override
	@Transactional (readOnly = true)
	public Iterable<PersonDto> findByCity(String city) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		List<Person> persons = personRepository.findByAddressCityIgnoreCase(city)
				.collect(Collectors.toList());
		return createListDto(persons);
	}

	@Override
	@Transactional (readOnly = true)
	public Iterable<PersonDto> findByAgeBeetween(Integer ageFrom, Integer ageTo) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		LocalDate starDate = LocalDate.now().minusYears(ageTo);
		LocalDate endDate = LocalDate.now().minusYears(ageFrom);
		if (endDate.isBefore(starDate)) {
			LocalDate tmpDate = starDate;
			starDate = endDate;
			endDate = tmpDate;
		}
		List<Person> persons = personRepository.findByBirthDateBetween(starDate, endDate)
				.collect(Collectors.toList());	
		return createListDto(persons);
	}

	@Override
	@Transactional
	public PersonDto updatePerson(Integer id, String newName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(newName);
//		personRepository.save(person);
		return createDtoObj(person);
	}

	@Override
	@Transactional (readOnly = true)
	public Iterable<PersonDto> findPersonsByName(String searchName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {	
		List<Person> persons =  personRepository.findPersonsByNameIgnoreCase(searchName)
				.collect(Collectors.toList());
		return createListDto(persons);
	}

	@Override
	public List<CityPopulationDto> findCityPopulation() {
		return	personRepository.getCitiesPopulation();

	}

	@Override
	@Transactional
	public PersonDto updateAddress(Integer id, AddressDto addressDto) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setAddress(modelMapper.map(addressDto, Address.class));
//		personRepository.save(person);
		return createDtoObj(person);
	}

	@Override
	@Transactional
	public PersonDto deletePerson(Integer id) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);	
		personRepository.deleteById(id);
		return createDtoObj(person);
	}

	@Override
	public void run(String... args) throws Exception {
		if (personRepository.count() == 0) {
			Person person = new Person(1000, "John", LocalDate.of(1985,  4, 11), new Address("Tel Aviv", "Ben Gvirol", 87));
			Child child = new Child(2000, "Mosche", LocalDate.of(2018, 7, 5), new Address("Ashkelon", "Bar Kohva", 21), "Shalom");
			Employee employee = new Employee(3000, "Sarah", LocalDate.of(1995, 11, 23), new Address("Rehovot", "Herzl", 7), "Motorola", 20_000);
			personRepository.save(person);
			personRepository.save(child);
			personRepository.save(employee);
		}
		
	}
	
	private PersonDto createDtoObj(Object obj) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String classname = obj.getClass().getSimpleName();
		classname = class_prefix + classname + class_postfix;
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