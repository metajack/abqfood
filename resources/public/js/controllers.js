function TopCtrl($scope) {
    var spinner = new Spinner({top: "50px"}).spin(document.getElementsByTagName("body")[0]);
    spinner.stop();
    $scope.menu = [{link: "/facilities", name: "By Name"},
                   {link: "/results", name: "By Result"},
                   {link: "/inspections", name: "By Date"}];
}

function FacilitiesCtrl($scope, $http) {
    var spinner = new Spinner({top: "50px"}).spin(document.getElementsByTagName("body")[0]);
    $http.
        get("/api/facilities").
        success(function(data) {
            $scope.facilities = data;
            spinner.stop();
        });

    $scope.orderProp = "name";
}

function FacilityCtrl($scope, $http, $routeParams) {
    var spinner = new Spinner({top: "50px"}).spin(document.getElementsByTagName("body")[0]);
    $http.
        get("/api/facility/" + $routeParams.fid).
        success(function(data) {
            $scope.facility = data;
            spinner.stop();
        });

    $scope.orderProp = "-date";
}

function FacilityInspectionCtrl($scope, $http, $routeParams) {
    var spinner = new Spinner({top: "50px"}).spin(document.getElementsByTagName("body")[0]);
    $http.
        get("/api/facility/" + $routeParams.fid + "/inspection/" + $routeParams.iid).
        success(function(data) {
            $scope.inspection = data;
            spinner.stop();
        });
}

function ResultsCtrl($scope, $http) {
    var spinner = new Spinner({top: "50px"}).spin(document.getElementsByTagName("body")[0]);
    $http.
        get("/api/results").
        success(function(data) {
            $scope.results = data;
            spinner.stop();
        });

    $scope.orderProp = "name";
}

function InspectionsCtrl($scope, $http, $routeParams) {
    var spinner = new Spinner({top: "50px"}).spin(document.getElementsByTagName("body")[0]);
    $http.
        get("/api/inspections" + (!$routeParams.rid ? "" : "/" + $routeParams.rid)).
        success(function(data) {
            $scope.inspections = data;
            spinner.stop();
        });

    $scope.orderProp = "-date";
}
