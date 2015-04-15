//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

define(
    [
        'require',
        'exports',
        'scripts/js-gen/org/grestler/infrastructure/dependencies/injection',
        'scripts/js-gen/org/grestler/domain/metamodel/impl/module',
        'scripts/js-gen/org/grestler/persistence/restserver/api/module',
        'scripts/js-gen/org/grestler/presentation/adminclient/console/module',
        'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/module'
    ],
    function (
        require,
        exports,
        injection,
        metamodel_impl_module,
        restserver_api_module,
        console_module,
        schemapage_module
    ) {

        exports.context =
            injection.makeContext()
                .plusModule( metamodel_impl_module.metamodelImplModule )
                .plusModule( restserver_api_module.restserverApiModule )
                .plusModule( console_module.consoleModule )
                .plusModule( schemapage_module.schemaPageModule );

    }
);
