package com.bogdan

class PersonRelation implements Serializable {

    Person person
    Person subscribedTo

    static constraints = {

    }

    static mapping = {
        version false
        id composite: ['person', 'subscribedTo']
    }
}
