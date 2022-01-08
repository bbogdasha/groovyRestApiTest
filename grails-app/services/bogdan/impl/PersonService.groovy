package bogdan.impl

import bogdan.IPersonService
import com.bogdan.Person
import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class PersonService implements IPersonService {

    List<Person> list() {
        return Person.findAll()
    }

    Person getOne(def params) {
        return Person.findById(params?.id)
    }

    Person save(def request) {
        JSONObject personJson = request.JSON
        Person personInstance = new Person(personJson)

        personInstance = personInstance.save()

        return personInstance
    }

    Person update(def params, def request) {
        def personJson = request.JSON
        Person personInstance = Person.get(params?.id)

        personInstance.properties = personJson
        personInstance = personInstance.merge()

        return personInstance
    }

    void delete(def params) {
        Person personInstance = Person.get(params?.id)
        personInstance.delete()
    }
}
