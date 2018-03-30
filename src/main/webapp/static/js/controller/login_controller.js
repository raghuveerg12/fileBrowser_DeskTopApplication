'use strict';

angular.module('myApp').controller('LoginController', [ '$scope', 'LoginService', '$location', function($scope, LoginService, $location) {
	var self = this;
	self.user = {
		id : null,
		username : '',
		password : ''
	};
	self.users = [];

	self.login = login;
	self.register = register;


	function validateUser(user) {
		LoginService.validateUser(user, self.user.username)
			.then(
				function(d) {
					console.log('User validated : ',d);
					var locationPath = $location.absUrl().substr(0,$location.absUrl().lastIndexOf("?"))+'index.html';
					console.log('location path : ',$location.absUrl().substr(0,$location.absUrl().lastIndexOf("?")));
					window.location.href = $location.absUrl().substr(0,$location.absUrl().lastIndexOf("?"))+'index.html';
				},
				function(errResponse) {
					console.error('Error while validating User');
				}
		);
	}

	function login() {
		if (self.user.username === null) {
			console.log('User Logging in', self.user);
		} else {
			console.log('user to be logged in');
			validateUser(self.user);
			console.log('User logged in ', self.user);
		}
	}

	function register() {
		console.log('user is about to be registered');
		if (self.user.username === null) {
			console.log('User Logging in', self.user);
		} else {
			LoginService.createUser(user)
				.then(
				function(d) {
					console.log('User registered : ',d);
				},
					function(errResponse) {
						console.error('Error while creating User');
					}
			);
		}

	}

} ]);