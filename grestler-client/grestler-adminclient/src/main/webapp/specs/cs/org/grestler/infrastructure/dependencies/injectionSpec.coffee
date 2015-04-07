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
      provideB = null
      provideC = null
      module = null

      beforeEach( () ->
        provideA = this.providers.provideA
        provideB = this.providers.provideB
        provideC = this.providers.provideC
        module = this.providers.module
      )

      it( "should provide one kind of object", () ->
        context = this.injection.makeContext().plus( provideA )
        expect( context.get( 'a' ) ).toEqual( "A" )
      )

      it( "should satisfy dependencies", () ->
        context = this.injection.makeContext().plus( provideA ).plus( provideB ).plus( provideC )
        expect( context.get( 'a' ) ).toEqual( "A" )
        expect( context.get( 'b' ) ).toEqual( "AB" )
        expect( context.get( 'c' ) ).toEqual( "AABC" )
      )

      it( "should satisfy dependencies when configured in reverse", () ->
        context = this.injection.makeContext().plus( provideC ).plus( provideB ).plus( provideA )
        expect( context.get( 'a' ) ).toEqual( "A" )
        expect( context.get( 'b' ) ).toEqual( "AB" )
        expect( context.get( 'c' ) ).toEqual( "AABC" )
      )

      it( "should satisfy dependencies via a module", () ->
        context = this.injection.makeContext().plus( provideC ).plusModule( module )
        expect( context.get( 'a' ) ).toEqual( "A" )
        expect( context.get( 'b' ) ).toEqual( "AB" )
        expect( context.get( 'c' ) ).toEqual( "AABC" )
      )
    )

    #####################################################################################
  )
)
