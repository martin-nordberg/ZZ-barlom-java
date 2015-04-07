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

        var module = {

            provideA: function provideA() {
                return "A";
            },

            provideB: function provideB( a ) {
                return a + "B";
            }

        };

        exports.provideA = provideA;
        exports.provideB = provideB;
        exports.provideC = provideC;
        exports.module = module;

    }
);