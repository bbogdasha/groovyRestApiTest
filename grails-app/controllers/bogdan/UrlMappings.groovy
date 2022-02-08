package bogdan

class UrlMappings {

    static mappings = {

        // Controller Person
        "/api/users"(controller: "person", action: "index", method:"GET")
        "/api/users/$id"(controller: "person", action: "show", method:"GET")
        "/api/register"(controller: "register", action: "register", method:"POST")
        "/api/users/$id"(controller: "person", action: "update", method:"PUT")
        "/api/users/$id"(controller: "person", action: "delete", method:"DELETE")
        "/api/users/count"(controller: "person", action: "count", method:"GET")
        "/api/users/count/$id"(controller: "person", action: "countPerUser", method:"GET")


        // Controller Message
        "/api/users/$userId/feed"(controller: "message", action: "userFeed", method:"GET")
        "/api/users/$userId/messages"(controller: "message", action: "index", method:"GET")
        "/api/users/$userId/messages/$id"(controller: "message", action: "show", method:"GET")
        "/api/users/$userId/messages"(controller: "message", action: "save", method:"POST")
        "/api/users/$userId/messages/$id"(controller: "message", action: "update", method:"PUT")
        "/api/users/$userId/messages/$id"(controller: "message", action: "delete", method:"DELETE")


        // Controller follower
        "/api/users/$userId/followers"(controller: "relation", action: "index", method:"GET")
        "/api/users/$userId/follow"(controller: "relation", action: "iFollow", method:"GET")
        "/api/users/$userId/followers/$followerId"(controller: "relation", action: "show", method:"GET")
        "/api/users/$followerId/follow/$userId"(controller: "relation", action: "save", method:"POST")
        "/api/users/$followerId/unfollow/$userId"(controller: "relation", action: "delete", method:"DELETE")

        "500"(controller: "error")
    }
}
