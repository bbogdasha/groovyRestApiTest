package bogdan.impl

import bogdan.IPersonService
import com.bogdan.Authority
import com.bogdan.UserAuthority
import com.bogdan.converters.CommandToPerson
import com.bogdan.exception.BadRequestProjectException
import com.bogdan.exception.NotFoundProjectException
import com.bogdan.commands.PersonCommand
import com.bogdan.Person
import grails.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class PersonService implements IPersonService {

    private static final String PERSON_NOT_FOUND = "Person with id: %d is not found."

    private static final String BAD_REQUEST = "The field: %s - must be filled out and comply with the rules."

    private static final String BAD_REQUEST_EMAIL = "Person with email: %s - already exists."

    private static final String BAD_REQUEST_USERNAME = "Person with username: %s - already exists."

    @Transactional(readOnly = true)
    List<Person> list() {
        return Person.findAll()
    }

    @Transactional(readOnly = true)
    Person getOne(Long id) {
        Person person = Person.findById(id)
        if (person == null) {
            throw new NotFoundProjectException(String.format(PERSON_NOT_FOUND, id))
        }
        return person
    }

    @Transactional(readOnly = true)
    void checkExist(Long id) {
        int exist = Person.countById(id)
        if (exist == 0) {
            throw new NotFoundProjectException(String.format(PERSON_NOT_FOUND, id))
        }
    }

    @Transactional(readOnly = true)
    void checkExistEmailAndUsername(PersonCommand cmd) {
        Person existEmail = Person.findByEmail(cmd.email)
        Person existUsername = Person.findByUsername(cmd.username)
        if (existEmail != null) {
            throw new BadRequestProjectException(String.format(BAD_REQUEST_EMAIL, cmd.email))
        }
        if (existUsername != null) {
            throw new BadRequestProjectException(String.format(BAD_REQUEST_USERNAME, cmd.username))
        }
    }

    Person save(PersonCommand cmd){
        checkExistEmailAndUsername(cmd)
        if (cmd.validate()) {
            Person person = new Person()
            CommandToPerson.converter(cmd, person)
            person.save()

            Authority role = Authority.findByAuthority("ROLE_USER")
            UserAuthority.create(person, role)

            return person
        } else {
            throw new BadRequestProjectException(String.format(BAD_REQUEST, cmd.errors.fieldError.field))
        }
    }

    Person update(Long id, PersonCommand cmd) {
        if (getOne(id).email != cmd.email) {
            checkExistEmailAndUsername(cmd)
        }
        if (cmd.validate()) {
            Person person = getOne(id)
            CommandToPerson.converter(cmd, person)
            return person.merge()
        } else {
            throw new BadRequestProjectException(String.format(BAD_REQUEST, cmd.errors.fieldError.field))
        }
    }

    void delete(Long id) {
        Person person = getOne(id)
        person.delete()
    }
}
