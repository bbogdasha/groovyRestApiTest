package bogdan.impl

import bogdan.IMessageService
import bogdan.converters.CommandToMessage
import com.bogdan.Message
import com.bogdan.Person
import commands.MessageCommand
import grails.transaction.Transactional
import org.grails.datastore.mapping.query.api.Criteria

@Transactional
class MessageService implements IMessageService {

    List<Message> list(Long userId) {
        return Message.where { user.id == userId }.list()
    }

    Message getOne(Long userId, Long id, MessageCommand cmd) {
        Criteria criteria = Message.createCriteria()
        Message result = criteria.get{
            and {
                eq ('user.id', userId)
                eq ('id', id)
            }
        } as Message

        return result
    }

    Message save(Long userId, MessageCommand cmd) {
        Message message = new Message()
        CommandToMessage.converter(cmd, message)

        Person person = Person.get(userId)
        person.addToMessages(message)

        return message.save()
    }

    Message update(Long id, MessageCommand cmd) {
        Message message = Message.get(id)
        CommandToMessage.converter(cmd, message)
        message.lastUpdated = new Date()

        return message.merge()
    }

    void delete(Long id) {
        Message messageInstance = Message.get(id)
        messageInstance.delete()
    }
}
