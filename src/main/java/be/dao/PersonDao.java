package be.dao;

import be.domain.Person;
import be.domain.ResponseCode;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {

	ResponseCode insertPerson(UUID id, Person person);

	default ResponseCode insertPerson(Person person) {
		UUID id = UUID.randomUUID();
		return insertPerson(id, person);
	}
	
	List<Person> selectAllPeople();
	
	Optional<Person> selectPersonById(UUID id);

	ResponseCode deletePersonById(UUID id);

	ResponseCode updatePersonById(UUID id, Person person);
}
