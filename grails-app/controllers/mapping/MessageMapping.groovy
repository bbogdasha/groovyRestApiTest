package mapping

import com.bogdan.Message

class MessageMapping {

    static Map getData(Message message) {
        Map result = [:]
        result.id = message.id
        result.theme = message.theme
        result.text = message.text
        result.dateCreated = message.dateCreated
        result.lastUpdated = message.lastUpdated
        result.person = [
            id: message.user.id,
            email: message.user.email,
            firstName: message.user.firstName,
            lastName: message.user.lastName
        ]

        return result
    }
}
