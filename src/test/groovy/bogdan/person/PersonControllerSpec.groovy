package bogdan.person

import bogdan.PersonController
import bogdan.UtilMethods
import bogdan.impl.PersonService
import com.bogdan.Person
import com.bogdan.UserAuthority
import com.bogdan.commands.PersonCommand
import com.bogdan.exception.BadRequestProjectException
import com.bogdan.exception.NotFoundProjectException
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(PersonController)
@Mock(UserAuthority)
class PersonControllerSpec extends Specification {

    private static final String PERSON_NOT_FOUND = "Person with id: %d is not found."

    private static final String BAD_REQUEST = "The field: %s - must be filled out and comply with the rules."

    private static final String BAD_REQUEST_EXIST_PERSON = "Person with email: %s - already exists."

    private static final Map NOT_FOUND_RESPONSE = [
            status: 404,
            message: "Not Found",
            details: [String.format(PERSON_NOT_FOUND, 1)]
    ]

    void 'Test show controller method of valid person'() {
        given:
        Person person = UtilMethods.person

        and:
        controller.personService = Mock(PersonService)

        when:
        controller.show(1)

        then:
        response.status == 200
        1 * controller.personService.getOne(1) >> person
        [id : 1,
        firstName : "Carl",
        lastName : "Black",
        email : "black@gmail.com",
        age : 26,
        roles : []] == response.json
    }

    void 'Test show controller method of not exists person'() {
        given:
        controller.personService = Mock(PersonService)

        when:
        controller.show(1)

        then:
        response.status == 404
        1 * controller.personService.getOne(1) >> {
            throw new NotFoundProjectException(String.format(PERSON_NOT_FOUND, 1))}
        response.json == NOT_FOUND_RESPONSE
    }

    void 'Test delete controller method of person'() {
        given:
        Map data = [
                status: 200,
                message: "Deleted"
        ]

        and:
        controller.personService = Mock(PersonService)

        when:
        controller.delete(1)

        then:
        response.status == 200
        1 * controller.personService.delete(1)
        response.json == data
    }

    void 'Test delete controller method of not exists person'() {
        given:
        controller.personService = Mock(PersonService)

        when:
        controller.delete(1)

        then:
        response.status == 404
        1 * controller.personService.delete(1) >> {
            throw new NotFoundProjectException(String.format(PERSON_NOT_FOUND, 1))}
        response.json == NOT_FOUND_RESPONSE
    }

    void 'Test index controller method for get list persons'() {
        given:
        List<Person> list = UtilMethods.getListPerson()

        and:
        controller.personService = Mock(PersonService)

        when:
        controller.index()

        then:
        response.status == 200
        1 * controller.personService.list() >> list
        List<Map> result = new ArrayList<>()
        list.each {p ->
            result.add([id : 1,
                        firstName : "Carl",
                        lastName : "Black",
                        email : "newEmail0@gmail.com",
                        age : 26,
                        roles : []])
        }
        response.json == result
    }

    void 'Test update controller method of valid person'() {
        given:
        Person oldPerson = UtilMethods.person
        PersonCommand cmd = UtilMethods.personCommand

        Person newPerson = oldPerson
        newPerson.email = "updated@gmail.com"

        and:
        controller.personService = Mock(PersonService)

        when:
        controller.update(1, cmd)

        then:
        response.status == 200
        1 * controller.personService.update(1, cmd) >> newPerson
        [id : 1,
         firstName : "Carl",
         lastName : "Black",
         email : "updated@gmail.com",
         age : 26,
         roles : []] == response.json
    }

    void 'Test update controller method of not exists person'() {
        given:
        PersonCommand cmd = UtilMethods.personCommand

        and:
        controller.personService = Mock(PersonService)

        when:
        controller.update(1, cmd)

        then:
        response.status == 404
        1 * controller.personService.update(1, cmd) >> {
            throw new NotFoundProjectException(String.format(PERSON_NOT_FOUND, 1))}
        response.json == NOT_FOUND_RESPONSE
    }

    void 'Test update controller method of exists person by email'() {
        given:
        PersonCommand cmd = UtilMethods.personCommand

        Map data = [
                status: 400,
                message: "Bad Request",
                details: [String.format(BAD_REQUEST_EXIST_PERSON, cmd.email)]
        ]

        and:
        controller.personService = Mock(PersonService)

        when:
        controller.update(1, cmd)

        then:
        response.status == 400
        1 * controller.personService.update(1, cmd) >> {
            throw new BadRequestProjectException(String.format(BAD_REQUEST_EXIST_PERSON, cmd.email))}
        response.json == data
    }

    @Unroll
    void "Test update controller method of invalid person: #testCase"() {
        given:
        PersonCommand cmd = cmdParam

        and:
        controller.personService = Mock(PersonService)

        when:
        cmd.validate()
        controller.update(1, cmd)

        then:
        response.status == 400
        1 * controller.personService.update(1, cmdParam) >> {
            throw new BadRequestProjectException(String.format(BAD_REQUEST, cmd.errors.fieldError.field))}
        response.json == result

        where:
        cmdParam                           | result                                  | testCase
        UtilMethods.personCommandWithoutFirstName | getBadRequestResponse("firstName") | 'person without first name'
        UtilMethods.personCommandWithoutLastName  | getBadRequestResponse("lastName")  | 'person without last name'
        UtilMethods.personCommandWithoutEmail     | getBadRequestResponse("email")     | 'person without email'
        UtilMethods.personCommandBlankFirstName   | getBadRequestResponse("firstName") | 'person with empty first name'
        UtilMethods.personCommandBlankLastName    | getBadRequestResponse("lastName")  | 'person with empty last name'
        UtilMethods.personCommandBlankEmail       | getBadRequestResponse("email")     | 'person with empty email'
    }

    private Map getBadRequestResponse(String field) {
        Map BAD_REQUEST_RESPONSE = [
                status: 400,
                message: "Bad Request",
                details: [String.format(BAD_REQUEST, field)]
        ]
        BAD_REQUEST_RESPONSE
    }
}
