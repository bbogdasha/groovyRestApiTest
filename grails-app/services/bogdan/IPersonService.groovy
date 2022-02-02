package bogdan

import com.bogdan.Person
import com.bogdan.commands.PersonCommand

interface IPersonService {

    List<Person> list()

    Person getOne(Long id)

    void checkExist(Long id)

    void checkExistEmailAndUsername(PersonCommand cmd)

    Person save(PersonCommand cmd)

    Person update(Long id, PersonCommand cmd)

    void delete(Long id)

}