package bogdan

class UrlMappings {

    static mappings = {

        // Controller Person
        "/api/users"(controller: "person", action: "index", method:"GET")
        "/api/users/$id"(controller: "person", action: "show", method:"GET")
        "/api/register"(controller: "register", action: "register", method:"POST")
        "/api/users/$id"(controller: "person", action: "update", method:"PUT")
        "/api/users/$id"(controller: "person", action: "delete", method:"DELETE")


        // Controller Message
        "/api/users/$userId/messages"(controller: "message", action: "index", method:"GET")
        "/api/users/$userId/messages/$id"(controller: "message", action: "show", method:"GET")
        "/api/users/$userId/messages"(controller: "message", action: "save", method:"POST")
        "/api/users/$userId/messages/$id"(controller: "message", action: "update", method:"PUT")
        "/api/users/$userId/messages/$id"(controller: "message", action: "delete", method:"DELETE")


        // Controller follower
        "/api/users/$userId/followers"(controller: "follower", action: "index", method:"GET")
        "/api/users/$userId/followers/$followerId"(controller: "follower", action: "show", method:"GET")
        "/api/users/$followerId/follow/$userId"(controller: "follower", action: "save", method:"POST")
        "/api/users/$followerId/followers/$userId"(controller: "follower", action: "delete", method:"DELETE")

        "500"(controller: "error")
    }
}
