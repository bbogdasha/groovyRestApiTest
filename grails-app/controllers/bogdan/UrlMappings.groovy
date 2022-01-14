package bogdan

class UrlMappings {

    static mappings = {

        // Controller Person
        "/api/users"(controller: "person", action: "index", method:"GET")
        "/api/users/$id"(controller: "person", action: "show", method:"GET")

        "/api/users"(controller: "person", action: "save", method:"POST")
        "/api/users/$id"(controller: "person", action: "update", method:"PUT")

        "/api/users/$id"(controller: "person", action: "delete", method:"DELETE")


        // Controller Message
        "/api/users/$userId/messages"(controller: "message", action: "index", method:"GET")
        "/api/users/$userId/messages/$id"(controller: "message", action: "show", method:"GET")

        "/api/users/$userId/messages"(controller: "message", action: "save", method:"POST")
        "/api/users/$userId/messages/$id"(controller: "message", action: "update", method:"PUT")

        "/api/users/$userId/messages/$id"(controller: "message", action: "delete", method:"DELETE")

//        "/"(view:"/index")
//        "500"(view:'/error')
//        "404"(controller: "error", action: "handleNotFoundException")
        "500"(controller: "error")
    }
}
