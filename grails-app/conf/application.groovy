import grails.plugin.springsecurity.SecurityConfigType

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.bogdan.UserSec'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.bogdan.UserAuthority'
grails.plugin.springsecurity.authority.className = 'com.bogdan.Authority'
grails.plugin.springsecurity.securityConfigType = SecurityConfigType.InterceptUrlMap
grails.plugin.springsecurity.interceptUrlMap = [
	[pattern: '/',               	access: ['permitAll']],
	[pattern: '/error',          	access: ['permitAll']],
	[pattern: '/index',         	access: ['permitAll']],
	[pattern: '/index.gsp',      	access: ['permitAll']],
	[pattern: '/shutdown',       	access: ['permitAll']],
	[pattern: '/assets/**',      	access: ['permitAll']],
	[pattern: '/**/js/**',       	access: ['permitAll']],
	[pattern: '/**/css/**',      	access: ['permitAll']],
	[pattern: '/**/images/**',   	access: ['permitAll']],
	[pattern: '/**/favicon.ico', 	access: ['permitAll']],
	[pattern: '/api/login',			access: ['permitAll']],
	[pattern: '/api/register', 	 	access: ['permitAll']],
	[pattern: '/api/users', 	   	access: ['permitAll']],
	[pattern: '/api/users/**', 	   	access: ['permitAll'], httpMethod: 'GET'],
	[pattern: '/**/messages',		access: ['permitAll'], httpMethod: 'GET'],
	[pattern: '/api/logout', 	   	access: ['isFullyAuthenticated()']],
	[pattern: '/**/messages/**',	access: ['isFullyAuthenticated()']],
	[pattern: '/api/users/**',    	access: ['isFullyAuthenticated()']],
	[pattern: '/**',             	access: ['isFullyAuthenticated()']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/api/**', filters:'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'],
  	[pattern: '/**', filters:'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
]

grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'
grails.plugin.springsecurity.rest.token.validation.useBearerToken = false
grails.plugin.springsecurity.rest.token.validation.headerName = 'X-Auth-Token'
grails.plugin.springsecurity.rest.token.storage.memcached.hosts = 'localhost:11211'
grails.plugin.springsecurity.rest.token.storage.memcached.username = ''
grails.plugin.springsecurity.rest.token.storage.memcached.password = ''
grails.plugin.springsecurity.rest.token.storage.memcached.expiration = 86400
