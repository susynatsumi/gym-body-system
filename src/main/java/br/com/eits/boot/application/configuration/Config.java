package br.com.eits.boot.application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import br.com.eits.boot.application.configuration.jwt.JwtConfigurer;
import br.com.eits.boot.application.configuration.jwt.TokenProvider;
import br.com.eits.boot.application.security.AuthenticationFailureHandler;
import br.com.eits.boot.application.security.AuthenticationSuccessHandler;

@EnableWebSecurity
public class Config {
	
	@Order(1)
	@Configuration
	public static class RestSecurityConfiguration// { 
		extends WebSecurityConfigurerAdapter {

		private final TokenProvider tokenProvider;
		
		/**
		 * Constructor
		 * @param tokenProvide
		 */
		@Autowired
		public RestSecurityConfiguration(TokenProvider tokenProvide) {
			this.tokenProvider = tokenProvide;
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
		
		/**
		 * A configuração desativa a proteção do CSRF e ativa o tratamento do CORS. 
		 * Em seguida, ele define a política de criação de sessão como STATELESS, 
		 * impedindo que o Spring Security crie HttpSessionobjetos. 
		 * 
		 * Os pontos de extremidade HTTP /api-login, são acessíveis sem uma 
		 * 	autenticação. 
		 * 
		 * Todos os outros pontos de extremidade serão protegidos 
		 * e exigirão um token JWT válido. 
		 * 
		 * No final, a JWTConfigurerclasse auxiliar injeta a JWTFilterna 
		 * cadeia de filtros do Spring Security.
		 * 
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http
				.csrf()
					.disable()
				.cors()
			.and()
				.antMatcher("/api/**").authorizeRequests()
					.and()
						.sessionManagement()
							.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
						.authorizeRequests()
							.antMatchers("/api-login").permitAll()
					.and()
						.apply(new JwtConfigurer(this.tokenProvider));
			
		}
		
		
		/**
		 * Override this method to configure {@link WebSecurity}. For example, if you wish to
		 * ignore certain requests.
		 */
//		@Override
//		public void configure( WebSecurity web ) throws Exception 
//		{
//			web.ignoring()
//				.antMatchers( 
//					"/**/favicon.ico", 
//					"/static/**", 
//					"/modules/**", 
//					"/broker/**/*.js", 
//					"/bundles/**", 
//					"/webjars/**",
//					"/authentication",
//					"/authenticate"
//				);
//		}
		
		
	}
	
	
//	/**
//	 * 
//	 * @author rodrigo
//	 */
//	@Configuration
//	@EnableGlobalMethodSecurity(prePostEnabled = true)
//	public class static WebSecurityConfiguration extends WebSecurityConfigurerAdapter
//	{
//		/*-------------------------------------------------------------------
//		 * 		 					ATTRIBUTES
//		 *-------------------------------------------------------------------*/
//		/**
//		 * 
//		 */
//		private final AuthenticationFailureHandler authenticationFailureHandler;
//		/**
//		 * 
//		 */
//		private final AuthenticationSuccessHandler authenticationSuccessHandler;
//	
//		@Autowired
//		public WebSecurityConfiguration( AuthenticationFailureHandler authenticationFailureHandler, AuthenticationSuccessHandler authenticationSuccessHandler )
//		{
//			this.authenticationFailureHandler = authenticationFailureHandler;
//			this.authenticationSuccessHandler = authenticationSuccessHandler;
//		}
//	
//		/*-------------------------------------------------------------------
//		 * 		 					 OVERRIDES
//		 *-------------------------------------------------------------------*/
//		/**
//		 * 
//		 */
//		@Override
//		protected void configure( HttpSecurity httpSecurity ) throws Exception
//		{
//			httpSecurity.csrf().disable();
//			httpSecurity.headers().frameOptions().disable();
//			
//			httpSecurity
//				.authorizeRequests()
//					.anyRequest()
//						.authenticated()
//						.and()
//							.formLogin()
//								.usernameParameter( "login" )
//								.passwordParameter( "senha" )
//								.loginPage( "/authentication" )
//								.loginProcessingUrl( "/authenticate" )
//								.failureHandler( this.authenticationFailureHandler )
//								.successHandler( this.authenticationSuccessHandler )
//							.permitAll()
//						.and()
//							.logout()
//								.logoutUrl( "/logout" )
//						.and().
//							headers()
//								.defaultsDisabled()
//								.contentTypeOptions();
//		}
//		
//		/**
//		 * Override this method to configure {@link WebSecurity}. For example, if you wish to
//		 * ignore certain requests.
//		 */
//		@Override
//		public void configure( WebSecurity web ) throws Exception 
//		{
//			web.ignoring()
//				.antMatchers( 
//					"/**/favicon.ico", 
//					"/static/**", 
//					"/modules/**", 
//					"/broker/**/*.js", 
//					"/bundles/**", 
//					"/webjars/**", 
//					"/api/**",
//					"/api-login"
//				);
//		}
//	}
	
}