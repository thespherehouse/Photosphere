/**
 * @apiDefine ResponseBlock
 * @apiSuccess (200) {Object} error The error block
 * @apiSuccess (200) {Object} data The data block
 * 
 */

/**
 * @apiDefine SemiHeaderBlock
 * @apiHeader device-id         Device ID of the phone/computer
 */

/**
 * @apiDefine FullHeaderBlock
 * @apiHeader device-id         Device ID of the phone/computer
 * @apiHeader token             Token of the current session
 */

/**
 * @apiDefine SemiQueryBlock
 * @apiParam (Query) {Number{0..Infinity}} [skip=0] Skip initial
 * @apiParam (Query) {Number{1..50}} [limit=10]     Maximum number to return
 */

/**
 * @apiDefine FullQueryBlock
 * @apiParam (Query) {Number{0..Infinity}} [skip=0]                             Skip initial
 * @apiParam (Query) {Number{1..50}} [limit=10]                                 Maximum number to return
 * @apiParam (Query) {String="updatedAt","createdAt"} [orderBy="updatedAt"]     Order posts by a field
 * @apiParam (Query) {String="asc","desc"} [sortOrder="desc"]                   Sorting order
 */

 /**
  * @apiDefine TokenSuccessBlock
  * @apiSuccess (Response-Header) {String} token     The token associated with the generated session
  */

















































/**
 * 
 * @api {post} /auth/register Register
 * @apiName Register
 * @apiGroup Auth
 * @apiVersion  0.0.1
 * 
 * @apiParam {String} name      Full name
 * @apiParam {String} email     Email
 * @apiParam {String} password  Password
 *
 * @apiUse ResponseBlock
 * @apiUse SemiHeaderBlock
 * @apiUse TokenSuccessBlock
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
    }
 * 
 * 
 */

/**
 * 
 * @api {get} /auth/checkEmail/:email Check email
 * @apiName Check Email
 * @apiGroup Auth
 * @apiVersion  0.0.1
 * 
 * @apiParam (Query) {String} email  Email
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiSuccessExample {json} Success-Response:
    HTTP/1.1 200 OK
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
    }
 * 
 * 
 */

/**
 * 
 * @api {put} /auth/login Login
 * @apiName Login
 * @apiGroup Auth
 * @apiVersion  0.0.1
 * 
 * @apiParam {String} email     Email
 * @apiParam {String} password  Password
 *
 * @apiUse ResponseBlock
 * @apiUse SemiHeaderBlock
 * @apiUse TokenSuccessBlock
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
    }
 * 
 * 
 */

/**
 * 
 * @api {put} /auth/socialLogin Social login
 * @apiName SocialLogin
 * @apiGroup Auth
 * @apiVersion  0.0.1
 * 
 * @apiParam {String} name      Name of the user
 * @apiParam {String} email     Email Address
 * @apiParam {String} socialId  Social ID from the social network
 * @apiParam {Number} loginType 1 for Facebook, 2 for Google
 *
 * @apiUse ResponseBlock
 * @apiUse SemiHeaderBlock
 * @apiUse TokenSuccessBlock
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
    }
 * 
 * 
 */

/**
 * 
 * @api {put} /auth/silentLogin Silent login
 * @apiName SilentLogin
 * @apiGroup Auth
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
    }
 * 
 * 
 */

/**
 * 
 * @api {put} /auth/fcm     FCM
 * @apiName FCM
 * @apiGroup Auth
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
    }
 * 
 * 
 */

/**
 * 
 * @api {delete} /auth/logout   Logout
 * @apiName FCM
 * @apiGroup Auth
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
    }
 * 
 * 
 */

/**
 * 
 * @api {post} /posts   Create post
 * @apiName Create new post
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiParam {String} title         Title of the post
 * @apiParam {String} description   Description of the post
 * @apiParam {Multipart} photo      The multipart request body of the photo
 * 
 * @apiSuccessExample {json} Response
    {
    	"error": {
    		"code": 0,
    		"message": "No errors"
    	},
    	"data": {
    		"owner": "5a3e0b6d938a3b668ea2c34d",
    		"ownerName": "John Doe",
    		"title": "Heaven is never too far away... :)",
    		"description": "Beauty is always an arm's length away.",
    		"aspectRatio": 1.5,
    		"url": "5a3e0b6d938a3b668ea2c34d/4hnf6jm10jbnz3dvl",
    		"_id": "5a4296b1ec9ce36f841a57c6",
    		"commentsCount": 0,
    		"comments": [],
    		"likesCount": 0,
    		"likes": [],
    		"updatedAt": "2017-12-26T18:36:33.052Z",
    		"createdAt": "2017-12-26T18:36:33.052Z"
    	}
    }
 * 
 * 
 */

/**
 * 
 * @api {post} /posts/:postId/comments   Create comment
 * @apiName Create new comment
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiParam (Query) {String} postId    ID of the post
 * 
 * @apiParam {String} comment   Comment to be posted
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": {
            "comment": "That is just lovely! :-P",
            "user": {
                "_id": "5a37b645be91393f899fd78d",
                "name": "John Doe"
            },
            "_id": "5a43ca398ee5ae08313a776d",
            "updatedAt": "2017-12-27T16:28:41.705Z",
            "createdAt": "2017-12-27T16:28:41.705Z"
        }
    }
 * 
 * 
 */

/**
 * 
 * @api {post} /posts/:postId/like   Like
 * @apiName Like a post
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiParam (Query) {String} postId    ID of the post
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
    }
 * 
 */

/**
 * 
 * @api {get} /posts   Get All Posts
 * @apiName Get all posts
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * @apiUse FullQueryBlock
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": [
            {
                "_id": "5a4296b1ec9ce36f841a57c6",
                "owner": "5a3e0b6d938a3b668ea2c34d",
                "ownerName": "John Doe",
                "title": "Heaven is never too far away... :)",
                "description": "Beauty is always an arm's length away.",
                "aspectRatio": 1.5,
                "url": "5a3e0b6d938a3b668ea2c34d/4hnf6jm10jbnz3dvl",
                "commentsCount": 6,
                "likesCount": 2,
                "updatedAt": "2017-12-26T18:36:33.052Z",
                "createdAt": "2017-12-26T18:36:33.052Z",
                "isLikedByMe": true,
                "isCommentedByMe": true
            },
            {
                "_id": "5a4292e04d98b46d1e8e9dac",
                "owner": "5a3e0b6d938a3b668ea2c34d",
                "ownerName": "John Doe",
                "title": "So Blue!",
                "description": "Sample description",
                "aspectRatio": 0.8,
                "url": "5a3e0b6d938a3b668ea2c34d/4hnf6jljyjbnyih15",
                "commentsCount": 1,
                "likesCount": 1,
                "updatedAt": "2017-12-26T18:18:46.654Z",
                "createdAt": "2017-12-26T18:18:46.654Z",
                "isLikedByMe": false,
                "isCommentedByMe": false
            }
        ]
    }
 * 
 */

/**
 * 
 * @api {get} /posts/by/me  Get My Posts
 * @apiName Get my posts
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * @apiUse FullQueryBlock
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": [
            {
                "_id": "5a4296b1ec9ce36f841a57c6",
                "owner": "5a3e0b6d938a3b668ea2c34d",
                "ownerName": "John Doe",
                "title": "Heaven is never too far away... :)",
                "description": "Beauty is always an arm's length away.",
                "aspectRatio": 1.5,
                "url": "5a3e0b6d938a3b668ea2c34d/4hnf6jm10jbnz3dvl",
                "commentsCount": 6,
                "likesCount": 2,
                "updatedAt": "2017-12-26T18:36:33.052Z",
                "createdAt": "2017-12-26T18:36:33.052Z",
                "isLikedByMe": true,
                "isCommentedByMe": true
            },
            {
                "_id": "5a4292e04d98b46d1e8e9dac",
                "owner": "5a3e0b6d938a3b668ea2c34d",
                "ownerName": "John Doe",
                "title": "So Blue!",
                "description": "Sample description",
                "aspectRatio": 0.8,
                "url": "5a3e0b6d938a3b668ea2c34d/4hnf6jljyjbnyih15",
                "commentsCount": 1,
                "likesCount": 1,
                "updatedAt": "2017-12-26T18:18:46.654Z",
                "createdAt": "2017-12-26T18:18:46.654Z",
                "isLikedByMe": false,
                "isCommentedByMe": false
            }
        ]
    }
 *
 */

/**
 * 
 * @api {get} /posts/by/:userId   Get User's Posts
 * @apiName Get user's posts
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * @apiUse FullQueryBlock
 * 
 * @apiParam (Query) {String} userId    ID of the user
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": [
            {
                "_id": "5a4296b1ec9ce36f841a57c6",
                "owner": "5a3e0b6d938a3b668ea2c34d",
                "ownerName": "John Doe",
                "title": "Heaven is never too far away... :)",
                "description": "Beauty is always an arm's length away.",
                "aspectRatio": 1.5,
                "url": "5a3e0b6d938a3b668ea2c34d/4hnf6jm10jbnz3dvl",
                "commentsCount": 6,
                "likesCount": 2,
                "updatedAt": "2017-12-26T18:36:33.052Z",
                "createdAt": "2017-12-26T18:36:33.052Z",
                "isLikedByMe": true,
                "isCommentedByMe": true
            },
            {
                "_id": "5a4292e04d98b46d1e8e9dac",
                "owner": "5a3e0b6d938a3b668ea2c34d",
                "ownerName": "John Doe",
                "title": "So Blue!",
                "description": "Sample description",
                "aspectRatio": 0.8,
                "url": "5a3e0b6d938a3b668ea2c34d/4hnf6jljyjbnyih15",
                "commentsCount": 1,
                "likesCount": 1,
                "updatedAt": "2017-12-26T18:18:46.654Z",
                "createdAt": "2017-12-26T18:18:46.654Z",
                "isLikedByMe": false,
                "isCommentedByMe": false
            }
        ]
    }
 *
 */

/**
 * 
 * @api {get} /posts/:postId   Get Post Details
 * @apiName Get post details
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * @apiUse SemiQueryBlock
 * 
 * @apiParam (Query) {String} postId    ID of the post
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": {
            "_id": "5a4296b1ec9ce36f841a57c6",
            "owner": "5a3e0b6d938a3b668ea2c34d",
            "ownerName": "John Doe",
            "title": "Heaven is never too far away... :)",
            "description": "Beauty is always an arm's length away.",
            "aspectRatio": 1.5,
            "url": "5a3e0b6d938a3b668ea2c34d/4hnf6jm10jbnz3dvl",
            "commentsCount": 6,
            "likesCount": 2,
            "updatedAt": "2017-12-26T18:36:33.052Z",
            "createdAt": "2017-12-26T18:36:33.052Z",
            "isLikedByMe": true,
            "isCommentedByMe": true
        }
    }
 *
 */

/**
 * 
 * @api {get} /posts/:postId/likes   Get Likes
 * @apiName Get likes
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * @apiUse SemiQueryBlock
 * 
 * @apiParam (Query) {String} postId    ID of the post
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": [
            {
                "user": {
                    "_id": "5a37b645be91393f899fd78d",
                    "name": "John Doe"
                },
                "_id": "5a476a75a65ee1482afba947",
                "createdAt": "2017-12-30T10:29:09.657Z"
            }
        ]
    }
 *
 */

/**
 * 
 * @api {get} /posts/:postId/comments   Get Comments
 * @apiName Get comments
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * @apiUse SemiQueryBlock
 * 
 * @apiParam (Query) {String} postId    ID of the post
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": [
            {
                "comment": "Here let me stop you right there... Think straight young man!",
                "user": {
                    "_id": "5a3e0f9c5d6e1dff6560500c",
                    "name": "John Doe"
                },
                "_id": "5a454cc5a0da6e1e8dd07966",
                "updatedAt": "2017-12-28T19:57:57.187Z",
                "createdAt": "2017-12-28T19:57:57.187Z"
            },
            {
                "comment": "Leave it, I will keep on doing this for testing",
                "user": {
                    "_id": "5a3e0f9c5d6e1dff6560500c",
                    "name": "John Doe"
                },
                "_id": "5a454caca0da6e1e8dd07965",
                "updatedAt": "2017-12-28T19:57:32.215Z",
                "createdAt": "2017-12-28T19:57:32.215Z"
            }
        ]
    }
 *
 */

/**
 * 
 * @api {put} /posts/:postId/title   Edit Post Title
 * @apiName Edit post title
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiParam (Query) {String} postId    ID of the post
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
 *
 */

/**
 * 
 * @api {put} /posts/:postId/description   Edit Post Description
 * @apiName Edit post description
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiParam (Query) {String} postId    ID of the post
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
 *
 */

/**
 * 
 * @api {put} /posts/:postId/comments/:commentId   Edit Comment
 * @apiName Edit comment
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiParam (Query) {String} postId    ID of the post
 * @apiParam (Query) {String} commentId ID of the comment
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
 *
 */

/**
 * 
 * @api {delete} /posts/:postId   Delete Post
 * @apiName Delete post
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiParam (Query) {String} postId    ID of the post
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
 *
 */

/**
 * 
 * @api {delete} /posts/:postId/unlike   Unlike
 * @apiName Unlike a post
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiParam (Query) {String} postId    ID of the post
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
    }
 *
 */

/**
 * 
 * @api {delete} /posts/:postId/comments/:commentId   Delete Comment
 * @apiName Delete comment
 * @apiGroup Posts
 * @apiVersion  0.0.1
 *
 * @apiUse ResponseBlock
 * @apiUse FullHeaderBlock
 * 
 * @apiParam (Query) {String} postId    ID of the post
 * @apiParam (Query) {String} commentId ID of the comment
 * 
 * @apiSuccessExample {json} Response
    {
        "error": {
            "code": 0,
            "message": "No errors"
        },
        "data": null
 *
 */
