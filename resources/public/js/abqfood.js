angular.module("abqfood", ["abqfoodFilters"]).
    config(["$routeProvider", function($routeProvider) {
        $routeProvider.
            when("/facilities", {templateUrl: "partials/facilities.html",
                                 controller: FacilityListCtrl}).
            when("/facility/:fid", {templateUrl: "partials/facility.html",
                                    controller: FacilityCtrl}).
            when("/facility/:fid/inspection/:iid", {templateUrl: "partials/facility_inspection.html",
                                                    controller: FacilityInspectionCtrl}).
            otherwise({redirectTo: "/facilities"});
    }]);
