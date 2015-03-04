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

} );
