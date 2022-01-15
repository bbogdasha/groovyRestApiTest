package com.bogdan.commands

import grails.validation.Validateable

class MessageCommand implements Validateable {

    String theme
    String text

    static constraints = {
        theme blank: false, nullable: false
        text blank: false, nullable: false
    }
}
