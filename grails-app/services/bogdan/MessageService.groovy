package bogdan

import com.bogdan.Message

interface MessageService {

    List<Message> list(def params)

    Message getOne(def params)

    Message save(def params, def request)

    Message update(def params, def request)

    void delete(def params)

}