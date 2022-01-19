package com.bogdan.converters

import com.bogdan.Person
import com.bogdan.commands.PersonCommand

class CommandToPerson {

    static Person converter(PersonCommand cmd, Person person){
        person.password = cmd.password
        person.username = cmd.username
        person.firstName = cmd.firstName
        person.lastName = cmd.lastName
        person.email = cmd.email
        person.age = cmd.age

        return person
    }
}
