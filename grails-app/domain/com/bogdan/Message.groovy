package com.bogdan

class Message {
    Long id
    String theme
    String text
    Date dateCreated = new Date()
    Date lastUpdated = null

    static belongsTo = [user: Person]

    static constraints = {
        theme blank: false, nullable: false, size: 3..30
        text blank: false, nullable: false, size: 3..255
        dateCreated nullable: false
        lastUpdated nullable: true
    }

    static mapping = {
        version false
        autoTimestamp false
    }
}
