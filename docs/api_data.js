define({ "api": [
  {
    "type": "get",
    "url": "/auth/checkEmail/:email",
    "title": "Check email",
    "name": "Check_Email",
    "group": "Auth",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>Email</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Auth",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "put",
    "url": "/auth/fcm",
    "title": "FCM",
    "name": "FCM",
    "group": "Auth",
    "version": "0.0.1",
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Auth",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "delete",
    "url": "/auth/logout",
    "title": "Logout",
    "name": "FCM",
    "group": "Auth",
    "version": "0.0.1",
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Auth",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "put",
    "url": "/auth/login",
    "title": "Login",
    "name": "Login",
    "group": "Auth",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>Email</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>Password</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ],
        "Response-Header": [
          {
            "group": "Response-Header",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>The token associated with the generated session</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Auth",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "/auth/register",
    "title": "Register",
    "name": "Register",
    "group": "Auth",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>Full name</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>Email</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>Password</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ],
        "Response-Header": [
          {
            "group": "Response-Header",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>The token associated with the generated session</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Auth",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          }
        ]
      }
    }
  },
  {
    "type": "put",
    "url": "/auth/silentLogin",
    "title": "Silent login",
    "name": "SilentLogin",
    "group": "Auth",
    "version": "0.0.1",
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Auth",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "put",
    "url": "/auth/socialLogin",
    "title": "Social login",
    "name": "SocialLogin",
    "group": "Auth",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>Name of the user</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>Email Address</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "socialId",
            "description": "<p>Social ID from the social network</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "loginType",
            "description": "<p>1 for Facebook, 2 for Google</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ],
        "Response-Header": [
          {
            "group": "Response-Header",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>The token associated with the generated session</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Auth",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "/posts/:postId/comments",
    "title": "Create comment",
    "name": "Create_new_comment",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "postId",
            "description": "<p>ID of the post</p>"
          }
        ],
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "comment",
            "description": "<p>Comment to be posted</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": {\n        \"comment\": \"That is just lovely! :-P\",\n        \"user\": {\n            \"_id\": \"5a37b645be91393f899fd78d\",\n            \"name\": \"John Doe\"\n        },\n        \"_id\": \"5a43ca398ee5ae08313a776d\",\n        \"updatedAt\": \"2017-12-27T16:28:41.705Z\",\n        \"createdAt\": \"2017-12-27T16:28:41.705Z\"\n    }\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "/posts",
    "title": "Create post",
    "name": "Create_new_post",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>Title of the post</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "description",
            "description": "<p>Description of the post</p>"
          },
          {
            "group": "Parameter",
            "type": "Multipart",
            "optional": false,
            "field": "photo",
            "description": "<p>The multipart request body of the photo</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n\t\"error\": {\n\t\t\"code\": 0,\n\t\t\"message\": \"No errors\"\n\t},\n\t\"data\": {\n\t\t\"owner\": \"5a3e0b6d938a3b668ea2c34d\",\n\t\t\"ownerName\": \"John Doe\",\n\t\t\"title\": \"Heaven is never too far away... :)\",\n\t\t\"description\": \"Beauty is always an arm's length away.\",\n\t\t\"aspectRatio\": 1.5,\n\t\t\"url\": \"5a3e0b6d938a3b668ea2c34d/4hnf6jm10jbnz3dvl\",\n\t\t\"_id\": \"5a4296b1ec9ce36f841a57c6\",\n\t\t\"commentsCount\": 0,\n\t\t\"comments\": [],\n\t\t\"likesCount\": 0,\n\t\t\"likes\": [],\n\t\t\"updatedAt\": \"2017-12-26T18:36:33.052Z\",\n\t\t\"createdAt\": \"2017-12-26T18:36:33.052Z\"\n\t}\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "delete",
    "url": "/posts/:postId/comments/:commentId",
    "title": "Delete Comment",
    "name": "Delete_comment",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "postId",
            "description": "<p>ID of the post</p>"
          },
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "commentId",
            "description": "<p>ID of the comment</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "delete",
    "url": "/posts/:postId",
    "title": "Delete Post",
    "name": "Delete_post",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "postId",
            "description": "<p>ID of the post</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "put",
    "url": "/posts/:postId/comments/:commentId",
    "title": "Edit Comment",
    "name": "Edit_comment",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "postId",
            "description": "<p>ID of the post</p>"
          },
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "commentId",
            "description": "<p>ID of the comment</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "put",
    "url": "/posts/:postId/description",
    "title": "Edit Post Description",
    "name": "Edit_post_description",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "postId",
            "description": "<p>ID of the post</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "put",
    "url": "/posts/:postId/title",
    "title": "Edit Post Title",
    "name": "Edit_post_title",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "postId",
            "description": "<p>ID of the post</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/posts",
    "title": "Get All Posts",
    "name": "Get_all_posts",
    "group": "Posts",
    "version": "0.0.1",
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": [\n        {\n            \"_id\": \"5a4296b1ec9ce36f841a57c6\",\n            \"owner\": \"5a3e0b6d938a3b668ea2c34d\",\n            \"ownerName\": \"John Doe\",\n            \"title\": \"Heaven is never too far away... :)\",\n            \"description\": \"Beauty is always an arm's length away.\",\n            \"aspectRatio\": 1.5,\n            \"url\": \"5a3e0b6d938a3b668ea2c34d/4hnf6jm10jbnz3dvl\",\n            \"commentsCount\": 6,\n            \"likesCount\": 2,\n            \"updatedAt\": \"2017-12-26T18:36:33.052Z\",\n            \"createdAt\": \"2017-12-26T18:36:33.052Z\",\n            \"isLikedByMe\": true,\n            \"isCommentedByMe\": true\n        },\n        {\n            \"_id\": \"5a4292e04d98b46d1e8e9dac\",\n            \"owner\": \"5a3e0b6d938a3b668ea2c34d\",\n            \"ownerName\": \"John Doe\",\n            \"title\": \"So Blue!\",\n            \"description\": \"Sample description\",\n            \"aspectRatio\": 0.8,\n            \"url\": \"5a3e0b6d938a3b668ea2c34d/4hnf6jljyjbnyih15\",\n            \"commentsCount\": 1,\n            \"likesCount\": 1,\n            \"updatedAt\": \"2017-12-26T18:18:46.654Z\",\n            \"createdAt\": \"2017-12-26T18:18:46.654Z\",\n            \"isLikedByMe\": false,\n            \"isCommentedByMe\": false\n        }\n    ]\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "Number",
            "size": "0..Infinity",
            "optional": true,
            "field": "skip",
            "defaultValue": "0",
            "description": "<p>Skip initial</p>"
          },
          {
            "group": "Query",
            "type": "Number",
            "size": "1..50",
            "optional": true,
            "field": "limit",
            "defaultValue": "10",
            "description": "<p>Maximum number to return</p>"
          },
          {
            "group": "Query",
            "type": "String",
            "allowedValues": [
              "\"updatedAt\"",
              "\"createdAt\""
            ],
            "optional": true,
            "field": "orderBy",
            "defaultValue": "updatedAt",
            "description": "<p>Order posts by a field</p>"
          },
          {
            "group": "Query",
            "type": "String",
            "allowedValues": [
              "\"asc\"",
              "\"desc\""
            ],
            "optional": true,
            "field": "sortOrder",
            "defaultValue": "desc",
            "description": "<p>Sorting order</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/posts/:postId/comments",
    "title": "Get Comments",
    "name": "Get_comments",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "postId",
            "description": "<p>ID of the post</p>"
          },
          {
            "group": "Query",
            "type": "Number",
            "size": "0..Infinity",
            "optional": true,
            "field": "skip",
            "defaultValue": "0",
            "description": "<p>Skip initial</p>"
          },
          {
            "group": "Query",
            "type": "Number",
            "size": "1..50",
            "optional": true,
            "field": "limit",
            "defaultValue": "10",
            "description": "<p>Maximum number to return</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": [\n        {\n            \"comment\": \"Here let me stop you right there... Think straight young man!\",\n            \"user\": {\n                \"_id\": \"5a3e0f9c5d6e1dff6560500c\",\n                \"name\": \"John Doe\"\n            },\n            \"_id\": \"5a454cc5a0da6e1e8dd07966\",\n            \"updatedAt\": \"2017-12-28T19:57:57.187Z\",\n            \"createdAt\": \"2017-12-28T19:57:57.187Z\"\n        },\n        {\n            \"comment\": \"Leave it, I will keep on doing this for testing\",\n            \"user\": {\n                \"_id\": \"5a3e0f9c5d6e1dff6560500c\",\n                \"name\": \"John Doe\"\n            },\n            \"_id\": \"5a454caca0da6e1e8dd07965\",\n            \"updatedAt\": \"2017-12-28T19:57:32.215Z\",\n            \"createdAt\": \"2017-12-28T19:57:32.215Z\"\n        }\n    ]\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/posts/:postId/likes",
    "title": "Get Likes",
    "name": "Get_likes",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "postId",
            "description": "<p>ID of the post</p>"
          },
          {
            "group": "Query",
            "type": "Number",
            "size": "0..Infinity",
            "optional": true,
            "field": "skip",
            "defaultValue": "0",
            "description": "<p>Skip initial</p>"
          },
          {
            "group": "Query",
            "type": "Number",
            "size": "1..50",
            "optional": true,
            "field": "limit",
            "defaultValue": "10",
            "description": "<p>Maximum number to return</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": [\n        {\n            \"user\": {\n                \"_id\": \"5a37b645be91393f899fd78d\",\n                \"name\": \"John Doe\"\n            },\n            \"_id\": \"5a476a75a65ee1482afba947\",\n            \"createdAt\": \"2017-12-30T10:29:09.657Z\"\n        }\n    ]\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/posts/by/me",
    "title": "Get My Posts",
    "name": "Get_my_posts",
    "group": "Posts",
    "version": "0.0.1",
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": [\n        {\n            \"_id\": \"5a4296b1ec9ce36f841a57c6\",\n            \"owner\": \"5a3e0b6d938a3b668ea2c34d\",\n            \"ownerName\": \"John Doe\",\n            \"title\": \"Heaven is never too far away... :)\",\n            \"description\": \"Beauty is always an arm's length away.\",\n            \"aspectRatio\": 1.5,\n            \"url\": \"5a3e0b6d938a3b668ea2c34d/4hnf6jm10jbnz3dvl\",\n            \"commentsCount\": 6,\n            \"likesCount\": 2,\n            \"updatedAt\": \"2017-12-26T18:36:33.052Z\",\n            \"createdAt\": \"2017-12-26T18:36:33.052Z\",\n            \"isLikedByMe\": true,\n            \"isCommentedByMe\": true\n        },\n        {\n            \"_id\": \"5a4292e04d98b46d1e8e9dac\",\n            \"owner\": \"5a3e0b6d938a3b668ea2c34d\",\n            \"ownerName\": \"John Doe\",\n            \"title\": \"So Blue!\",\n            \"description\": \"Sample description\",\n            \"aspectRatio\": 0.8,\n            \"url\": \"5a3e0b6d938a3b668ea2c34d/4hnf6jljyjbnyih15\",\n            \"commentsCount\": 1,\n            \"likesCount\": 1,\n            \"updatedAt\": \"2017-12-26T18:18:46.654Z\",\n            \"createdAt\": \"2017-12-26T18:18:46.654Z\",\n            \"isLikedByMe\": false,\n            \"isCommentedByMe\": false\n        }\n    ]\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "Number",
            "size": "0..Infinity",
            "optional": true,
            "field": "skip",
            "defaultValue": "0",
            "description": "<p>Skip initial</p>"
          },
          {
            "group": "Query",
            "type": "Number",
            "size": "1..50",
            "optional": true,
            "field": "limit",
            "defaultValue": "10",
            "description": "<p>Maximum number to return</p>"
          },
          {
            "group": "Query",
            "type": "String",
            "allowedValues": [
              "\"updatedAt\"",
              "\"createdAt\""
            ],
            "optional": true,
            "field": "orderBy",
            "defaultValue": "updatedAt",
            "description": "<p>Order posts by a field</p>"
          },
          {
            "group": "Query",
            "type": "String",
            "allowedValues": [
              "\"asc\"",
              "\"desc\""
            ],
            "optional": true,
            "field": "sortOrder",
            "defaultValue": "desc",
            "description": "<p>Sorting order</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/posts/:postId",
    "title": "Get Post Details",
    "name": "Get_post_details",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "postId",
            "description": "<p>ID of the post</p>"
          },
          {
            "group": "Query",
            "type": "Number",
            "size": "0..Infinity",
            "optional": true,
            "field": "skip",
            "defaultValue": "0",
            "description": "<p>Skip initial</p>"
          },
          {
            "group": "Query",
            "type": "Number",
            "size": "1..50",
            "optional": true,
            "field": "limit",
            "defaultValue": "10",
            "description": "<p>Maximum number to return</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": {\n        \"_id\": \"5a4296b1ec9ce36f841a57c6\",\n        \"owner\": \"5a3e0b6d938a3b668ea2c34d\",\n        \"ownerName\": \"John Doe\",\n        \"title\": \"Heaven is never too far away... :)\",\n        \"description\": \"Beauty is always an arm's length away.\",\n        \"aspectRatio\": 1.5,\n        \"url\": \"5a3e0b6d938a3b668ea2c34d/4hnf6jm10jbnz3dvl\",\n        \"commentsCount\": 6,\n        \"likesCount\": 2,\n        \"updatedAt\": \"2017-12-26T18:36:33.052Z\",\n        \"createdAt\": \"2017-12-26T18:36:33.052Z\",\n        \"isLikedByMe\": true,\n        \"isCommentedByMe\": true\n    }\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/posts/by/:userId",
    "title": "Get User's Posts",
    "name": "Get_user_s_posts",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "userId",
            "description": "<p>ID of the user</p>"
          },
          {
            "group": "Query",
            "type": "Number",
            "size": "0..Infinity",
            "optional": true,
            "field": "skip",
            "defaultValue": "0",
            "description": "<p>Skip initial</p>"
          },
          {
            "group": "Query",
            "type": "Number",
            "size": "1..50",
            "optional": true,
            "field": "limit",
            "defaultValue": "10",
            "description": "<p>Maximum number to return</p>"
          },
          {
            "group": "Query",
            "type": "String",
            "allowedValues": [
              "\"updatedAt\"",
              "\"createdAt\""
            ],
            "optional": true,
            "field": "orderBy",
            "defaultValue": "updatedAt",
            "description": "<p>Order posts by a field</p>"
          },
          {
            "group": "Query",
            "type": "String",
            "allowedValues": [
              "\"asc\"",
              "\"desc\""
            ],
            "optional": true,
            "field": "sortOrder",
            "defaultValue": "desc",
            "description": "<p>Sorting order</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": [\n        {\n            \"_id\": \"5a4296b1ec9ce36f841a57c6\",\n            \"owner\": \"5a3e0b6d938a3b668ea2c34d\",\n            \"ownerName\": \"John Doe\",\n            \"title\": \"Heaven is never too far away... :)\",\n            \"description\": \"Beauty is always an arm's length away.\",\n            \"aspectRatio\": 1.5,\n            \"url\": \"5a3e0b6d938a3b668ea2c34d/4hnf6jm10jbnz3dvl\",\n            \"commentsCount\": 6,\n            \"likesCount\": 2,\n            \"updatedAt\": \"2017-12-26T18:36:33.052Z\",\n            \"createdAt\": \"2017-12-26T18:36:33.052Z\",\n            \"isLikedByMe\": true,\n            \"isCommentedByMe\": true\n        },\n        {\n            \"_id\": \"5a4292e04d98b46d1e8e9dac\",\n            \"owner\": \"5a3e0b6d938a3b668ea2c34d\",\n            \"ownerName\": \"John Doe\",\n            \"title\": \"So Blue!\",\n            \"description\": \"Sample description\",\n            \"aspectRatio\": 0.8,\n            \"url\": \"5a3e0b6d938a3b668ea2c34d/4hnf6jljyjbnyih15\",\n            \"commentsCount\": 1,\n            \"likesCount\": 1,\n            \"updatedAt\": \"2017-12-26T18:18:46.654Z\",\n            \"createdAt\": \"2017-12-26T18:18:46.654Z\",\n            \"isLikedByMe\": false,\n            \"isCommentedByMe\": false\n        }\n    ]\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "/posts/:postId/like",
    "title": "Like",
    "name": "Like_a_post",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "postId",
            "description": "<p>ID of the post</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  },
  {
    "type": "delete",
    "url": "/posts/:postId/unlike",
    "title": "Unlike",
    "name": "Unlike_a_post",
    "group": "Posts",
    "version": "0.0.1",
    "parameter": {
      "fields": {
        "Query": [
          {
            "group": "Query",
            "type": "String",
            "optional": false,
            "field": "postId",
            "description": "<p>ID of the post</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "{\n    \"error\": {\n        \"code\": 0,\n        \"message\": \"No errors\"\n    },\n    \"data\": null\n}",
          "type": "json"
        }
      ],
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "error",
            "description": "<p>The error block</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>The data block</p>"
          }
        ]
      }
    },
    "filename": "src/api/features/doc.js",
    "groupTitle": "Posts",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "device-id",
            "description": "<p>Device ID of the phone/computer</p>"
          },
          {
            "group": "Header",
            "optional": false,
            "field": "token",
            "description": "<p>Token of the current session</p>"
          }
        ]
      }
    }
  }
] });
