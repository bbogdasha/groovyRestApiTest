package bogdan

import bogdan.impl.MessageService
import com.bogdan.Message
import commands.MessageCommand
import grails.rest.RestfulController
import mapping.MessageMapping
import org.springframework.http.HttpStatus

class MessageController extends RestfulController<Message> {

    MessageService messageService
    static responseFormats = ['json', 'xml']

    MessageController() {
        super(Message)
    }

    def index(Long userId) {
        List<Message> messages = messageService.list(userId)
        List<Map> result = new ArrayList<>()
        messages.each {message ->
            result.add(MessageMapping.getData(message))
        }
        respond result
    }

    def show(Long userId, Long id, MessageCommand cmd) {
        Message message = messageService.getOne(userId, id, cmd)
        respond MessageMapping.getData(message)
    }

    def save(Long userId, MessageCommand cmd) {
        Message message = messageService.save(userId, cmd)
        respond MessageMapping.getData(message)
    }

    def update(Long id, MessageCommand cmd) {
        Message message = messageService.update(id, cmd)
        respond MessageMapping.getData(message)
    }

    def delete(Long id) {
        messageService.delete(id)
        respond status: HttpStatus.OK
    }
}
