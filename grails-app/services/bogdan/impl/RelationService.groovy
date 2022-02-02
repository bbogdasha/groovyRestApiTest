package bogdan.impl

import bogdan.IRelationService
import com.bogdan.Person
import com.bogdan.exception.BadRequestProjectException
import com.bogdan.exception.NotFoundProjectException
import grails.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class RelationService implements IRelationService {

    private static final String BAD_REQUEST = "You can't %s yourself."

    private static final String BAD_REQUEST_EXIST_PERSON = "You are already follow to %s."

    private static final String FOLLOWER_NOT_FOUND = "Follower with id: %d is not found."

    PersonService personService

    List<Person> myFollowers(Long personId) {
        Person person = personService.getOne(personId)
        return person.subscribedTo.toList()
    }

    Person existFollower(Long followerId, Long userId) {
        myFollowers(userId).find { it.id == followerId }
    }

    Person getFollower(Long personId, Long followerId) {
        Person follower = existFollower(followerId, personId)
        if (follower == null) {
            throw new NotFoundProjectException(String.format(FOLLOWER_NOT_FOUND, followerId))
        }
        return follower
    }

    Map<String, Person> follow(Long followerId, Long personId) {
        Person follower = personService.getOne(followerId)
        Person person = personService.getOne(personId)

        if (existFollower(followerId, personId) == follower) {
            throw new BadRequestProjectException(String.format(BAD_REQUEST_EXIST_PERSON, person.firstName))
        }

        if (followerId == personId) {
            throw new BadRequestProjectException(String.format(BAD_REQUEST, "follow"))
        }

        person.addToSubscribedTo(follower)
        person.save()

        return [person: follower, follower: person]
    }

    Map<String, Person> unfollow(Long followerId, Long personId) {
        Person follower = personService.getOne(followerId)
        Person person = personService.getOne(personId)

        if (existFollower(followerId, personId) == null) {
            throw new NotFoundProjectException(String.format(FOLLOWER_NOT_FOUND, person.id))
        }

        if (followerId == personId) {
            throw new BadRequestProjectException(String.format(BAD_REQUEST, "unfollow"))
        }

        person.removeFromSubscribedTo(follower)
        person.save()

        return [person: follower, follower: person]
    }
}