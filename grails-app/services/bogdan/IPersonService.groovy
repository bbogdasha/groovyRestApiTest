package bogdan

import com.bogdan.Person
import com.bogdan.commands.PersonCommand

interface IPersonService {

    List<Person> list()

    Person getOne(Long id)

    Person save(PersonCommand cmd)

    Person update(Long id, PersonCommand cmd)

    void delete(Long id)

}