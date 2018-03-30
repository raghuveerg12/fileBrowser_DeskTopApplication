'use strict';

angular.module('myApp').factory('LoginService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8081/IsonBackupManagement/user/';

    var factory = {        
        validateUser: validateUser,        
        createUser: createUser
    };

    return factory;

    function validateUser(user,username){
    	   var deferred = $q.defer();
           $http.post(REST_SERVICE_URI+'validate',user)
               .then(
               function (response) {
            	   console.log('LoginService response data for login user : ',response.headers("isUserAvailable"));
                   deferred.resolve(response.data);
                   console.log('LoginService - response data for login user : ',response.data);
               },
               function(errResponse){
            	   console.log('LoginService response data is : ',errResponse.headers("isUserAvailable"));
                   console.error('Error while validating User');
                   deferred.reject(errResponse);
               }
           );
           return deferred.promise;
    }
    
    function createUser(user) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, user)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


}]);
