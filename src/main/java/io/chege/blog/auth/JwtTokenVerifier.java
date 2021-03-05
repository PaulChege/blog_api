package io.chege.blog.auth;

import com.google.common.base.Strings;
import io.chege.blog.SpringContext;
import io.chege.blog.user.User;
import io.chege.blog.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtTokenVerifier(JwtConfig jwtConfig, SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())){
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            String username = claimsJws.getBody().getSubject();
            var authorities = (List<Map<String, String>>) claimsJws.getBody().get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream().map(m -> new SimpleGrantedAuthority(m.get("authority"))).collect(Collectors.toSet());

            UserRepository repository = SpringContext.getBean(UserRepository.class);
            Optional<User> user = repository.getUserByEmail(username);
            if(user.isPresent() && user.get().getStatus()) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch(RuntimeException e){
            throw new IllegalStateException("Invalid token");
        }
        filterChain.doFilter(request, response);
    }
}
