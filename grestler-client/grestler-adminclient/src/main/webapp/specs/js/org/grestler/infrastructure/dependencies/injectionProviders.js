define(
    ["require", "exports"], function ( require, exports ) {

        function provideA() {
            return "A";
        }

        function provideB( a ) {
            return a + "B";
        }

        function provideC( a, b ) {
            return a + b + "C";
        }

        exports.provideA = provideA;
        exports.provideB = provideB;
        exports.provideC = provideC;

    }
);