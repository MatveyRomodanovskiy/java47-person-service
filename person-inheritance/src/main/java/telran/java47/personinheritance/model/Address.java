package telran.java47.personinheritance.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.java47.personinheritance.model.Address;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable public class Address implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	String city;
	String street;
	Integer building;
}