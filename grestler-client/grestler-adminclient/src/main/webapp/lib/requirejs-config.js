
// Configuration of libraries for use by RequireJS.
var require = {
    paths: {
        jquery: 'lib/jquery-2.1.3/jquery-2.1.3',
        "jquery-private": 'lib/jquery-private',
        ractive: 'lib/ractive-0.6.1/ractive',
        text: 'lib/requirejs-2.1.16/text'
    },

    map: {
        // all modules will get 'jquery-private' for their 'jquery' dependency.
        '*': { 'jquery': 'jquery-private' },

        // 'jquery-private' wants the real jQuery module though.
        'jquery-private': { 'jquery': 'jquery' }
    }

};

