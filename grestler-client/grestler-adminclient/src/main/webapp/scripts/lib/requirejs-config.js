
// Configuration of libraries for use by RequireJS.
var require = {

    paths: {
        // JQuery, with a wrapper to keep it from the global namespace
        jquery: 'scripts/lib/jquery-2.1.3/jquery-2.1.3',
        'jquery-private': 'scripts/lib/jquery-private',

        // Ractive
        ractive: 'scripts/lib/ractive-0.6.1/ractive',

        // RequireJS plugin for plain text files (templates)
        text: 'scripts/lib/requirejs-2.1.16/text'
    },

    map: {
        // all modules will get 'jquery-private' for their 'jquery' dependency.
        '*': { 'jquery': 'jquery-private' },

        // 'jquery-private' wants the real jQuery module though.
        'jquery-private': { 'jquery': 'jquery' }
    }

};

