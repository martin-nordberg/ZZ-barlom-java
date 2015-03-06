#
# Specification tests for org/grestler/metamodel/impl/elements.
#
define( [], ()->

  describe( "Grestler Metamodel Implementation Specs", () ->

    beforeEach( (done) ->

      me = this

      require( ['scripts/js-gen/org/grestler/domain/metamodel/impl/elements'], (elements) ->
        me.elements = elements
        done()
      )

    )

    #####################################################################################

    describe( "A root package:", () ->

      id = '12345678' # TOD: real UUID

      pkg = null

      beforeEach( () ->
        pkg = new this.elements.RootPackage( id )
      )

      it( "should initialize its attributes", () ->
        expect(pkg.id).toBe( id )
        expect(pkg.name).toBe( '$' )
      )

      it( "should have a blank path", () ->
        expect(pkg.path).toBe( '' )
      )

      it( "should be its own parent", () ->
        expect(pkg.parent).toBe( pkg )
        expect(pkg.parentPackage).toBe( pkg )
      )

      it( "should not be a child of itself", () ->
        expect(pkg.isChildOf(pkg)).toBeFalsy()
      )

    )

    #####################################################################################

    describe( "A package:", () ->

      id1 = '11111111' # TOD: real UUID
      id2 = '22222222' # TOD: real UUID

      root = null
      pkg1 = null
      pkg2 = null

      beforeEach( () ->
        root = new this.elements.RootPackage( '00000000' )
        pkg1 = new this.elements.Package( id1, root, 'pkg1' )
        pkg2 = new this.elements.Package( id2, pkg1, 'pkg2' )
      )

      it( "should initialize its attributes", () ->
        expect(pkg1.id).toBe( id1 )
        expect(pkg1.name).toBe( 'pkg1' )
      )

      it( "should have a correct path", () ->
        expect(pkg1.path).toBe( 'pkg1' )
        expect(pkg2.path).toBe( 'pkg1.pkg2' )
      )

      it( "should track its parent", () ->
        expect(pkg1.parent).toBe( root )
        expect(pkg1.parentPackage).toBe( root )
        expect(pkg2.parent).toBe( pkg1 )
        expect(pkg2.parentPackage).toBe( pkg1 )
      )

      it( "should report correct child relationships", () ->
        expect(pkg1.isChildOf(root)).toBeTruthy()
        expect(pkg2.isChildOf(root)).toBeTruthy()
        expect(pkg2.isChildOf(pkg1)).toBeTruthy()

        expect(pkg1.isChildOf(pkg2)).toBeFalsy()
        expect(root.isChildOf(pkg1)).toBeFalsy()
        expect(root.isChildOf(pkg2)).toBeFalsy()
      )

    )

    #####################################################################################

  )

)
