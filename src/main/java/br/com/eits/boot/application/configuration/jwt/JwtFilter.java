package br.com.eits.boot.application.configuration.jwt;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * referencias
 * https://golb.hplar.ch/2017/02/JWT-Authentication-with-Ionic-2-and-Spring-Boot.html
 * 
 * Filters incoming requests and installs a Spring Security principal if a
 * header corresponding to a valid user is found.
 * 
 * Esta classe será injetada na cadeia de filtros do Spring Security e toda
 * solicitação HTTP, que precisa ser autenticada, fluirá através desse filtro
 * 
 */
@Component
public class JwtFilter// {
		extends OncePerRequestFilter  {

	public final static String AUTHORIZATION_HEADER = "Authorization";

	@Autowired
	private TokenProvider tokenProvider;

	/**
	 * 
	 * @param tokenProvider
	 */
	// public JwtFilter(TokenProvider tokenProvider) {
	// this.tokenProvider = tokenProvider;
	// }

	/**
	 * Faz o filtro de todas as requisições, verificando o header do token
	 */
	/*@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		System.out.println("JwtFilter.doFilter()");

		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			System.out.println("requisição para uri: " + httpServletRequest.getRequestURI());
			String jwtToken = resolveToken(httpServletRequest);
			if (jwtToken != null) {

				SecurityContext ctx = SecurityContextHolder.createEmptyContext();
				SecurityContextHolder.setContext(ctx);
				
				UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) this.tokenProvider
						.getAuthentication(jwtToken);

				if (authentication != null) {

					authentication.setDetails(
							new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) servletRequest));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			System.out.println("entrou no filter, validou token " + jwtToken);

			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}*/

	private static String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		System.out.println(bearerToken);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	
	/**
	 * Digo que meu filter deve tratar apenas requisições para a api
	 */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return !path.startsWith("/api/");
    }

	@Override
	protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain)
			throws ServletException, java.io.IOException {
		System.out.println("JwtFilter.doFilter()");

		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			System.out.println("requisição para uri: " + httpServletRequest.getRequestURI());
			String jwtToken = resolveToken(httpServletRequest);
			if (jwtToken != null) {

				SecurityContext ctx = SecurityContextHolder.createEmptyContext();
				SecurityContextHolder.setContext(ctx);
				
				UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) this.tokenProvider
						.getAuthentication(jwtToken);

				if (authentication != null) {

					authentication.setDetails(
							new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) servletRequest));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			System.out.println("entrou no filter, validou token " + jwtToken);

			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
}