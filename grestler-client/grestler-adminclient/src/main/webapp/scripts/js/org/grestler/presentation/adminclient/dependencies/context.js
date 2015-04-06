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
        'scripts/js-gen/org/grestler/presentation/adminclient/main/topnavviewmodel',
        'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/leftnavcontroller',
        'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/leftnavmodel',
        'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/leftnavviewmodel',
        'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/rightnavcontroller',
        'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/rightnavmodel',
        'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/rightnavviewmodel'
    ],
    function (
        require,
        exports,
        injection,
        topnavcontroller,
        topnavmodel,
        topnavviewmodel,
        schemapage_leftnavcontroller,
        schemapage_leftnavmodel,
        schemapage_leftnavviewmodel,
        schemapage_rightnavcontroller,
        schemapage_rightnavmodel,
        schemapage_rightnavviewmodel
    ) {

        exports.context =
            injection.makeContext( topnavmodel.provideTopNavModelPageSelection )
                .plus( topnavcontroller.provideTopNavController )
                .plus( topnavviewmodel.provideTopNavViewModelPageVisibilities )
                .plus( schemapage_leftnavmodel.provideSchemaPageLeftTabSelection )
                .plus( schemapage_leftnavcontroller.provideSchemaPageLeftNavController )
                .plus( schemapage_leftnavviewmodel.provideSchemaPageLeftTabVisibilities )
                .plus( schemapage_rightnavmodel.provideSchemaPageRightTabSelection )
                .plus( schemapage_rightnavcontroller.provideSchemaPageRightNavController )
                .plus( schemapage_rightnavviewmodel.provideSchemaPageRightTabVisibilities )

    }
);
