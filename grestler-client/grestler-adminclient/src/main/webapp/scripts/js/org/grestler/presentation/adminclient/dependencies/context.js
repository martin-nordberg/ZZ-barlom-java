//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

define(
    [
        'require',
        'exports',
        'scripts/js-gen/org/grestler/infrastructure/dependencies/injection',
        'scripts/js-gen/org/grestler/presentation/adminclient/main/module',
        'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/module'
    ],
    function (
        require,
        exports,
        injection,
        main_module,
        schemapage_module
    ) {

        exports.context =
            injection.makeContext()
                .plusModule( main_module.mainModule )
                .plusModule( schemapage_module.schemaPageModule );

    }
);
