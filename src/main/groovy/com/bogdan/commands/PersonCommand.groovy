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
        firstName blank: false, nullable: false
        lastName blank: false, nullable: false
        email email: true, blank: false, unique: true
        age nullable: true
        password blank: false, password: true
        username blank: false, unique: true
    }
}
