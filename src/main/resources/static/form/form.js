angular.module('simple-web-form').controller('formController', function ($scope, $http) {
    const contextRecordPath = 'http://localhost:8000/web-form/api/v1/records';
    const contextPrivilegePath = 'http://localhost:8000/web-form/api/v1/privileges';
    $scope.recordDto = {
        id: null, firstname: '', lastname: '', date_of_birth: null,
        recording_time: null, recording_direction: null, status: null, privilegesDto: []
    };
    $scope.newPrivilegesDto = new Set();
    $scope.directionSet = {
        DIRECTION_1: "DIRECTION_1",
        DIRECTION_2: "DIRECTION_2",
        DIRECTION_3: "DIRECTION_3",
        DIRECTION_4: "DIRECTION_4",
        DIRECTION_5: "DIRECTION_5"
    };

    $scope.recordId = null;
    $scope.isRecordSetVisible = false;

    $scope.createNewRecord = function () {
        if ($scope.newPrivilegesDto.size !== 0) {
            $scope.recordDto.privilegesDto = Array.from($scope.newPrivilegesDto);
        }
        console.log($scope.recordDto);
        $http({
            url: contextRecordPath,
            method: 'POST',
            data: $scope.recordDto
        })
            .then(function successCallback(response) {
                document.getElementById('response').innerHTML = response.data.lastname + ' created';
                $scope.recordDto = {
                    id: null, firstname: '', lastname: '', date_of_birth: null,
                    recording_time: null, recording_direction: null, status: null, privilegesDto: []
                };
                $scope.newPrivilegesDto.clear();
                $scope.errors = null;
            }, function errorCallback(response) {
                $scope.errors = response.data.list;
            });
    };

    $scope.addPrivilegeDto = function (privilege) {
        if ($scope.newPrivilegesDto.has(privilege)) {
            $scope.newPrivilegesDto.delete(privilege);
        } else {
            $scope.newPrivilegesDto.add(privilege);
        }
    };

    $scope.getAllRecordsDto = function () {
        $http.get(contextRecordPath)
            .then(function successCallback(response) {
                $scope.recordSet = response.data;
                $scope.isRecordSetVisible = true;
            }, function errorCallback() {
                alert("Error");
            });
    };

    $scope.getAllPrivilegesDto = function () {
        $http.get(contextPrivilegePath)
            .then(function successCallback(response) {
                $scope.privilegesArray = Array.from(response.data);
            }, function errorCallback() {
                alert("Error");
            });
    };

    $scope.cleanRecordDto = function () {
        $scope.recordDto = {
            id: null, firstname: '', lastname: '', date_of_birth: null,
            recording_time: null, recording_direction: null, status: null, privilegesDto: []
        };
        $scope.newPrivilegesDto.clear();
        $scope.cleanResponse();
    };

    $scope.fillNewPrivilegesDto = function () {
        if ($scope.recordDto.date_of_birth !== null) {
            $scope.recordDto.date_of_birth = new Date($scope.recordDto.date_of_birth);
            $scope.recordDto.recording_time = new Date($scope.recordDto.recording_time);
        }
        if ($scope.recordDto.privilegesDto.length !== 0) {
            for (let i = 0; i < $scope.privilegesArray.length; i++) {
                for (let j = 0; j < $scope.recordDto.privilegesDto.length; j++) {
                    if ($scope.privilegesArray[i].id === $scope.recordDto.privilegesDto[j].id) {
                        $scope.newPrivilegesDto.add($scope.privilegesArray[i]);
                    }
                }
            }
        }
        console.log($scope.recordDto);
    };

    $scope.isPrivilegeIncludes = function (id) {
        for (let i = 0; i < $scope.recordDto.privilegesDto.length; i++) {
            if ($scope.recordDto.privilegesDto[i].id === id) {
                return true;
            }
        }
        return false;
    };

    $scope.deleteRecord = function (id) {
        $http.delete(contextRecordPath + '/' + id)
            .then(function successCallback() {
                document.getElementById('response').innerHTML = $scope.recordDto.lastname + ' deleted';
                $scope.recordDto = {
                    id: null, firstname: '', lastname: '', date_of_birth: null,
                    recording_time: null, recording_direction: null, status: null, privilegesDto: []
                };
                $scope.newPrivilegesDto.clear();
                $scope.errors = null;
            }, function errorCallback() {
                alert("Error");
            });
    };

    $scope.cleanResponse = function () {
        document.getElementById('response').innerHTML = '';
        $scope.errors = null;
    };

    $scope.getAllPrivilegesDto();
});