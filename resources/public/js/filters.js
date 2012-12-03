var MONTHS = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

angular.module('abqfoodFilters', []).filter("prettydate", function() {
    return function(input) {
        var day = new Date(input);
        return "" + MONTHS[day.getMonth()] + " " + day.getDate() + ", " + day.getFullYear();
    };
});
