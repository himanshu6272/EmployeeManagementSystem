package com.EmployeeManagementSystem.config;

import com.EmployeeManagementSystem.services.CustomUserDetailsService;
import com.EmployeeManagementSystem.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private  static final Logger log = Logger.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        log.info(requestHeader);
        String username = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")){
            token = requestHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(token);
            }catch (IllegalArgumentException e) {
                log.info("Illegal Argument while fetching the username !!");
                log.error(e);
            } catch (ExpiredJwtException e) {
                log.info("Given jwt token is expired !!");
                log.error(e);
            } catch (MalformedJwtException e) {
                log.info("Some changed has done in token !! Invalid Token");
                log.error(e);
            } catch (Exception e) {
                log.error(e);

            }
        }else {
            log.error("Invalid Header Value!!");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            boolean validateToken = this.jwtUtil.validateToken(token, userDetails);
            if (validateToken){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else {
                log.error("Validation fails!!");
            }
        }
        filterChain.doFilter(request, response);
    }
}
