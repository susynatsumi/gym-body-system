package br.com.eits.boot.application.configuration.jwt;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.io.IOException;

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
	extends GenericFilterBean {

	public final static String AUTHORIZATION_HEADER = "Authorization";

	@Autowired
	private TokenProvider tokenProvider;

	/**
	 * 
	 * @param tokenProvider
	 */
//	public JwtFilter(TokenProvider tokenProvider) {
//		this.tokenProvider = tokenProvider;
//	}

	/**
	 * Faz o filtro de todas as requisições, verificando o header do token
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		System.out.println("JwtFilter.doFilter()");
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			String jwtToken = resolveToken(httpServletRequest);
			if (jwtToken != null) {
				Authentication authentication = this.tokenProvider.getAuthentication(jwtToken);
				if (authentication != null) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			System.out.println("entrou no filter, validou token "+jwtToken);
			
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private static String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		System.out.println(bearerToken);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}