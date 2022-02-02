package bogdan

import bogdan.impl.MessageService
import com.bogdan.Message
import com.bogdan.commands.MessageCommand
import com.bogdan.exception.ErrorHandler
import com.bogdan.mapping.MessageMapping
import grails.web.Controller
import org.springframework.http.HttpStatus

@Controller
class MessageController implements ErrorHandler {

    MessageService messageService

    static responseFormats = ['json', 'xml']

    def index(Long userId) {
        List<Message> messages = messageService.list(userId)
        List<Map> result = new ArrayList<>()
        messages.each {message ->
            result.add(MessageMapping.getData(message))
        }
        respond result
    }

    def show(Long userId, Long id) {
        Message message = messageService.getOne(userId, id)
        respond MessageMapping.getData(message)
    }

    def save(Long userId, MessageCommand cmd) {
        Message message = messageService.save(userId, cmd)
        respond MessageMapping.getData(message)
    }

    def update(Long userId, Long id, MessageCommand cmd) {
        Message message = messageService.update(userId, id, cmd)
        respond MessageMapping.getData(message)
    }

    def delete(Long userId, Long id) {
        messageService.delete(userId, id)
        respond status: HttpStatus.OK.value(),
                message: "Deleted"
    }
}
