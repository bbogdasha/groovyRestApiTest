package bogdan

import bogdan.impl.RelationService
import com.bogdan.Person
import com.bogdan.exception.ErrorHandler
import com.bogdan.mapping.PersonMapping
import grails.web.Controller
import org.springframework.http.HttpStatus

@Controller
class RelationController implements ErrorHandler {

    RelationService relationService

    static responseFormats = ['json', 'xml']

    def index(Long userId) {
        List<Person> persons = relationService.myFollowers(userId)
        List<Map> result = new ArrayList<>()
        persons.each {person ->
            result.add(PersonMapping.getData(person))
        }
        respond result
    }

    def show(Long userId, Long followerId) {
        Person follower = relationService.getFollower(userId, followerId)
        respond PersonMapping.getData(follower)
    }

    def save(Long followerId, Long userId) {
        Map data = relationService.follow(followerId, userId)
        respond status: HttpStatus.OK.value(),
                message: "You (${data.get("person")?.firstName}) follow to ${data.get("follower")?.firstName}."
    }

    def delete(Long followerId, Long userId) {
        Map data = relationService.unfollow(followerId, userId)
        respond status: HttpStatus.OK.value(),
                message: "You (${data.get("person")?.firstName}) are no longer follow the ${data.get("follower")?.firstName}"
    }
}
