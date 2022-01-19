package mapping

import com.bogdan.Person

class PersonMapping {

    static Map getData(Person person) {
        Map result = [:]
        result.id = person.id
        result.firstName = person.firstName
        result.lastName = person.lastName
        result.email = person.email
        result.age = person.age
        result.roles = person.authorities.authority

        return result
    }
}
