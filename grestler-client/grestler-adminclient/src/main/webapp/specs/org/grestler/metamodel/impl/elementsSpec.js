/**
 * Specification tests for org/grestler/metamodel/impl/elements.
 */

describe( "Grestler Metamodel Implementation Specs", function() {

    beforeEach(function (done) {
        var me = this;

        require(['scripts/ts/org/grestler/metamodel/impl/elements'], function (elements) {
            me.elements = elements;
            done();
        });

    });

    afterEach(function () {
    });

    /////////////////////////////////////////////////////////////////////////////////////////////

    describe( "A root package:", function () {

        var id = '12345678'; // TOD: real UUID

        var pkg;

        beforeEach( function() {
            pkg = new this.elements.RootPackage( id );
        } );

        it( "should initialize its attributes", function () {

            expect(pkg.id).toBe( id );
            expect(pkg.name).toBe( '$' );

        } );

        it( "should have a blank path", function () {

            expect(pkg.path).toBe( '' );

        } );

        it( "should be its own parent", function () {

            expect(pkg.parent).toBe( pkg );
            expect(pkg.parentPackage).toBe( pkg );

        } );

        it( "should not be a child of itself", function () {

            expect(pkg.isChildOf(pkg)).toBeFalsy();

        } );

    } );

    /////////////////////////////////////////////////////////////////////////////////////////////

    describe( "A package:", function () {

        var id1 = '11111111'; // TOD: real UUID
        var id2 = '22222222'; // TOD: real UUID

        var root;
        var pkg1;
        var pkg2;

        beforeEach( function() {
            root = new this.elements.RootPackage( '00000000' );
            pkg1 = new this.elements.Package( id1, root, 'pkg1' );
            pkg2 = new this.elements.Package( id2, pkg1, 'pkg2' );
        } );

        it( "should initialize its attributes", function () {

            expect(pkg1.id).toBe( id1 );
            expect(pkg1.name).toBe( 'pkg1' );

        } );

        it( "should have a correct path", function () {

            expect(pkg1.path).toBe( 'pkg1' );
            expect(pkg2.path).toBe( 'pkg1.pkg2' );

        } );

        it( "should track its parent", function () {

            expect(pkg1.parent).toBe( root );
            expect(pkg1.parentPackage).toBe( root );
            expect(pkg2.parent).toBe( pkg1 );
            expect(pkg2.parentPackage).toBe( pkg1 );

        } );

        it( "should report correct child relationships", function () {

            expect(pkg1.isChildOf(root)).toBeTruthy();
            expect(pkg2.isChildOf(root)).toBeTruthy();
            expect(pkg2.isChildOf(pkg1)).toBeTruthy();

            expect(pkg1.isChildOf(pkg2)).toBeFalsy();
            expect(root.isChildOf(pkg1)).toBeFalsy();
            expect(root.isChildOf(pkg2)).toBeFalsy();

        } );

    } );

} );
