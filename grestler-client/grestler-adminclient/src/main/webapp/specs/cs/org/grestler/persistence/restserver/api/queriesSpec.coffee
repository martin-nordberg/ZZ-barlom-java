#
# Specification tests for org/grestler/persistence/restserver/api/queries.
#
define( [], ()->
  describe( "Grestler REST Server Queries Specs", () ->

    #####################################################################################
    beforeEach( ( done ) ->
      me = this

      # Bring in the queries module before testing
      require( [
          'scripts/js-gen/org/grestler/persistence/restserver/api/queries'
        ], ( restserver_api_queries ) ->
        me.restserver_api_queries = restserver_api_queries
        done()
      )
    )

    #####################################################################################

    describe( "A package loader:", () ->
      beforeEach( () ->
      )

      it( "should load packages", ( done ) ->
        loadCount = 0
        packages = {}

        loader = new this.restserver_api_queries.PackageLoader()

        repository = {
          findPackageById: ( id ) ->
            packages[id]

          findOptionalPackageById: ( id ) ->
            packages[id]

          loadPackage: ( id, parentPackage, name ) ->
            packages[id] = { id: id }
            loadCount += 1

          loadRootPackage: ( id ) ->
            packages[id] = { id: id }
            loadCount += 1
        }

        loader.loadAllPackages( repository ).then( () ->
          expect( loadCount ).toBeTruthy()
          done()
        )
      )
    )
  )
)
