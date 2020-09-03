package com.codecool.pizzabackend.security;

import com.codecool.pizzabackend.entity.User;
import com.codecool.pizzabackend.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@Component
@Slf4j
public class JwtTokenServices {
    //    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecurevalami";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 36000000; // 10h
    private final String rolesFieldName = "roles";
    private UserRepository userRepository;

    @Autowired
    public JwtTokenServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Creates a JWT token
    public String createToken(String username, List<String> roles) {
        // Add a custom field to the token
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(rolesFieldName, roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String getTokenFromRequest(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
    boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("JWT token invalid " + e);
        }
        return false;
    }

    /**
     * Parses the username and roles from the token. Since the token is signed we can be sure its valid information.
     * Note that it does not make a DB call to be super fast!
     * This could result in returning false data (e.g. the user was deleted, but their token has not expired yet)
     * To prevent errors because of this make sure to check the user in the database for more important calls!
     */
    Authentication parseUserFromTokenInfo(String token) throws UsernameNotFoundException {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String username = body.getSubject();
        List<String> roles = (List<String>) body.get(rolesFieldName);
        List<SimpleGrantedAuthority> authorities = new LinkedList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new UsernamePasswordAuthenticationToken(username, "", authorities);
    }

    public String getUsernameFromJwtToken(String token) {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String username = body.getSubject();
        return username;
    }
    public User getUserFromRequest(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        String username = getUsernameFromJwtToken(token);
        LOGGER.info(String.format("username from token: %s", username));
        User user = userRepository.getAppUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
        return user;

    }
}