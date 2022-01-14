package bogdan.impl

import bogdan.IPersonService
import bogdan.converters.CommandToPerson
import com.bogdan.NotFoundProjectException
import commands.PersonCommand
import com.bogdan.Person
import grails.transaction.Transactional

@Transactional
class PersonService implements IPersonService {

    private static final String PERSON_NOT_FOUND = "Person with id: %d is not found."

    List<Person> list() {
        return Person.findAll()
    }

    Person getOne(Long id) {
        Person person = Person.findById(id)
        if (person == null) {
            throw new NotFoundProjectException(String.format(PERSON_NOT_FOUND, id))
        }
        return person
    }

    Person save(PersonCommand cmd){
        Person person = new Person()
        CommandToPerson.converter(cmd, person)

        return person.save()
    }

    Person update(Long id, PersonCommand cmd) {
        Person person = getOne(id)
        CommandToPerson.converter(cmd, person)

        return person.merge()
    }

    void delete(Long id) {
        Person person = getOne(id)
        person.delete()
    }
}
