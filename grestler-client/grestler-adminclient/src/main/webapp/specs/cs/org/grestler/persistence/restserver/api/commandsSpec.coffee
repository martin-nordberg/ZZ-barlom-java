#
# Specification tests for org/grestler/persistence/restserver/api/queries.
#
define( [], ()->
  describe( "Grestler REST Server Commands Specs", () ->

    #####################################################################################
    beforeEach( ( done ) ->
      me = this

      # Bring in the needed modules before testing
      require( [
          'scripts/js-gen/org/grestler/infrastructure/utilities/uuids',
          'scripts/js-gen/org/grestler/domain/metamodel/impl/commands',
          'scripts/js-gen/org/grestler/persistence/restserver/api/commands'
        ], ( uuids, metamodel_impl_commands, restserver_api_commands ) ->
        me.metamodel_impl_commands = metamodel_impl_commands
        me.restserver_api_commands = restserver_api_commands
        me.uuids = uuids
        done()
      )
    )

    #####################################################################################

    describe( "A package creation command:", () ->
      beforeEach( () ->
      )

      it( "should create a package", ( done ) ->
        loadCount = 0

        repository = {
          findPackageById: ( id ) ->
            null

          loadPackage: ( id, parentPackage, name ) ->
            loadCount += 1

          loadRootPackage: ( id ) ->
            loadCount += 1
        }

        cmdWriterFactory = new this.restserver_api_commands.MetamodelCommandWriterFactory()
        cmdFactory = new this.metamodel_impl_commands.MetamodelCommandFactory( repository, cmdWriterFactory )

        cmdId = this.uuids.makeUuid();
        pkgId = this.uuids.makeUuid();
        rootPkgId = "00000000-7a26-11e4-a545-08002741a702"

        pkgJson = { cmdId: cmdId, id: pkgId, parentPackageId: rootPkgId, name: "restsample" + pkgId.substring( 0, 8 ) }

        cmd = cmdFactory.makeCommand( "packagecreation" )

        cmd.execute( pkgJson ).then( () ->
          expect( loadCount ).toBeTruthy()
          done()
        )
      )
    )
  )
)
