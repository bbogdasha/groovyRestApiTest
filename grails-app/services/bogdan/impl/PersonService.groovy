package bogdan.impl

import bogdan.IPersonService
import bogdan.converters.CommandToPerson
import commands.PersonCommand
import com.bogdan.Person
import grails.transaction.Transactional

@Transactional
class PersonService implements IPersonService {

    List<Person> list() {
        return Person.findAll()
    }

    Person getOne(Long id) {
        return Person.findById(id)
    }

    Person save(PersonCommand cmd){
        Person person = new Person()
        CommandToPerson.converter(cmd, person)

        return person.save()
    }

    Person update(Long id, PersonCommand cmd) {
        Person person = Person.get(id)
        CommandToPerson.converter(cmd, person)

        return person.merge()
    }

    void delete(Long id) {
        Person personInstance = Person.get(id)
        personInstance.delete()
    }
}
