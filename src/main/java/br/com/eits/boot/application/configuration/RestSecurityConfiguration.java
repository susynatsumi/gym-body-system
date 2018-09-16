package br.com.eits.boot.application.configuration;

/**
 * 
 * @author Eduardo
 *
 */
//@Configuration
//@Order(1)
public class RestSecurityConfiguration { 
//	extends WebSecurityConfigurerAdapter {
//
//	private final TokenProvider tokenProvider;
//	
//	/**
//	 * Constructor
//	 * @param tokenProvide
//	 */
//	@Autowired
//	public RestSecurityConfiguration(TokenProvider tokenProvide) {
//		this.tokenProvider = tokenProvide;
//	}
//
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//	
//	/**
//	 * A configuração desativa a proteção do CSRF e ativa o tratamento do CORS. 
//	 * Em seguida, ele define a política de criação de sessão como STATELESS, 
//	 * impedindo que o Spring Security crie HttpSessionobjetos. 
//	 * 
//	 * Os pontos de extremidade HTTP /api-login, são acessíveis sem uma 
//	 * 	autenticação. 
//	 * 
//	 * Todos os outros pontos de extremidade serão protegidos 
//	 * e exigirão um token JWT válido. 
//	 * 
//	 * No final, a JWTConfigurerclasse auxiliar injeta a JWTFilterna 
//	 * cadeia de filtros do Spring Security.
//	 * 
//	 */
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		http
//			.csrf()
//				.disable();
//		
//		http
//			.cors()
//		.and()
//			.antMatcher("/api/**").authorizeRequests()
//				.and()
//					.sessionManagement()
//						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				.and()
//					.authorizeRequests()
//						.antMatchers("/api-login").permitAll();
////				.and()
////					.apply(new JwtConfigurer(this.tokenProvider));
//		
//	}
//	
//	
//	/**
//	 * Override this method to configure {@link WebSecurity}. For example, if you wish to
//	 * ignore certain requests.
//	 */
////	@Override
////	public void configure( WebSecurity web ) throws Exception 
////	{
////		web.ignoring()
////			.antMatchers( 
////				"/**/favicon.ico", 
////				"/static/**", 
////				"/modules/**", 
////				"/broker/**/*.js", 
////				"/bundles/**", 
////				"/webjars/**",
////				"/authentication",
////				"/authenticate"
////			);
////	}
	
	
}
