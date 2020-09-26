package be.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import be.dao.PersonDao;
import be.domain.ResponseCode;
import be.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;



@Service
public class PersonService {

	private final PersonDao personDao;
	
	@Autowired
	public PersonService(@Qualifier("mySql")PersonDao personDao) {
		this.personDao = personDao;
	}
	
	public ResponseCode addPerson(Person person) {
		return personDao.insertPerson(person);
	}
	
	public List<Person> getAllPeople(){
		return personDao.selectAllPeople();
	}
	
	public Optional<Person> getPersonById(UUID id){
		return personDao.selectPersonById(id);
	}
	
	public ResponseCode deletePerson(UUID id) {
		return personDao.deletePersonById(id);
	}
	
	public ResponseCode updatePerson(UUID id, Person person) {
		return personDao.updatePersonById(id, person);
	}
}
