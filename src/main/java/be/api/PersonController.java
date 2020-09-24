package be.api;

import java.util.List;
import java.util.UUID;

import be.domain.Person;
import be.domain.ResponseCode;
import be.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/person")
@RestController
public class PersonController {

	private final PersonService personService;
	private final Logger logger;
	
	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
		this.logger = LoggerFactory.getLogger(PersonController.class);
	}
	
	@PostMapping
	public void addPerson(@RequestBody Person person) {
		if(personService.addPerson(person) == ResponseCode.RESPONSE_CODE_OK){
			logger.info("Adding " + person.getName()+ " to DB ..");
		}else{
			logger.error(person.getName()+ " was not added to DB !");
		}
	}
	
	@GetMapping
	public List<Person> getAllPeople(){
		logger.info("Requesting all people from DB ..");
		return personService.getAllPeople();
	}
	
	@GetMapping(path = "{id}")
	public Person getPersonById(@PathVariable("id") UUID id){
		logger.info("Requesting person with id " + id.toString() + " ..");
		return personService.getPersonById(id).orElse(null);
	}
	
	@DeleteMapping(path = "{id}")
	public void deletePersonById(@PathVariable("id") UUID id) {
		if(personService.deletePerson(id) == ResponseCode.RESPONSE_CODE_OK){
			logger.info("Deleting " + id.toString() + " from DB ..");
		}else{
			logger.error(id.toString() + " was not deleted from DB !");
		}
	}
	
	@PutMapping(path = "{id}")
	public void updatePerson(@PathVariable("id") UUID id, @RequestBody Person person) {
		if(personService.updatePerson(id, person) == ResponseCode.RESPONSE_CODE_OK){
			logger.info("Updating " + id + "name to " + person.getName() + " ..");
		}else{
			logger.error(id+ " was not updated to " + person.getName() + " !");
		}
	}
}
