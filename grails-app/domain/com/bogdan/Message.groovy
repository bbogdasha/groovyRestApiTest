package com.bogdan

class Message {
    Long id
    String theme
    String text
    Date dateCreated = new Date()
    Date lastUpdated = null

    static belongsTo = [user: Person]

    static constraints = {
        theme blank: false, nullable: false
        text blank: false, nullable: false
        dateCreated nullable: false
        lastUpdated nullable: true
    }

    static mapping = {
        version false
        autoTimestamp false
    }
}
