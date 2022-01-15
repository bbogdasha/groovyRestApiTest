package bogdan.impl

import bogdan.IMessageService
import bogdan.converters.CommandToMessage
import com.bogdan.exception.BadRequestProjectException
import com.bogdan.Message
import com.bogdan.exception.NotFoundProjectException
import com.bogdan.Person
import com.bogdan.commands.MessageCommand
import grails.transaction.Transactional

@Transactional
class MessageService implements IMessageService {

    private static final String MESSAGE_NOT_FOUND = "Message with id: %d is not found."

    private static final String BAD_REQUEST = "The field: %s - must be filled out and comply with the rules."

    PersonService personService

    @Transactional(readOnly = true)
    List<Message> list(Long userId) {
        personService.checkExist(userId)
        return Message.where { user.id == userId }.list()
    }

    @Transactional(readOnly = true)
    Message getOne(Long userId, Long id) {
        List<Message> messages = list(userId)
        Message message = messages.stream().filter({it.id == id}).findAny().orElse(null)
        if (message == null) {
            throw new NotFoundProjectException(String.format(MESSAGE_NOT_FOUND, id))
        }
        return message
    }

    Message save(Long userId, MessageCommand cmd) {
        if (cmd.validate()) {
            Message message = new Message()
            CommandToMessage.converter(cmd, message)

            Person person = personService.getOne(userId)
            person.addToMessages(message)

            return message.save()
        } else {
            throw new BadRequestProjectException(String.format(BAD_REQUEST, cmd.errors.fieldError.field))
        }
    }

    Message update(Long userId, Long id, MessageCommand cmd) {
        if (cmd.validate()) {
            Message message = getOne(userId, id)
            CommandToMessage.converter(cmd, message)
            message.lastUpdated = new Date()

            return message.merge()
        } else {
            throw new BadRequestProjectException(String.format(BAD_REQUEST, cmd.errors.fieldError.field))
        }
    }

    void delete(Long userId, Long id) {
        Message message = getOne(userId, id)
        message.delete()
    }
}
