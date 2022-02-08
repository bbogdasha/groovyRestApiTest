package com.bogdan.commands

import grails.validation.Validateable

class PersonCommand implements Validateable {

    String username
    String password
    String firstName
    String lastName
    String email
    int age

    static constraints = {
        firstName blank: false, nullable: false, size: 2..30
        lastName blank: false, nullable: false, size: 2..30
        email email: true, blank: false, unique: true, size: 5..30
        age nullable: true, range: 10..120
        password blank: false, password: true
        username blank: false, unique: true, size: 5..30
    }
}
