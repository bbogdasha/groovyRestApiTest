package com.bogdan

class Follower implements Serializable {

    Person person
    Person follower

    static constraints = {
        person nullable: true
        follower nullable: true
    }

    static mapping = {
        version false
        id composite: ['person', 'follower']
    }
}
