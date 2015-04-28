#
# Specification tests for org/grestler/metamodel/impl/elements.
#
define( [], ()->
  describe( "Grestler Metamodel Implementation Specs", () ->

    #####################################################################################
    beforeEach( ( done ) ->
      me = this

      # define IDs to use
      me.id1 = '11111111' # TOD: real UUID
      me.id2 = '22222222' # TOD: real UUID
      me.id3 = '33333333' # TOD: real UUID
      me.id4 = '44444444' # TOD: real UUID

      # Bring in the elements module before testing
      require( [
          'scripts/js-gen/org/grestler/domain/metamodel/api/elements',
          'scripts/js-gen/org/grestler/domain/metamodel/impl/elements'
        ],
      ( api, impl ) ->
        me.api = api
        me.impl = impl
        done()
      )
    )

    #####################################################################################

    describe( "A root package:", () ->
      pkg = null

      beforeEach( () ->
        pkg = new this.impl.RootPackage( this.id1 )
      )

      it( "should initialize its attributes", () ->
        expect( pkg.id ).toBe( this.id1 )
        expect( pkg.name ).toBe( '$' )
      )

      it( "should have a blank path", () ->
        expect( pkg.path ).toBe( '' )
      )

      it( "should be its own parent", () ->
        expect( pkg.parent ).toBe( pkg )
        expect( pkg.parentPackage ).toBe( pkg )
      )

      it( "should not be a child of itself", () ->
        expect( pkg.isChildOf( pkg ) ).toBeFalsy()
      )
    )

    #####################################################################################

    describe( "A package:", () ->
      root = null
      pkg1 = null
      pkg2 = null

      beforeEach( () ->
        root = new this.impl.RootPackage( '00000000' )
        pkg1 = new this.impl.Package( this.id1, root, 'pkg1' )
        pkg2 = new this.impl.Package( this.id2, pkg1, 'pkg2' )
      )

      it( "should initialize its attributes", () ->
        expect( pkg1.id ).toBe( this.id1 )
        expect( pkg1.name ).toBe( 'pkg1' )
      )

      it( "should have a correct path", () ->
        expect( pkg1.path ).toBe( 'pkg1' )
        expect( pkg2.path ).toBe( 'pkg1.pkg2' )
      )

      it( "should track its parent", () ->
        expect( pkg1.parent ).toBe( root )
        expect( pkg1.parentPackage ).toBe( root )
        expect( pkg2.parent ).toBe( pkg1 )
        expect( pkg2.parentPackage ).toBe( pkg1 )
      )

      it( "should report correct child relationships", () ->
        expect( pkg1.isChildOf( root ) ).toBeTruthy()
        expect( pkg2.isChildOf( root ) ).toBeTruthy()
        expect( pkg2.isChildOf( pkg1 ) ).toBeTruthy()

        expect( pkg1.isChildOf( pkg2 ) ).toBeFalsy()
        expect( root.isChildOf( pkg1 ) ).toBeFalsy()
        expect( root.isChildOf( pkg2 ) ).toBeFalsy()
      )
    )

    #####################################################################################

    describe( "A vertex type:", () ->
      root = null
      pkg1 = null
      vt0 = null
      vt1 = null

      beforeEach( () ->
        root = new this.impl.RootPackage( '00000000' )
        pkg1 = new this.impl.Package( this.id1, root, 'pkg1' )
        vt0 = new this.impl.VertexType( this.id2, pkg1, 'Vertex Type', null, this.api.EAbstractness.ABSTRACT )
        vt1 = new this.impl.VertexType( this.id3, pkg1, 'vt1', vt0, this.api.EAbstractness.CONCRETE )
      )

      it( "should initialize its attributes", () ->
        expect( vt1.id ).toBe( this.id3 )
        expect( vt1.name ).toBe( 'vt1' )
        expect( vt1.abstractness ).toBe( this.api.EAbstractness.CONCRETE )
      )

      it( "should have a correct path", () ->
        expect( vt1.path ).toBe( 'pkg1.vt1' )
      )

      it( "should track its parent", () ->
        expect( vt1.parent ).toBe( pkg1 )
        expect( vt1.parentPackage ).toBe( pkg1 )
      )

      it( "should know its super type", () ->
        expect( vt1.superType ).toBe( vt0 )
      )
    )

    #####################################################################################

    describe( "A directed edge type:", () ->
      root = null
      pkg1 = null
      vt0 = null
      et0 = null
      et1 = null

      beforeEach( () ->
        root = new this.impl.RootPackage( '00000000' )
        pkg1 = new this.impl.Package( this.id1, root, 'pkg1' )
        vt0 = new this.impl.VertexType( this.id2, pkg1, 'Vertex Type', null, this.api.EAbstractness.ABSTRACT )
        et0 = new this.impl.DirectedEdgeType( this.id3,
          pkg1,
          'et1',
          et0,
          this.api.EAbstractness.ABSTRACT,
          this.api.ECyclicity.ACYCLIC,
          this.api.EMultiEdgedness.MULTI_EDGES_NOT_ALLOWED,
          this.api.ESelfLooping.SELF_LOOPS_NOT_ALLOWED,
          vt0,
          vt0,
          null,
          null,
          null,
          null,
          null,
          null )
        et1 = new this.impl.DirectedEdgeType( this.id4,
          pkg1,
          'et1',
          et0,
          this.api.EAbstractness.CONCRETE,
          this.api.ECyclicity.ACYCLIC,
          this.api.EMultiEdgedness.MULTI_EDGES_NOT_ALLOWED,
          this.api.ESelfLooping.SELF_LOOPS_NOT_ALLOWED,
          vt0,
          vt0,
          'tailRole',
          'headRole',
          0,
          5,
          0,
          1 )
      )

      it( "should initialize its attributes", () ->
        expect( et1.id ).toBe( this.id4 )
        expect( et1.name ).toBe( 'et1' )
        expect( et1.abstractness ).toBe( this.api.EAbstractness.CONCRETE )
        expect( et1.cyclicity ).toBe( this.api.ECyclicity.ACYCLIC )
        expect( et1.multiEdgedness ).toBe( this.api.EMultiEdgedness.MULTI_EDGES_NOT_ALLOWED )
        expect( et1.selfLooping ).toBe( this.api.ESelfLooping.SELF_LOOPS_NOT_ALLOWED )
        expect( et1.tailVertexType ).toBe( vt0 )
        expect( et1.headVertexType ).toBe( vt0 )
        expect( et1.tailRoleName ).toBe( 'tailRole' )
        expect( et1.headRoleName ).toBe( 'headRole' )
        expect( et1.minTailOutDegree ).toBe( 0 )
        expect( et1.maxTailOutDegree ).toBe( 5 )
        expect( et1.minHeadInDegree ).toBe( 0 )
        expect( et1.maxHeadInDegree ).toBe( 1 )

        expect( et1.isSimple ).toBeTruthy();
      )

      it( "should have a correct path", () ->
        expect( et1.path ).toBe( 'pkg1.et1' )
      )

      it( "should track its parent", () ->
        expect( et1.parent ).toBe( pkg1 )
        expect( et1.parentPackage ).toBe( pkg1 )
      )

      it( "should know its super type", () ->
        expect( et1.superType ).toBe( et0 )
      )
    )

    #####################################################################################
  )
)
