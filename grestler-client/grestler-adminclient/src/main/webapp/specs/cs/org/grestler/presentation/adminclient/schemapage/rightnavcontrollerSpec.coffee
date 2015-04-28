#
# Specification tests for org/grestler/presentation/adminclient/schemapage/rightnavcontroller.
#
define( [], ()->
  describe( "Grestler Admin Client Schema Page Right Nav Specs", () ->

    #####################################################################################
    beforeEach( ( done ) ->
      me = this

      # Bring in the queries module before testing
      require( [
          'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/rightnavcontroller',
          'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/rightnavmodel',
          'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/rightnavviewmodel'
        ], ( rightnavcontroller, rightnavmodel, rightnavviewmodel ) ->
        me.rightnavcontroller = rightnavcontroller
        me.rightnavmodel = rightnavmodel
        me.rightnavviewmodel = rightnavviewmodel
        done()
      )
    )

    #####################################################################################

    describe( "A right nav controller:", () ->
      beforeEach( () ->
        this.model = new this.rightnavmodel.RightTabSelection();
        this.controller = new this.rightnavcontroller.RightNavController( this.model );
        this.viewmodel = new this.rightnavviewmodel.RightTabVisibilities( this.model );

        this.viewmodel.observeModelChanges();
      )

      it( "should set the visibility of the Diagrams tab", ( done ) ->
        me = this

        me.controller.onDiagramsTabClicked();

        setTimeout( () ->
          expect( me.viewmodel.isDiagramsTabActive ).toBeTruthy();
          expect( me.viewmodel.isDocumentationTabActive ).toBeFalsy();
          expect( me.viewmodel.isPropertiesTabActive ).toBeFalsy();
          done();
        , 0 );
      )

      it( "should set the visibility of the Documentation tab", ( done ) ->
        me = this

        me.controller.onDocumentationTabClicked();

        setTimeout( () ->
          expect( me.viewmodel.isDiagramsTabActive ).toBeFalsy();
          expect( me.viewmodel.isDocumentationTabActive ).toBeTruthy();
          expect( me.viewmodel.isPropertiesTabActive ).toBeFalsy();
          done();
        , 0 );
      )

      it( "should set the visibility of the Properties tab", ( done ) ->
        me = this

        me.controller.onPropertiesTabClicked();

        setTimeout( () ->
          expect( me.viewmodel.isDiagramsTabActive ).toBeFalsy();
          expect( me.viewmodel.isDocumentationTabActive ).toBeFalsy();
          expect( me.viewmodel.isPropertiesTabActive ).toBeTruthy();
          done();
        , 0 );
      )
    )
  )
)
