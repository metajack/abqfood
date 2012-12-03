function TopCtrl($scope) {
    $scope.menu = [{link: "/facilities", name: "By Name"},
                   {link: "/results", name: "By Result"},
                   {link: "/inspections", name: "By Date"}];
}

function FacilitiesCtrl($scope, $http) {
    $http.
        get("/api/facilities").
        success(function(data) {
            $scope.facilities = data;
        });

    $scope.orderProp = "name";
}

function FacilityCtrl($scope, $http, $routeParams) {
    $http.
        get("/api/facility/" + $routeParams.fid).
        success(function(data) {
            $scope.facility = data;
        });

    $scope.orderProp = "-date";
}

function FacilityInspectionCtrl($scope, $http, $routeParams) {
    $http.
        get("/api/facility/" + $routeParams.fid + "/inspection/" + $routeParams.iid).
        success(function(data) {
            $scope.inspection = data;
        });
}

function ResultsCtrl($scope, $http) {
    $http.
        get("/api/results").
        success(function(data) {
            $scope.results = data;
        });

    $scope.orderProp = "name";
}

function InspectionsCtrl($scope, $http, $routeParams) {
    $http.
        get("/api/inspections" + (!$routeParams.rid ? "" : "/" + $routeParams.rid)).
        success(function(data) {
            $scope.inspections = data;
        });

    $scope.orderProp = "-date";
}
