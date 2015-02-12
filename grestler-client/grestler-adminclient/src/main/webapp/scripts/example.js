define(["require", "exports"], function (require, exports) {
    var Example = (function () {
        function Example() {
            this.modifier = "cruel";
            this.name = "world";
        }
        return Example;
    })();
    exports.Example = Example;
});
