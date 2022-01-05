package bogdan.impl

import bogdan.MessageService
import com.bogdan.Message
import com.bogdan.Person
import grails.transaction.Transactional
import org.grails.datastore.mapping.query.api.Criteria
import org.grails.web.json.JSONObject

@Transactional
class MessageServiceImpl implements MessageService {

    List<Message> list(def params) {
        def userId = params.userId
        return Message.where { user.id == userId }.list()
    }

    Message getOne(def params) {
        def userId = params.userId
        def messageId = params?.id

        Criteria criteria = Message.createCriteria()
        Message result = criteria.get{
            and {
                eq ('user.id', Long.parseLong(userId))
                eq ('id', Long.parseLong(messageId))
            }
        } as Message

        return result
    }

    Message save(def params, def request) {
        def userId = params.userId
        Person person = Person.get(userId)
        JSONObject messageJson = request.JSON
        Message messageInstance = new Message(messageJson)

        person.addToMessages(messageInstance)

        return messageInstance
    }

    Message update(def params, def request) {
        def messageId = params?.id
        def messageJson = request.JSON
        Message messageInstance = Message.get(messageId)

        messageInstance.properties = messageJson
        messageInstance.lastUpdated = new Date()
        messageInstance = messageInstance.merge()

        return messageInstance
    }

    void delete(def params) {
        def messageId = params?.id
        Message messageInstance = Message.get(messageId)
        messageInstance.delete()
    }
}
