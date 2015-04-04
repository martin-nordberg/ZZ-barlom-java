//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

define(
    [
        'require',
        'exports',
        'scripts/js-gen/org/grestler/infrastructure/dependencies/injection',
        'scripts/js-gen/org/grestler/presentation/adminclient/main/topnavcontroller',
        'scripts/js-gen/org/grestler/presentation/adminclient/main/topnavmodel',
        'scripts/js-gen/org/grestler/presentation/adminclient/main/topnavviewmodel'
    ],
    function ( require, exports, injection, topnavcontroller, topnavmodel, topnavviewmodel ) {

        exports.context =
            injection.makeContext( topnavmodel.provideTopNavModelPageSelection )
                .plus( topnavcontroller.provideTopNavController )
                .plus( topnavviewmodel.provideTopNavViewModelPageVisibilities );

    }
);
