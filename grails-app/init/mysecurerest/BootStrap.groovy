package mysecurerest

import com.bogdan.Authority
import com.bogdan.Message
import com.bogdan.Person
import com.bogdan.UserAuthority
import com.bogdan.UserSec

class BootStrap {

    def init = { servletContext ->

        Date date = new Date()

        Person person1 = new Person(firstName: 'Carl', lastName: 'Black', email: 'black@gmail.com', age: 26)
        Person person2 = new Person(firstName: 'Sara', lastName: 'White', email: 'white@gmail.com', age: 21)

        Message message1 = new Message(theme: 'Tesla', text: 'Bla bla bla', dateCreated: date, lastUpdated: null)
        Message message2 = new Message(theme: 'Space', text: 'Bla bla bla', dateCreated: date, lastUpdated: null)
        Message message3 = new Message(theme: 'Movies', text: 'Bla bla bla', dateCreated: date, lastUpdated: null)
        Message message4 = new Message(theme: 'Holiday', text: 'Bla bla bla', dateCreated: date, lastUpdated: null)
        Message message5 = new Message(theme: 'Travel', text: 'Bla bla bla', dateCreated: date, lastUpdated: null)

        person1.addToMessages(message1).save()
        person1.addToMessages(message2).save()
        person1.addToMessages(message3).save()
        person2.addToMessages(message4).save()
        person2.addToMessages(message5).save()

        Authority role1 = new Authority(authority: "ROLE_USER").save()
        UserSec user1 = new UserSec(username: "user1", password: "pwd1").save()
        UserAuthority.create(user1,role1)
    }

    def destroy = {
    }
}
