export default {

    //Generic
    None: { code: 0, message: 'No errors' },
    Internal: { code: 1, message: 'Internal error' },
    Header: { code: 2, message: 'Device ID absent' },
    Incomplete: { code: 3, message: 'Incomplete request parameters' },
    NotFound: { code: 4, message: 'No data found' },
    NoEffect: { code: 5, message: 'Action had no effect' },

    // Auth
    Token: { code: 6, message: 'Token absent' },
    Session: { code: 7, message: 'Invalid session' },
    EmailTaken: { code: 8, message: 'Email already taken' },
    SocialAuth: { code: 9, message: 'User registered through social' },
    RegularAuth: { code: 10, message: 'User registered through username & password' },
    InvalidLoginType: { code: 11, message: 'Login type must be an integer between 0 and 2' },
    EmailUnregistered: { code: 12, message: 'Email not registered' },
    Password: { code: 13, message: 'Password invalid' },
    NoEmail: { code: 14, message: 'Email field is mandatory' },
    NoFCM: { code: 15, message: 'FCM Token field is mandatory' },

    // Post
    NoTitle: { code: 16, message: 'Title field is mandatory' },
    NoDescription: { code: 17, message: 'Description field is mandatory' },
    NoComment: { code: 18, message: 'Comment field is mandatory' }

};
