package com.bogdan.converters

import com.bogdan.Message
import com.bogdan.commands.MessageCommand

class CommandToMessage {

    static Message converter(MessageCommand cmd, Message message){
        message.theme = cmd.theme
        message.text = cmd.text

        return message
    }
}
