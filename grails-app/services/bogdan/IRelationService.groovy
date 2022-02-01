package bogdan

import com.bogdan.Person

interface IRelationService {

    List<Person> myFollowers(Long personId)

    Person getFollower(Long personId, Long followerId)

    Map<String, Person> follow(Long followerId, Long personId)

    Map<String, Person> unfollow(Long followerId, Long personId)

}