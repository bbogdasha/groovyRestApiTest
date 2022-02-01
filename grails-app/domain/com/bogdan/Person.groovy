package com.bogdan

class Person extends UserSec {
    Long   id
    String firstName
    String lastName
    String email
    int age

    static hasMany = [messages: Message,
                      follower: Person]

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
        follower joinTable: [ name: 'person_relation', column: 'person_id', key: 'relation_person_id']
    }
}
