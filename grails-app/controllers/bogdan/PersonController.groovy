package bogdan

import bogdan.impl.MessageService
import bogdan.impl.PersonService
import com.bogdan.Person
import com.bogdan.commands.PersonCommand
import com.bogdan.exception.ErrorHandler

import com.bogdan.mapping.PersonMapping
import grails.plugin.springsecurity.annotation.Secured
import grails.web.Controller
import org.springframework.http.HttpStatus

@Controller
class PersonController implements ErrorHandler {

    PersonService personService

    MessageService messageService

    static responseFormats = ['json', 'xml']

    @Secured(['ROLE_ADMIN'])
    def index() {
        List<Person> persons = personService.list()
        respond persons.collect {PersonMapping.getData(it)}
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def show(Long id) {
        Person person = personService.getOne(id)
        respond PersonMapping.getData(person)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def update(Long id, PersonCommand cmd) {
        Person person = personService.update(id, cmd)
        respond PersonMapping.getData(person)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def delete(Long id) {
        personService.delete(id)
        respond status: HttpStatus.OK.value(),
                message: "Deleted"
    }

    @Secured(['ROLE_ADMIN'])
    def count() {
        List<Map> maps = messageService.countMessages()
        respond maps.collect {
            [id : it[1],
             firstName : it[2],
             email : it[3],
             messages : it[0]]
        }
    }

    @Secured(['ROLE_ADMIN'])
    def countPerUser(Long id) {
        Map map = messageService.countMessagesPerUser(id)
        respond map
    }
}
