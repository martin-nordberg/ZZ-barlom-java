#
# Specification tests for org/grestler/infrastructure/reflection/functionintrospection
#
define( [], ()->
  describe( "Function Introspection Specs", () ->

    #####################################################################################
    beforeEach( ( done ) ->
      me = this

      # Bring in the functionintrospection module before testing
      require( ['scripts/js-gen/org/grestler/infrastructure/reflection/functionintrospection'],
      ( functionintrospection ) ->
        me.functionintrospection = functionintrospection
        done()
      )
    )

    #####################################################################################

    describe( "A function description:", () ->
      fnDesc = null

      beforeEach( () ->
        fn = ( a, b, c ) -> a + b + c
        fnDesc = this.functionintrospection.describeFunction( fn )
      )

      it( "should initialize parameter names", () ->
        expect( fnDesc.parameters.length ).toBe( 3 )
        expect( fnDesc.parameters[0].name ).toBe( 'a' )
        expect( fnDesc.parameters[1].name ).toBe( 'b' )
        expect( fnDesc.parameters[2].name ).toBe( 'c' )
      )

      it( "should initialize the function name", () ->
        fnDesc = this.functionintrospection.describeFunction( this.functionintrospection.describeFunction )
        expect( fnDesc.name ).toBe( 'describeFunction' )
      )

      it( "should describe zero parameters", () ->
        fn0 = () -> "stuff"
        fnDesc = this.functionintrospection.describeFunction( fn0 )
        expect( fnDesc.parameters.length ).toBe( 0 )
      )
    )

    #####################################################################################
  )
)
