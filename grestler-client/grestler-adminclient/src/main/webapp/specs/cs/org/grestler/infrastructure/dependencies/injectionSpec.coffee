#
# Specification tests for org/grestler/infrastructure/reflection/functionintrospection
#
define( [], ()->
  describe( "Dependency Injection Specs", () ->

    #####################################################################################
    beforeEach( ( done ) ->
      me = this

      # Bring in the injection module before testing
      require( [
          'scripts/js-gen/org/grestler/infrastructure/dependencies/injection',
          'specs/js/org/grestler/infrastructure/dependencies/injectionProviders'
        ],
      ( injection, providers ) ->
        me.injection = injection
        me.providers = providers
        done()
      )
    )

    #####################################################################################

    describe( "A function description:", () ->

      provideA = null

      beforeEach( () ->
        provideA = this.providers.provideA
      )

      it( "should provide one kind of object", () ->
        context = this.injection.makeContext( provideA )
        expect( context.get( 'a' ) ).toEqual( "A" )
      )
    )

    #####################################################################################
  )
)
