function FacilityListCtrl($scope, $http) {
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
