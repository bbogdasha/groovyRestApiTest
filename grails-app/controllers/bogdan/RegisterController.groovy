package bogdan

import bogdan.impl.PersonService
import com.bogdan.Person
import com.bogdan.commands.PersonCommand
import com.bogdan.exception.ErrorHandler
import grails.plugin.springsecurity.annotation.Secured
import grails.web.Controller
import com.bogdan.mapping.PersonMapping

@Controller
@Secured(['permitAll'])
class RegisterController implements ErrorHandler {

    PersonService personService

    static responseFormats = ['json', 'xml']

    def register(PersonCommand cmd) {
        Person person = personService.save(cmd)
        respond PersonMapping.getData(person)
    }
}
