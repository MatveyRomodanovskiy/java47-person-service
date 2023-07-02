package telran.java47.person.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.tomcat.util.buf.C2BConverter;
import org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.ActionGrouping;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java47.person.dao.PersonRepository;
import telran.java47.person.dto.AddressDto;
import telran.java47.person.dto.CityPopulationDto;
import telran.java47.person.dto.PersonDto;
import telran.java47.person.dto.exceptions.PersonNotFoundException;
import telran.java47.person.model.Address;
import telran.java47.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findByCity(String city) {
		return personRepository.findByAddressCity(city).stream().map(person -> modelMapper.map(person, PersonDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public Iterable<PersonDto> findByAgeBeetween(Integer ageFrom, Integer ageTo) {
		LocalDate starDate = LocalDate.now().minusYears(ageTo);
		LocalDate endDate = LocalDate.now().minusYears(ageFrom);
		if (endDate.isBefore(starDate)) {
			LocalDate tmpDate = starDate;
			starDate = endDate;
			endDate = tmpDate;
		}
		return personRepository.findByBirthDateBetween(starDate, endDate).stream()
				.map(person -> modelMapper.map(person, PersonDto.class)).collect(Collectors.toList());
	}

	@Override
	public PersonDto updatePerson(Integer id, String newName) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(newName);
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findPersonsByName(String searchName) {
		return personRepository.findPersonsByName(searchName).stream()
				.map(person -> modelMapper.map(person, PersonDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<CityPopulationDto> findCityPopulation() {
		Map<String, Integer> cityPopulationMap = personRepository.findAll().stream()
				.map(person -> person.getAddress().getCity())
				.collect(Collectors.toMap(city -> city, val -> 1, Integer::sum));
		List<CityPopulationDto> cityPopulationDtoList = new ArrayList<>();
		cityPopulationMap.forEach((k, v) -> cityPopulationDtoList.add(new CityPopulationDto(k, v)));
		return cityPopulationDtoList;
	}

	@Override
	public PersonDto updateAddress(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setAddress(modelMapper.map(addressDto, Address.class));
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto deletePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);	
		personRepository.deleteById(id);
		return modelMapper.map(person, PersonDto.class);
	}

}