#
# Specification tests for org/grestler/presentation/adminclient/console/topnavcontroller.
#
define( [], ()->
  describe( "Grestler Admin Client Console Top Nav Specs", () ->

    #####################################################################################
    beforeEach( ( done ) ->
      me = this

      # Bring in the queries module before testing
      require( [
          'scripts/js-gen/org/grestler/presentation/adminclient/console/topnavcontroller',
          'scripts/js-gen/org/grestler/presentation/adminclient/console/topnavmodel',
          'scripts/js-gen/org/grestler/presentation/adminclient/console/topnavviewmodel'
        ], ( topnavcontroller, topnavmodel, topnavviewmodel ) ->
        me.topnavcontroller = topnavcontroller
        me.topnavmodel = topnavmodel
        me.topnavviewmodel = topnavviewmodel
        done()
      )
    )

    #####################################################################################

    describe( "A top nav controller:", () ->
      beforeEach( () ->
        this.model = new this.topnavmodel.PageSelection();
        this.controller = new this.topnavcontroller.TopNavController( this.model );
        this.viewmodel = new this.topnavviewmodel.PageVisibilities( this.model );

        this.viewmodel.observeModelChanges();
      )

      it( "should set the visibility of the Queries page", ( done ) ->
        me = this

        me.controller.onQueriesClicked();

        setTimeout( () ->
          expect( me.viewmodel.isQueriesPageActive ).toBeTruthy();
          expect( me.viewmodel.isSchemaPageActive ).toBeFalsy();
          expect( me.viewmodel.isServerPageActive ).toBeFalsy();
          done();
        , 0 );
      )

      it( "should set the visibility of the Schema page", ( done ) ->
        me = this

        me.controller.onSchemaClicked();

        setTimeout( () ->
          expect( me.viewmodel.isQueriesPageActive ).toBeFalsy();
          expect( me.viewmodel.isSchemaPageActive ).toBeTruthy();
          expect( me.viewmodel.isServerPageActive ).toBeFalsy();
          done();
        , 0 );
      )

      it( "should set the visibility of the Server page", ( done ) ->
        me = this

        me.controller.onServerClicked();

        setTimeout( () ->
          expect( me.viewmodel.isQueriesPageActive ).toBeFalsy();
          expect( me.viewmodel.isSchemaPageActive ).toBeFalsy();
          expect( me.viewmodel.isServerPageActive ).toBeTruthy();
          done();
        , 0 );
      )
    )
  )
)
