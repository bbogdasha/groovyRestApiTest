package com.bogdan

class Person extends UserSec {
    Long   id
    String firstName
    String lastName
    String email
    int age

    static hasMany = [messages: Message,
                      subscribedTo: Person]

    static constraints = {
        email email: true, blank: false, unique: true, nullable: false, size: 5..30
        firstName blank: false, nullable: false, size: 2..30
        lastName blank: false, nullable: false, size: 2..30
        age nullable: true, range: 10..120
        username size: 5..30
        messages nullable: true
    }

    static mapping = {
        table "person"
        version false
        subscribedTo joinTable: [ name: 'person_relation', column: 'person_id', key: 'relation_person_id']
        messages cascade: 'all-delete-orphan'
        subscribedTo cascade: 'all-delete-orphan'
    }
}
