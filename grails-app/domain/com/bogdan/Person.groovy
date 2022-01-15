package com.bogdan

class Person extends UserSec {
    Long   id
    String firstName
    String lastName
    String email
    int age

    static hasMany = [messages: Message]

    static constraints = {
        email email: true, blank: false, unique: true, nullable: false
        firstName blank: false, nullable: false
        lastName blank: false, nullable: false
        age nullable: true
        messages nullable: true
    }

    static mapping = {
        table "person"
        version false
    }
}
