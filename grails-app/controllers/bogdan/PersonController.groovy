package bogdan

import bogdan.impl.PersonService
import com.bogdan.ErrorController
import com.bogdan.Person
import commands.PersonCommand
import grails.web.Controller
import mapping.PersonMapping
import org.springframework.http.HttpStatus

@Controller
class PersonController extends ErrorController {

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

    def save(PersonCommand cmd) {
        if (cmd.validate()) {
            Person person = personService.save(cmd)
            respond PersonMapping.getData(person)
        } else {
            response.status = 400
            respond status: 400,
                    message: "The field $cmd.errors.fieldError.field must be filled out and comply with the rules!"
        }
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
