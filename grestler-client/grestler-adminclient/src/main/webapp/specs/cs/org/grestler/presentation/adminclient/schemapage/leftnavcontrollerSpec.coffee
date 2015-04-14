#
# Specification tests for org/grestler/presentation/adminclient/schemapage/leftnavcontroller.
#
define( [], ()->
  describe( "Grestler Admin Client Schema Page Left Nav Specs", () ->

    #####################################################################################
    beforeEach( ( done ) ->
      me = this

      # Bring in the queries module before testing
      require( [
          'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/leftnavcontroller',
          'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/leftnavmodel',
          'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/leftnavviewmodel'
        ], ( leftnavcontroller, leftnavmodel, leftnavviewmodel ) ->
        me.leftnavcontroller = leftnavcontroller
        me.leftnavmodel = leftnavmodel
        me.leftnavviewmodel = leftnavviewmodel
        done()
      )
    )

    #####################################################################################

    describe( "A left nav controller:", () ->
      beforeEach( () ->
        this.model = new this.leftnavmodel.LeftTabSelection();
        this.controller = new this.leftnavcontroller.LeftNavController( this.model );
        this.viewmodel = new this.leftnavviewmodel.LeftTabVisibilities( this.model );

        this.viewmodel.observeModelChanges();
      )

      it( "should set the visibility of the Bookmarks tab", ( done ) ->
        me = this

        me.controller.onBookmarksTabClicked();

        setTimeout( () ->
          expect( me.viewmodel.isBookmarksTabActive ).toBeTruthy();
          expect( me.viewmodel.isBrowseTabActive ).toBeFalsy();
          expect( me.viewmodel.isRecentTabActive ).toBeFalsy();
          expect( me.viewmodel.isSearchTabActive ).toBeFalsy();
          done();
        , 0 );
      )

      it( "should set the visibility of the Browse tab", ( done ) ->
        me = this

        me.controller.onBrowseTabClicked();

        setTimeout( () ->
          expect( me.viewmodel.isBookmarksTabActive ).toBeFalsy();
          expect( me.viewmodel.isBrowseTabActive ).toBeTruthy();
          expect( me.viewmodel.isRecentTabActive ).toBeFalsy();
          expect( me.viewmodel.isSearchTabActive ).toBeFalsy();
          done();
        , 0 );
      )

      it( "should set the visibility of the Bookmarks tab", ( done ) ->
        me = this

        me.controller.onRecentTabClicked();

        setTimeout( () ->
          expect( me.viewmodel.isBookmarksTabActive ).toBeFalsy();
          expect( me.viewmodel.isBrowseTabActive ).toBeFalsy();
          expect( me.viewmodel.isRecentTabActive ).toBeTruthy();
          expect( me.viewmodel.isSearchTabActive ).toBeFalsy();
          done();
        , 0 );
      )

      it( "should set the visibility of the Bookmarks tab", ( done ) ->
        me = this

        me.controller.onSearchTabClicked();

        setTimeout( () ->
          expect( me.viewmodel.isBookmarksTabActive ).toBeFalsy();
          expect( me.viewmodel.isBrowseTabActive ).toBeFalsy();
          expect( me.viewmodel.isRecentTabActive ).toBeFalsy();
          expect( me.viewmodel.isSearchTabActive ).toBeTruthy();
          done();
        , 0 );
      )

    )
  )
)
