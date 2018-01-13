export default {

    //Generic
    None: { code: 0, message: 'No errors' },
    Internal: { code: 1, message: 'Internal error' },
    Header: { code: 2, message: 'Device ID absent' },
    Incomplete: { code: 3, message: 'Incomplete request parameters' },
    NotFound: { code: 4, message: 'No data found' },
    AlreadyPresent: { code: 5, message: 'Data already present' },
    NoEffect: { code: 6, message: 'Action had no effect' },

    // Auth
    Token: { code: 7, message: 'Token absent' },
    Session: { code: 8, message: 'Invalid session' },
    EmailTaken: { code: 9, message: 'Email already taken' },
    SocialAuth: { code: 10, message: 'User registered through social' },
    RegularAuth: { code: 11, message: 'User registered through username & password' },
    InvalidLoginType: { code: 12, message: 'Login type must be an integer between 0 and 2' },
    EmailUnregistered: { code: 13, message: 'Email not registered' },
    Password: { code: 14, message: 'Password invalid' },
    NoEmail: { code: 15, message: 'Email field is mandatory' },
    NoFCM: { code: 16, message: 'FCM Token field is mandatory' },

    // Post
    NoTitle: { code: 17, message: 'Title field is mandatory' },
    NoDescription: { code: 18, message: 'Description field is mandatory' },
    NoComment: { code: 19, message: 'Comment field is mandatory' }

};
