package bogdan

import com.bogdan.Person

interface IFollowerService {

    List<Person> myFollowers(Long userId)

    Person getFollower(Long userId, Long followerId)

    Map<String, Person> following(Long followerId, Long userId)

    Map<String, Person> delete(Long followerId, Long userId)

}