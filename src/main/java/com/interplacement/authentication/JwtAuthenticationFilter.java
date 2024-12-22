package com.interplacement.authentication;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestHeader = request.getHeader("Authorization");
		log.info("Request Header: {}", requestHeader);

		String username = null;
		String token = null;

		if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
			token = requestHeader.substring(7); // Extract JWT token
			try {
				username = this.jwtHelper.getUserNameFromToken(token);
				log.info("Extracted username from token: {}", username);
			} catch (IllegalArgumentException e) {
				logger.error("Illegal argument while fetching username: {}");
			} catch (ExpiredJwtException e) {
				logger.error("JWT token is expired: {}");
			} catch (MalformedJwtException e) {
				logger.error("Malformed JWT token: {}");
			} catch (Exception e) {
				logger.error("Unexpected error while processing JWT token: {}");
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

			if (validateToken) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				log.info("Authentication successfully set for user: {}", username);
			} else {
				logger.error("JWT token validation failed for user: {}");
			}
		}

		filterChain.doFilter(request, response);
	}
}
