package bogdan.impl

import bogdan.IFollowerService
import com.bogdan.Follower
import com.bogdan.Person
import com.bogdan.exception.BadRequestProjectException
import com.bogdan.exception.NotFoundProjectException
import grails.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class FollowerService implements IFollowerService {

    private static final String BAD_REQUEST = "You can't %s yourself."

    private static final String BAD_REQUEST_EXIST_PERSON = "You are already following to %s."

    private static final String FOLLOWER_NOT_FOUND = "Follower with id: %d is not found."

    PersonService personService

    List<Person> myFollowers(Long userId) {
        Person person = personService.getOne(userId)
        return person.follower.person
    }

    Person getFollower(Long userId, Long followerId) {
        List<Person> followers = myFollowers(userId)
        Person follower = followers.stream().filter({it.id == followerId}).findAny().orElse(null)
        if (follower == null) {
            throw new NotFoundProjectException(String.format(FOLLOWER_NOT_FOUND, followerId))
        }
        return follower
    }

    Person existFollower(Long followerId, Long userId) {
        List<Person> followers = this.myFollowers(userId)
        Person follower = followers.stream().filter({it.id == followerId}).findAny().orElse(null)
        return follower
    }

    Map<String, Person> following(Long followerId, Long userId) {

        Person follower = personService.getOne(followerId)
        Person person = personService.getOne(userId)

        if (existFollower(followerId, userId) != follower) {
            if (followerId != userId) {
                Follower follow = new Follower(person: follower, follower: person)
                follow.save()

                Map<String, Person> result = [person: follower, follower: person]
                return result
            } else {
                throw new BadRequestProjectException(String.format(BAD_REQUEST, "follow"))
            }
        } else {
            throw new BadRequestProjectException(String.format(BAD_REQUEST_EXIST_PERSON, person.firstName))
        }
    }

    Map<String, Person> delete(Long followerId, Long userId) {

        Person person = personService.getOne(followerId)
        Person follower = personService.getOne(userId)

        //if (existFollower(followerId, userId) != null) {
            if (followerId != userId) {
                Follower follow = Follower.findByPersonAndFollower(person, follower)
                follow.delete()

                Map<String, Person> result = [person: follower, follower: person]
                return result
            } else {
                throw new BadRequestProjectException(String.format(BAD_REQUEST, "delete"))
            }
        }
//        else {
//            throw new NotFoundProjectException(String.format(FOLLOWER_NOT_FOUND, follower.id))
//        }
//    }
}