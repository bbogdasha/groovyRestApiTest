package com.bogdan

class PersonRelation implements Serializable {

    Person person
    Person follower

    static constraints = {

    }

    static mapping = {
        version false
        id composite: ['person', 'follower']
    }
}
