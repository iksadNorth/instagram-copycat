package me.iksadnorth.insta.config.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.utils.JwtTokenUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String header = response.getHeader(HttpHeaders.AUTHORIZATION);

        try{
            if(header == null || !header.startsWith("Bearer ")) {
                throw new InstaApplicationException(
                        ErrorCode.TOKEN_NOT_FOUNDED,
                        String.format("헤더에 인증을 위한 Authorization가 Bearer로 시작하지 않음. \nheader: %s", header)
                );
            }

            String token = header.split(" ")[1].trim();
            String email = JwtTokenUtils.getEmail(token, secretKey);
            UserDetails principal = userService.loadUserByUsername(email);


            if(!JwtTokenUtils.isExpired(token, secretKey)) {
                throw new InstaApplicationException(ErrorCode.TOKEN_EXPIRED);
            }

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    principal, null, principal.getAuthorities()
            );

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (RuntimeException e) {
            log.error(e.toString());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
