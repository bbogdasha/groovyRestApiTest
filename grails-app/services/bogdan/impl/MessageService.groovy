package bogdan.impl

import bogdan.IMessageService
import bogdan.converters.CommandToMessage
import com.bogdan.Message
import com.bogdan.NotFoundProjectException
import com.bogdan.Person
import commands.MessageCommand
import grails.transaction.Transactional

@Transactional
class MessageService implements IMessageService {

    private static final String MESSAGE_NOT_FOUND = "Message with id: %d is not found."

    private static final String PERSON_NOT_FOUND = "Person with id: %d is not found."

    List<Message> list(Long userId) {
        Person person = Person.findById(userId)
        if (person == null) {
            throw new NotFoundProjectException(String.format(PERSON_NOT_FOUND, userId))
        }
        return Message.where { user.id == userId }.list()
    }

    Message getOne(Long userId, Long id) {
        List<Message> messages = list(userId)
        Message message = messages.stream().filter({it.id == id}).findAny().orElse(null)
        if (message == null) {
            throw new NotFoundProjectException(String.format(MESSAGE_NOT_FOUND, id))
        }
        return message
    }

    Message save(Long userId, MessageCommand cmd) {
        Message message = new Message()
        CommandToMessage.converter(cmd, message)

        Person person = Person.findById(userId)
        if (person == null) {
            throw new NotFoundProjectException(String.format(PERSON_NOT_FOUND, userId))
        }
        person.addToMessages(message)

        return message.save()
    }

    Message update(Long userId, Long id, MessageCommand cmd) {
        Message message = getOne(userId, id)
        CommandToMessage.converter(cmd, message)
        message.lastUpdated = new Date()

        return message.merge()
    }

    void delete(Long userId, Long id) {
        Message messageInstance = getOne(userId, id)
        messageInstance.delete()
    }
}
