package bogdan

import com.bogdan.Message
import commands.MessageCommand

interface IMessageService {

    List<Message> list(Long userId)

    Message getOne(Long userId, Long id)

    Message save(Long userId, MessageCommand cmd)

    Message update(Long userId, Long id, MessageCommand cmd)

    void delete(Long userId, Long id)

}