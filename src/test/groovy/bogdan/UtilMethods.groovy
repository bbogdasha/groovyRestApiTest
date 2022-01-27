package bogdan

import com.bogdan.Person
import com.bogdan.commands.PersonCommand

class UtilMethods {

    static Person getPerson() {
        Person person = new Person()
        person.id = 1
        person.firstName = "Carl"
        person.lastName = "Black"
        person.email = "black@gmail.com"
        person.age = 26
        person.username = "user1"
        person.password = "pwd1"
        person
    }

    static PersonCommand getPersonCommand() {
        PersonCommand person = new PersonCommand()
        person.firstName = "Carl"
        person.lastName = "Black"
        person.email = "black@gmail.com"
        person.age = 26
        person.username = "user1"
        person.password = "pwd1"
        person
    }

    static List<Person> getListPerson() {
        List<Person> list = new ArrayList<>()
        for (int i = 0; i < 3; i++) {
            Person person = person
            person.email = "newEmail$i@gmail.com"
            list.add(person)
        }
        list
    }

    static PersonCommand getPersonCommandWithoutFirstName(){
        PersonCommand cmd = personCommand
        cmd.firstName = null
        cmd
    }

    static PersonCommand getPersonCommandWithoutLastName(){
        PersonCommand cmd = personCommand
        cmd.lastName = null
        cmd
    }

    static PersonCommand getPersonCommandWithoutEmail(){
        PersonCommand cmd = personCommand
        cmd.email = null
        cmd
    }

    static PersonCommand getPersonCommandBlankFirstName(){
        PersonCommand cmd = personCommand
        cmd.firstName = ""
        cmd
    }

    static PersonCommand getPersonCommandBlankLastName(){
        PersonCommand cmd = personCommand
        cmd.lastName = ""
        cmd
    }

    static PersonCommand getPersonCommandBlankEmail(){
        PersonCommand cmd = personCommand
        cmd.email = ""
        cmd
    }
}
