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
      provideSingletonD = null
      provideE = null
      module = null

      beforeEach( () ->
        provideA = this.providers.provideA
        provideB = this.providers.provideB
        provideC = this.providers.provideC
        provideSingletonD = this.providers.provideSingletonD
        provideE = this.providers.provideE
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

      it( "should store singletons for repeated use", () ->
        context = this.injection.makeContext().plus( provideA ).plus( provideSingletonD )
        d1 = context.get( 'd' )
        d2 = context.get( 'd' )
        d1.changed = true
        expect( d2.changed ).toBeTruthy()
      )

      it( "should store singletons from a module for repeated use", () ->
        context = this.injection.makeContext().plus( provideA ).plusModule( module )
        d1 = context.get( 'd' )
        d2 = context.get( 'd' )
        d1.changed = true
        expect( d2.changed ).toBeTruthy()
      )

      it( "should provide distinct instances of non-singletons", () ->
        context = this.injection.makeContext().plus( provideE ).plusModule( module )
        e1 = context.get( 'e' )
        e2 = context.get( 'e' )
        e1.changed = true
        expect( e2.changed ).toBeFalsy()
      )

    )

    #####################################################################################
  )
)
