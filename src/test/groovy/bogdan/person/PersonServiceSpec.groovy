package bogdan.person

import bogdan.UtilMethods
import bogdan.impl.PersonService
import com.bogdan.Authority
import com.bogdan.Person
import com.bogdan.UserAuthority
import com.bogdan.commands.PersonCommand
import com.bogdan.exception.BadRequestProjectException
import com.bogdan.exception.NotFoundProjectException
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(PersonService)
@Mock([Person, Authority, UserAuthority])
class PersonServiceSpec extends Specification {

    private static UtilMethods util = new UtilMethods()

    private static final String PERSON_NOT_FOUND = "Person with id: %d is not found."

    private static final String BAD_REQUEST = "The field: %s - must be filled out and comply with the rules."

    private static final String BAD_REQUEST_EXIST_PERSON = "Person with email: %s - already exists."

    void "Test getOne service method of valid person"() {
        given:
        service.save(util.personCommand)
        Person person = util.person

        when:
        Person result = service.getOne(1)

        then:
        with(result) {
            firstName == person.firstName
            lastName == person.lastName
            email == person.email
            age == person.age
        }

        cleanup:
        service.delete(result.id)
    }

    void "Test getOne service method of not exists person"() {
        when:
        service.getOne(1)

        then:
        Exception exception = thrown(NotFoundProjectException)
        exception.message == String.format(PERSON_NOT_FOUND, 1)
    }

    void "Test save service method of valid person"() {
        given:
        Person person = util.person

        when:
        Person result = service.save(util.personCommand)

        then:
        with(result) {
            firstName == person.firstName
            lastName == person.lastName
            email == person.email
            age == person.age
        }

        cleanup:
        service.delete(result.id)
    }

    void "Test save service method of exists person"() {
        given:
        Person person = service.save(util.personCommand)

        when:
        Person result = service.save(util.personCommand)

        then:
        Exception exception = thrown(BadRequestProjectException)
        exception.message == String.format(BAD_REQUEST_EXIST_PERSON, person.email)
        result == null

        cleanup:
        service.delete(person.id)
    }

    @Unroll
    void "Test save service method of invalid person: #testCase"() {
        given:
        PersonCommand cmd = cmdParam

        when:
        cmd.validate()
        service.save(cmd)

        then:
        Exception exception = thrown(BadRequestProjectException)
        exception.message == String.format(BAD_REQUEST, cmd.errors.fieldError.field)

        where:
        cmdParam                        |   result                                  |   testCase
        util.personCommandWithoutFirstName   |   String.format(BAD_REQUEST, "firstName") |   'person without first name'
        util.personCommandWithoutLastName    |   String.format(BAD_REQUEST, "lastName")  |   'person without last name'
        util.personCommandWithoutEmail       |   String.format(BAD_REQUEST, "email")     |   'person without email'
        util.personCommandBlankFirstName     |   String.format(BAD_REQUEST, "firstName") |   'person with empty first name'
        util.personCommandBlankLastName      |   String.format(BAD_REQUEST, "lastName")  |   'person with empty last name'
        util.personCommandBlankEmail         |   String.format(BAD_REQUEST, "email")     |   'person with empty email name'
    }

    void "Test checkExist service method of not exists person"() {
        when:
        service.checkExist(1)

        then:
        Exception exception = thrown(NotFoundProjectException)
        exception.message == String.format(PERSON_NOT_FOUND, 1)
    }

    void "Test list service method of persons"() {
        given:
        List<Person> list = getListPerson()
        service.save(util.personCommand)

        when:
        List<Person> result = service.list()

        then:
        result.size() == list.size()
        result.get(0) == list.get(0)

        cleanup:
        list.each {person -> service.delete(person.id)}
    }

    void "Test update service method of valid person"() {
        given:
        PersonCommand oldPersonCommand = util.personCommand
        Person person = service.save(oldPersonCommand)
        PersonCommand newPersonCommand = oldPersonCommand
        newPersonCommand.email = "updated@gmail.com"

        when:
        Person result = service.update(person.id, newPersonCommand)

        then:
        with(result) {
            firstName == person.firstName
            lastName == person.lastName
            email == person.email
            age == person.age
        }

        cleanup:
        service.delete(result.id)
    }

    void "Test update service method of not exists person"() {
        given:
        PersonCommand personCommand = util.personCommand

        when:
        service.update(1, personCommand)

        then:
        Exception exception = thrown(NotFoundProjectException)
        exception.message == String.format(PERSON_NOT_FOUND, 1)
    }

    @Unroll
    void "Test update service method of invalid person: #testCase"() {
        given:
        PersonCommand oldPersonCommand = util.personCommand
        Person person = service.save(oldPersonCommand)
        PersonCommand newPersonCommand = cmdParam

        when:
        newPersonCommand.validate()
        service.update(person.id, newPersonCommand)

        then:
        Exception exception = thrown(BadRequestProjectException)
        exception.message == String.format(BAD_REQUEST, newPersonCommand.errors.fieldError.field)

        cleanup:
        service.delete(person.id)

        where:
        cmdParam                        |   result                                  |   testCase
        util.personCommandWithoutFirstName   |   String.format(BAD_REQUEST, "firstName") |   'person without first name'
        util.personCommandWithoutLastName    |   String.format(BAD_REQUEST, "lastName")  |   'person without last name'
        util.personCommandWithoutEmail       |   String.format(BAD_REQUEST, "email")     |   'person without email'
        util.personCommandBlankFirstName     |   String.format(BAD_REQUEST, "firstName") |   'person with empty first name'
        util.personCommandBlankLastName      |   String.format(BAD_REQUEST, "lastName")  |   'person with empty last name'
        util.personCommandBlankEmail         |   String.format(BAD_REQUEST, "email")     |   'person with empty email name'
    }

    void "Test delete service method of person"() {
        given:
        service.save(util.personCommand)

        when:
        service.delete(1)

        then:
        service.list().isEmpty()
    }

    void "Test delete service method of not exists person"() {
        when:
        service.delete(1)

        then:
        Exception exception = thrown(NotFoundProjectException)
        exception.message == String.format(PERSON_NOT_FOUND, 1)
    }

    private List<Person> getListPerson() {
        List<Person> list = new ArrayList<>()
        for (int i = 0; i < 1; i++) {
            Person person = util.person
            person.email = "newEmail$i@gmail.com"
            list.add(person)
        }
        list
    }
}
