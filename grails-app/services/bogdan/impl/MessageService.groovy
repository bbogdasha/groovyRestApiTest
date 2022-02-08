package bogdan.impl

import bogdan.IMessageService
import com.bogdan.converters.CommandToMessage
import com.bogdan.exception.BadRequestProjectException
import com.bogdan.Message
import com.bogdan.exception.NotFoundProjectException
import com.bogdan.Person
import com.bogdan.commands.MessageCommand
import grails.transaction.Transactional
import org.hibernate.criterion.CriteriaSpecification
import org.springframework.stereotype.Service

@Service
@Transactional
class MessageService implements IMessageService {

    private static final String MESSAGE_NOT_FOUND = "Message with id: %d is not found."

    private static final String BAD_REQUEST = "The field: %s - must be filled out and comply with the rules."

    PersonService personService

    RelationService relationService

    @Transactional(readOnly = true)
    List<Message> list(Long userId) {
        personService.checkExist(userId)
        Message.where { user.id == userId }.list()
    }

    @Transactional(readOnly = true)
    Message getOne(Long userId, Long id) {
        Message message = list(userId).find {it.id == id}
        if (!message) {
            throw new NotFoundProjectException(String.format(MESSAGE_NOT_FOUND, id))
        }
        message
    }

    Message save(Long userId, MessageCommand cmd) {
        if (cmd.validate()) {
            Message message = new Message()
            CommandToMessage.converter(cmd, message)

            Person person = personService.getOne(userId)
            person.addToMessages(message)

            message.save()
        } else {
            throw new BadRequestProjectException(String.format(BAD_REQUEST, cmd.errors.fieldError.field))
        }
    }

    Message update(Long userId, Long id, MessageCommand cmd) {
        if (cmd.validate()) {
            Message message = getOne(userId, id)
            CommandToMessage.converter(cmd, message)
            message.lastUpdated = new Date()

            message.merge()
        } else {
            throw new BadRequestProjectException(String.format(BAD_REQUEST, cmd.errors.fieldError.field))
        }
    }

    void delete(Long userId, Long id) {
        Message message = getOne(userId, id)
        message.delete()
    }

    List<Message> getFeed(Long userId) {
        List<Message> result = list(userId)
        relationService.iFollow(userId).collect {result.addAll(it.messages)}
        result.sort {it.dateCreated}
    }

    List<Map> countMessages() {
        Message.createCriteria().list {
            join "user"
            createAlias 'user', 'usr'
            projections {
                count('id')
                groupProperty('user.id')
                groupProperty('usr.firstName')
                groupProperty('usr.email')
            }
        } as List<Map>
    }

    Map countMessagesPerUser(Long id) {
        Person.createCriteria().get {
            resultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
            eq("id", id)
            join "messages"
            createAlias 'messages', 'msg'
            projections {
                count('msg.id', 'messages')
                groupProperty('id', 'id')
                groupProperty('firstName', 'firstName')
                groupProperty('email', 'email')
            }
        } as Map
    }
}
