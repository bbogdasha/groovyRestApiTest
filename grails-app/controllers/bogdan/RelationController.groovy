package bogdan

import bogdan.impl.RelationService
import com.bogdan.Person
import com.bogdan.exception.ErrorHandler
import com.bogdan.mapping.PersonMapping
import grails.plugin.springsecurity.annotation.Secured
import grails.web.Controller
import org.springframework.http.HttpStatus

@Controller
class RelationController implements ErrorHandler {

    RelationService relationService

    static responseFormats = ['json', 'xml']

    @Secured(['ROLE_USER'])
    def index(Long userId) {
        List<Person> persons = relationService.myFollowers(userId)
        respond persons.collect {PersonMapping.getData(it)}
    }

    @Secured(['ROLE_USER'])
    def iFollow(Long userId) {
        List<Person> persons = relationService.iFollow(userId)
        respond persons.collect {PersonMapping.getData(it)}
    }

    @Secured(['ROLE_USER'])
    def show(Long userId, Long followerId) {
        Person follower = relationService.getFollower(userId, followerId)
        respond PersonMapping.getData(follower)
    }

    @Secured(['ROLE_USER'])
    def save(Long followerId, Long userId) {
        Map data = relationService.follow(followerId, userId)
        respond status: HttpStatus.OK.value(),
                message: "You (${data.get("person")?.firstName}) follow to ${data.get("follower")?.firstName}."
    }

    @Secured(['ROLE_USER'])
    def delete(Long followerId, Long userId) {
        Map data = relationService.unfollow(followerId, userId)
        respond status: HttpStatus.OK.value(),
                message: "You (${data.get("person")?.firstName}) are no longer follow the ${data.get("follower")?.firstName}"
    }
}
