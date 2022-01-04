package bogdan

import com.bogdan.Message
import grails.rest.RestfulController
import org.springframework.http.HttpStatus

class MessageController extends RestfulController<Message> {

    def messageService
    static responseFormats = ['json', 'xml']

    MessageController() {
        super(Message)
    }

    @Override
    def index() {
        List<Message> messages = messageService.list(params)
        respond messages
    }

    @Override
    def show() {
        Message message = messageService.getOne(params)
        respond message
    }

    @Override
    def save() {
        Message message = messageService.save(params, request)
        respond message
    }

    @Override
    def update() {
        Message message = messageService.update(params, request)
        respond message
    }

    @Override
    def delete() {
        messageService.delete(params)
        respond status: HttpStatus.OK
    }
}
