package bogdan.impl

import bogdan.IPersonService
import bogdan.converters.CommandToPerson
import com.bogdan.exception.BadRequestProjectException
import com.bogdan.exception.NotFoundProjectException
import com.bogdan.commands.PersonCommand
import com.bogdan.Person
import grails.transaction.Transactional

@Transactional
class PersonService implements IPersonService {

    private static final String PERSON_NOT_FOUND = "Person with id: %d is not found."

    private static final String BAD_REQUEST = "The field: %s - must be filled out and comply with the rules."

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

    Person save(PersonCommand cmd){
        if (cmd.validate()) {
            Person person = new Person()
            CommandToPerson.converter(cmd, person)
            return person.save()
        } else {
            throw new BadRequestProjectException(String.format(BAD_REQUEST, cmd.errors.fieldError.field))
        }
    }

    Person update(Long id, PersonCommand cmd) {
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
