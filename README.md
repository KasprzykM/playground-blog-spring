 # Playground blog Spring Backend
RESTful API service for blog-like website written in Spring. This project is done purely to learn Spring aspects while
at the same time make something semi-useful, so not everything in it might make sense. Perhaps it will be of use for you as well.

## Features:
* Data persistence to MySQL database.
* Fully dockerized both for database and main application.
* User authorization based on JWT token. (JJWT 0.4)
* Role based security + authorization required for all routes.
* Creating/updating/deleting posts/comments.
* Gradle based.
* Java 14 (though even Java 8 would probably work as well).
* Spring Boot 2.3.2, Spring Security, Spring Actuator and Spring Hateoas.
* RESTful-like responses and statelessness.

## How to run

Firstly there is some configuration to do, as you may want to change it yourself:
* in ``docker-compose.yml`` file change ``MYSQL_ROOT_PASSWORD``, ``MYSQL_USER``, ``DATABASE_USER`` and ``DATABASE_PASSWORD``
according to your needs.
* you may want to change Signing key and Token time validity for JWT in ``TokenProvider.java`` as well.

To build project file run:
```bash
$ gradlew clean build
```

Then create docker image:
```shell script
$ docker build -t spring-backend .
```

And to run it all together:

```shell script
$ docker-compose up
```

## How to use

### User /api/users/
Firstly, you need to register (and be saved to database) to start. If you are running application locally
you can send your details like that:
```bash
curl --request POST 'http://localhost:8080/api/users/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "User1",
    "password": "pwd123",
    "email": "test@gmail.com"
}'
```
If everything went successful your response will look like this:
NOTE: You can not register as `ADMIN` meaning you will not be able to perform some of the available requests. This is by design
and to actually register `ADMIN` account you will need to either alter database or add him in `CommandLineRunner`.
```yaml
{
    "email": "test1a@gmail.com",
    "username": "User11",
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/users/5"
        },
        "users": {
            "href": "http://localhost:8080/api/users"
        }
    }
}
```

Then you will need to get actually log in as that user and receive your token:
```shell script
curl --request POST 'http://localhost:8080/api/token/generate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "User1",
    "password": "pwd123"
}'
```

And again if successful you will receive response:
```yaml
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyMSIsInNjb3BlcyI6IlJPTEVfVVNFUiI....."
}
```
Use that token for later actions. For example, as `ADMIN` you can get list of all users:
```shell script
curl --location --request GET 'http://localhost:8080/api/users' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1p....'
```

Which will look like this:
```yaml
{
    "_embedded": {
        "users": [
            {
                "email": "admin1@gmail.com",
                "username": "Admin1",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users/1"
                    },
                    "users": {
                        "href": "http://localhost:8080/api/users"
                    }
                }
            },
            {
                "email": "user1@gmail.com",
                "username": "User1",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users/3"
                    },
                    "users": {
                        "href": "http://localhost:8080/api/users"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/users"
        }
    }
}
```

Supported methods:
* `GET` by `@PathVariable id` one user.
* `GET` all users.
* `DELETE` by `@PathVariable id` one user.
* `GET` with route `/api/users/name/` by `@PathVariable username` one user of id.
* `PUT` by `@PathVariable id` user with `@RequestBody User` to update existing user of id.
* `PUT` with route `/api/users/changePassword/` with `@RequestBody of new User Details` to change password.
* `POST` with route `/api/users/register` to register (explained above).

### Post /api/posts/

Posts take form of and only `ADMIN` can create one (Why? Well because its supposed to be one man blog only):
```shell script
curl --request POST 'http://localhost:8080/api/posts' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJz....' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Post To be Found",
    "rawContent": "Hello World \n",
    "renderedContent": "Hello world ",
    "renderedSummary": "It just says hello world!"
}'
```

With everything going correct the response will look like this:
```yaml
{
    "author": {
        "email": "admin1@gmail.com",
        "username": "Admin1"
    },
    "title": "Post To be Found",
    "rawContent": "Hello World \n",
    "renderedContent": "Hello world ",
    "createdAt": "2020-08-28T19:26:55.607+00:00",
    "lastUpdatedAt": "2020-08-28T19:26:55.607+00:00",
    "draft": true,
    "_links": {
        "self": [
            {
                "href": "http://localhost:8080/api/users/1"
            },
            {
                "href": "http://localhost:8080/api/posts/1"
            }
        ],
        "posts": {
            "href": "http://localhost:8080/api/posts"
        }
    }
}
```

Supported methods:
* `GET` all posts.
* `POST` with `@RequestBody Post` to create post.
* `PUT` with `@PathVariable id` and `@RequestBody Post` to update existing post of id. You can only update your own posts.
* `DELETE` with `@PathVariable id` to delete post of id.
* `GET` with `@PathVariable id` to get post of id.
* `GET` with route `/api/posts/byUser/` with `@PathVariable username` to fetch all posts by user of username.


### Comments /api/comments/

Comments can be posted by both `USER` and `ADMIN` and take form of:
```shell script
curl --location --request POST 'http://localhost:8080/api/comments/1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ...' \
--header 'Content-Type: application/json' \
--data-raw '{
    "text": "Nice one. I liked it."
}'
```
Where `@PathVariable 1` refers to post of id 1.

Response will look like:
```yaml
{
    "text": "Nice one. I liked it.",
    "createdAt": "2020-08-28T19:35:15.059+00:00",
    "commentAuthor": {
        "email": "admin1@gmail.com",
        "username": "Admin1"
    },
    "commentedPost": {
        "author": {
            "email": "admin1@gmail.com",
            "username": "Admin1"
        },
        "title": "Post To be Found",
        "rawContent": "Hello World \n",
        "renderedContent": "Hello world ",
        "createdAt": "2020-08-28T19:26:55.607+00:00",
        "lastUpdatedAt": "2020-08-28T19:26:55.607+00:00",
        "draft": true
    },
    "_links": {
        "self": [
            {
                "href": "http://localhost:8080/api/users/1"
            },
            {
                "href": "http://localhost:8080/api/posts/1"
            },
            {
                "href": "http://localhost:8080/api/comments/1"
            }
        ],
        "comments": {
            "href": "http://localhost:8080/api/comments"
        }
    }
}
```

Supported methods:
* `GET` with `@PathVariable id` comment of id.
* `GET` all comments -> this one is not the best idea though.
* `DELETE` with `@PathVariable id` to delete comment of id.
* `POST` with `@PathVariable id` and `@RequestBody Comment` to comment of post id.
* `GET` with route `/api/comments/byPost` and `@PathVariable id` to get all comments from post of id.
* `GET` with route `/api/comments/byUser` and `@PathVariable username` to get all comments from user of username.
* `PUT` with `@PathVariable id` and `@RequestBody Comment` to update comment. You can only update your own comments.


### So what's missing

Well, for starters there are no tests written. Some of the API Methods have overly verbose responses with a lot of hrefs in them, thats probably bad idea as well.
There is also no way to check if provided email is actually real, it only checks if the address is valid but not if is in use.
