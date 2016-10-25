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

        function provideSingletonD( a ) {
            return { a: a, d: true };
        }

        function provideE( a, b ) {
            return { a:a, b:b, e:true };
        }

        var module = {

            provideA: function provideA() {
                return "A";
            },

            provideB: function provideB( a ) {
                return a + "B";
            },

            provideSingletonD: function provideSingletonD( a ) {
                return { a: a, d: true };
            }

        };

        exports.provideA = provideA;
        exports.provideB = provideB;
        exports.provideC = provideC;
        exports.provideSingletonD = provideSingletonD;
        exports.provideE = provideE;
        exports.module = module;

    }
);