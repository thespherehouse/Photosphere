export default {

    //Generic
    None: { code: 0, message: 'No errors' },
    Internal: { code: 1, message: 'Internal error' },
    Token: { code: 2, message: 'Token invalid or absent' },
    Header: { code: 3, message: 'Device ID & type absent' },
    Incomplete: { code: 4, message: 'Incomplete request parameters' },
    Session: { code: 5, message: 'Invalid session' },
    NotFound: { code: 6, message: 'No data found' },
    NoEffect: { code: 7, message: 'Action had no effect' },

    // Registration
    EmailTaken: { code: 8, message: 'Email already taken' },

    // Login
    EmailUnregistered: { code: 9, message: 'Email not registered' },
    Password: { code: 10, message: 'Password invalid' }
};
