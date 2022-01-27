package bogdan

import bogdan.impl.PersonService
import com.bogdan.Person
import com.bogdan.commands.PersonCommand
import com.bogdan.exception.ErrorHandler
import com.bogdan.mapping.PersonMapping
import grails.web.Controller
import org.springframework.http.HttpStatus

@Controller
class PersonController implements ErrorHandler {

    PersonService personService

    static responseFormats = ['json', 'xml']

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

    def update(Long id, PersonCommand cmd) {
        Person person = personService.update(id, cmd)
        respond PersonMapping.getData(person)
    }

    def delete(Long id) {
        personService.delete(id)
        respond status: HttpStatus.OK.value(),
                message: "Deleted"
    }
}
