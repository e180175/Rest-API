package be.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import be.domain.Person;
import be.domain.ResponseCode;
import org.springframework.stereotype.Repository;


@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

	private static List<Person> DB = new ArrayList<>();

	@Override
	public ResponseCode insertPerson(UUID id, Person person) {
		DB.add(new Person(id, person.getName()));
		return ResponseCode.RESPONSE_CODE_OK;
	}

	@Override
	public List<Person> selectAllPeople() {
		return DB;
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
	}

	@Override
	public ResponseCode deletePersonById(UUID id) {
		Optional<Person> personMaybe = selectPersonById(id);
		if (personMaybe.isPresent()) {
			DB.remove(personMaybe.get());
			return ResponseCode.RESPONSE_CODE_OK;
		}
		return ResponseCode.RESPONSE_CODE_NOK;
	}

	@Override
	public ResponseCode updatePersonById(UUID id, Person person) {
		return selectPersonById(id).map(p -> {
			int indexOfPersonToUpdate = DB.indexOf(p);
			if (indexOfPersonToUpdate >= 0) {
				DB.set(indexOfPersonToUpdate, new Person(id, person.getName()));
				return ResponseCode.RESPONSE_CODE_OK;
			}
			return ResponseCode.RESPONSE_CODE_NOK;
		}).orElse(ResponseCode.RESPONSE_CODE_NOK);
	}

}
