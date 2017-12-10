'use strict';

const username = 'suhel';
const password = 'milliongreats';
const dbname = 'storiesphere';

// module.exports.mongoUri = 'mongodb://' + username + ':' + password + '@' +
//     'portions-shard-00-00-kvlwp.mongodb.net:27017,' +
//     'portions-shard-00-01-kvlwp.mongodb.net:27017,' +
//     'portions-shard-00-00-kvlwp.mongodb.net:27017/' +
//     dbname + '?ssl=true&replicaSet=portions-shard-0&authSource=admin';

module.exports.mongoUri = 'mongodb://127.0.0.1:27017/' + dbname;
module.exports.port = 80;
