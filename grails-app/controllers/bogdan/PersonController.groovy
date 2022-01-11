package bogdan

import bogdan.impl.PersonService
import com.bogdan.Person
import commands.PersonCommand
import grails.rest.RestfulController
import mapping.PersonMapping
import org.springframework.http.HttpStatus

class PersonController extends RestfulController<Person> {

    PersonService personService
    static responseFormats = ['json', 'xml']

    PersonController() {
        super(Person)
    }

    def index() {
        List<Person> persons = personService.list()
        List<Map> result = new ArrayList<>()
        persons.each {person ->
            result.add(PersonMapping.getData(person))
        }
        respond result
    }

    def show(Long id) {
        Person person = personService.getOne(id)
        respond PersonMapping.getData(person)
    }

    def save(PersonCommand cmd) {
        Person person = personService.save(cmd)
        respond PersonMapping.getData(person)
    }

    def update(Long id, PersonCommand cmd) {
        Person person = personService.update(id, cmd)
        respond PersonMapping.getData(person)
    }

    def delete(Long id) {
        personService.delete(id)
        respond status: HttpStatus.OK
    }
}
