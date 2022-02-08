package com.bogdan.commands

import grails.validation.Validateable

class MessageCommand implements Validateable {

    String theme
    String text

    static constraints = {
        theme blank: false, nullable: false, size: 3..30
        text blank: false, nullable: false, size: 3..255
    }
}
