package com.bogdan

import grails.rest.Resource

@Resource(uri='/api/users/**')
class Person {
    Long   id
    String firstName
    String lastName
    String email
    int age

    static hasMany = [messages: Message]

    static constraints = {
        email email: true, blank: false, unique: true
        firstName blank: false, nullable: false
        lastName blank: false, nullable: false
        age nullable: true
        messages nullable: true
    }

    static mapping = {
        version false
    }
}
