angular.module("abqfood", ["abqfoodFilters"]).
    config(["$routeProvider", function($routeProvider) {
        $routeProvider.
            when("/top", {templateUrl: "partials/top.html",
                          controller: TopCtrl}).
            when("/facilities", {templateUrl: "partials/facilities.html",
                                 controller: FacilitiesCtrl}).
            when("/facility/:fid", {templateUrl: "partials/facility.html",
                                    controller: FacilityCtrl}).
            when("/facility/:fid/inspection/:iid", {templateUrl: "partials/facility_inspection.html",
                                                    controller: FacilityInspectionCtrl}).
            when("/inspections", {templateUrl: "partials/inspections.html",
                                  controller: InspectionsCtrl}).
            when("/results", {templateUrl: "partials/results.html",
                              controller: ResultsCtrl}).
            when("/inspections/:rid", {templateUrl: "partials/inspections.html",
                                       controller: InspectionsCtrl}).
            otherwise({redirectTo: "/top"});
    }]);
