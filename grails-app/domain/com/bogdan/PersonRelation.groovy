package com.bogdan

class PersonRelation implements Serializable {

    Person person
    Person subscribedTo

    static int removeAll(Person p) {
        p == null ? 0 : where { person == p }.deleteAll()
    }

    static constraints = {

    }

    static mapping = {
        version false
        id composite: ['person', 'subscribedTo']
        table 'person_relation'
        subscribedTo column:'relation_person_id'
    }
}
