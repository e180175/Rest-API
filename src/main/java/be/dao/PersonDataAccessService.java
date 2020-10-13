package be.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

@Repository("derby")
public class PersonDataAccessService extends JdbcDaoSupport implements PersonDao{
	
	@Autowired 
	DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public ResponseCode insertPerson(UUID id, Person person) {
		if(person.getName().isEmpty())return ResponseCode.RESPONSE_CODE_NOK;
		try(PreparedStatement stmt = dataSource.getConnection().prepareStatement("INSERT INTO Persons (id,name) VALUES(?,?)")){
			stmt.setString(1,id.toString());
			stmt.setString(2,person.getName());
			if(!stmt.execute()) return ResponseCode.RESPONSE_CODE_OK;
		}catch(SQLException e){
			e.printStackTrace();
			return ResponseCode.RESPONSE_CODE_NOK;
		}
		return ResponseCode.RESPONSE_CODE_NOK;
	}

	@Override
	public List<Person> selectAllPeople() {
		List<Person> personList = new ArrayList<>();

		try(Statement stmt = dataSource.getConnection().createStatement()){
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM Persons");

			UUID id;
			String name;
			while(resultSet.next()){
				id = UUID.fromString(resultSet.getString(1));
				name = resultSet.getString(2);
				personList.add(new Person(id,name));
			}

		}catch(SQLException e){
			e.printStackTrace();
		}

		return personList;
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		try(PreparedStatement stmt = dataSource.getConnection().prepareStatement("SELECT * FROM Persons WHERE id = ?")){
			stmt.setString(1,id.toString());

			ResultSet resultSet = stmt.executeQuery();
			if(resultSet.first()){
				return Optional.of(new Person(id,resultSet.getString(2)));
			}

		}catch(SQLException e){
			e.printStackTrace();
		}

		return Optional.of(new Person(UUID.randomUUID(), "No Match"));
	}

	@Override
	public ResponseCode deletePersonById(UUID id) {
		try(PreparedStatement stmt = dataSource.getConnection().prepareStatement("DELETE FROM Persons WHERE id = ?")){
			stmt.setString(1,id.toString());
			if(!stmt.execute()) return ResponseCode.RESPONSE_CODE_OK;
		}catch(SQLException e){
			e.printStackTrace();
			return ResponseCode.RESPONSE_CODE_NOK;
		}
		return ResponseCode.RESPONSE_CODE_NOK;
	}

	@Override
	public ResponseCode updatePersonById(UUID id, Person person) {
		try(PreparedStatement stmt = dataSource.getConnection().prepareStatement("UPDATE Persons SET name = ? WHERE id = ?")){
			stmt.setString(1,person.getName());
			stmt.setString(2,id.toString());
			if(!stmt.execute()) return ResponseCode.RESPONSE_CODE_OK;
		}catch(SQLException e){
			e.printStackTrace();
			return ResponseCode.RESPONSE_CODE_NOK;
		}
		return ResponseCode.RESPONSE_CODE_NOK;
	}

}
