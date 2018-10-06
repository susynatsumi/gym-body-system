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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.eits.boot.application.configuration.jwt.JwtAuthenticationEntryPoint;
import br.com.eits.boot.application.configuration.jwt.JwtFilter;
import br.com.eits.boot.application.security.AuthenticationFailureHandler;
import br.com.eits.boot.application.security.AuthenticationSuccessHandler;

@EnableWebSecurity
public class SecurityConfiguration {
	
	@Order(2)
	@Configuration
//	@EnableGlobalMethodSecurity(prePostEnabled = false)
	public static class RestSecurityConfiguration// { 
		extends WebSecurityConfigurerAdapter {

//		private final TokenProvider tokenProvider;
		@Autowired
		private JwtFilter jwtFilter;
		/**
		 * Constructor
		 * @param tokenProvide
		 */
//		@Autowired
//		public RestSecurityConfiguration(TokenProvider tokenProvide) {
//			this.tokenProvider = tokenProvide;
//		}

	    @Autowired
	    private JwtAuthenticationEntryPoint unauthorizedHandler;
		
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
		 * O jwt filter será adicionado validando somente quando a url conter /api/**
		 * 
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http
			.csrf()
				.disable()
			
			// para tratar os erros
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			// ativo o cors para o jwt
			.cors()
		.and()
//		.authorizeRequests()
//				.and()
//					.sessionManagement()
//						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				.and()
//					.antMatcher("/api/**").authorizeRequests()
//				.and()
		// permito request em todas as urls, trato para filtrar requests da api no meu filter
					.authorizeRequests()
					.antMatchers("/**").permitAll()
//					.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//						.antMatchers("/api-login").permitAll()
//						.antMatchers("/authenticate").permitAll()
//						.antMatchers("/authentication").permitAll()
//				.and()
//					.apply(new JwtConfigurer(this.tokenProvider))
					// adiciono o filter responsavel pelo controle de sessão e token
				.and().addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class)
			;
			
/*	 	http.csrf().disable().cors()
	        .and()
		      .sessionManagement()
		        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        	.requestMatchers()
	        	.antMatchers("/api/**")
//		        .antMatchers("/api-login").permitAll()
		    .and()
		        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		        .authorizeRequests().anyRequest().authenticated();*/
			
//			http.csrf().disable().cors().and().requestMatchers().and()
//			.antMatcher("/api/**").authorizeRequests()
//			.antMatchers("/api-login").permitAll()
//			.and()
//            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//            .authorizeRequests().anyRequest().authenticated();
				
		}
		
		
		/*
		 * http
				.csrf()
					.disable()
				.cors()
//			.and().authorizeRequests()
					.and()
						.sessionManagement()
							.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
						.antMatcher("/api/**").authorizeRequests()
					.and()
						.authorizeRequests()
							.antMatchers("/api-login").permitAll()
//					.and()
//						.apply(new JwtConfigurer(this.tokenProvider))
					.and().addFilterBefore( new JwtFilter(this.tokenProvider), UsernamePasswordAuthenticationFilter.class);
			
		 */
		
		/**
		 * configura o cors da api
		 * @return
		 */
	    @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
	                        .allowedHeaders("*");
	            }
	        };
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
//					"/authenticate",
//					"/logout",
//					"/api-login"
//				);
//			
//		}
		
		
	}
	
	
	/**
	 * 
	 * @author rodrigo
	 */
	@Order(0)
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
		
		/*-------------------------------------------------------------------
		 * 		 					ATTRIBUTES
		 *-------------------------------------------------------------------*/
		/**
		 * 
		 */
		private final AuthenticationFailureHandler authenticationFailureHandler;
		/**
		 * 
		 */
		private final AuthenticationSuccessHandler authenticationSuccessHandler;
	
		@Autowired
		public WebSecurityConfiguration( AuthenticationFailureHandler authenticationFailureHandler, AuthenticationSuccessHandler authenticationSuccessHandler )
		{
			this.authenticationFailureHandler = authenticationFailureHandler;
			this.authenticationSuccessHandler = authenticationSuccessHandler;
		}
	
		/*-------------------------------------------------------------------
		 * 		 					 OVERRIDES
		 *-------------------------------------------------------------------*/
		/**
		 * 
		 */
		@Override
		protected void configure( HttpSecurity httpSecurity ) throws Exception
		{
			httpSecurity.csrf().disable();
			httpSecurity.headers().frameOptions().disable();
			
			httpSecurity
				.authorizeRequests()
//				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//				.antMatchers("/api/**").permitAll()
//				.antMatchers("/api-login").permitAll()
				.anyRequest().authenticated()
			.and()
					.formLogin()
							.usernameParameter( "login" )
							.passwordParameter( "senha" )
							.loginPage( "/authentication" )
							.loginProcessingUrl( "/authenticate" )
							.failureHandler( this.authenticationFailureHandler )
							.successHandler( this.authenticationSuccessHandler )
						.permitAll()
					.and()
						.logout()
							.logoutUrl( "/logout" )
					.and().
						headers()
							.defaultsDisabled()
							.contentTypeOptions();
		}
		
		/**
		 * Override this method to configure {@link WebSecurity}. For example, if you wish to
		 * ignore certain requests.
		 */
		@Override
		public void configure( WebSecurity web ) throws Exception 
		{
			web.ignoring()
				.antMatchers( 
					"/**/favicon.ico", 
					"/static/**", 
					"/modules/**", 
					"/broker/**/*.js", 
					"/bundles/**", 
					"/webjars/**",
					"/api/**",
					"/api-login"
				);
		}
		
	}
	

}