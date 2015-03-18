#
# Specification tests for org/grestler/metamodel/impl/elements.
#
define( [], ()->
  describe( "Grestler REST Server Queries Specs", () ->

    #####################################################################################
    beforeEach( ( done ) ->
      me = this

      # Bring in the elements module before testing
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

      it( "should load packages", (done) ->
        loadCount = 0

        loader = new this.restserver_api_queries.PackageLoader()

        repository = {
          findOptionalPackageById: ( id ) ->
            null

          loadPackage: ( id, parentPackage, name ) ->
            loadCount += 1

          loadRootPackage: ( id ) ->
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
