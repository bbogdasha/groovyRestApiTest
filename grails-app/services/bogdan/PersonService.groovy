package bogdan

import com.bogdan.Person

interface PersonService {

    List<Person> list()

    Person getOne(def params)

    Person save(def request)

    Person update(def params, def request)

    void delete(def params)

}