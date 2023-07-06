package telran.java47.personinheritance.dto;

import lombok.Getter;

@Getter
public class EmployeeDto extends PersonDto{

	String company;
	int salary;
}