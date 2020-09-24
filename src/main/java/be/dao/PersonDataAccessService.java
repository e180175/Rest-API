package be.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import be.domain.Person;
import be.domain.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("mySql")
public class PersonDataAccessService extends JdbcDaoSupport implements PersonDao{
	
	@Autowired 
	DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public ResponseCode insertPerson(UUID id, Person person) {
		return ResponseCode.RESPONSE_CODE_NOK;
	}

	@Override
	public List<Person> selectAllPeople() {
		return new ArrayList<>();
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		return Optional.of(new Person(UUID.randomUUID(), "Default Value"));
	}

	@Override
	public ResponseCode deletePersonById(UUID id) {
		return ResponseCode.RESPONSE_CODE_NOK;
	}

	@Override
	public ResponseCode updatePersonById(UUID id, Person person) {
		return ResponseCode.RESPONSE_CODE_NOK;
	}

}
