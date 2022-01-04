package bogdan

import com.bogdan.Person
import grails.rest.RestfulController
import org.springframework.http.HttpStatus

class PersonController extends RestfulController<Person> {

    PersonService personService
    static responseFormats = ['json', 'xml']

    PersonController() {
        super(Person)
    }

    @Override
    def index() {
        List<Person> persons = personService.list()
        respond persons
    }

    @Override
    def show() {
        Person person = personService.getOne(params)
        respond person
    }

    @Override
    def save() {
        Person person = personService.save(request)
        respond person
    }

    @Override
    def update() {
        Person person = personService.update(params, request)
        respond person
    }

    @Override
    def delete() {
        personService.delete(params)
        respond status: HttpStatus.OK
    }
}
