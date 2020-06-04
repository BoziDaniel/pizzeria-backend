package com.codecool.pizzabackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtTokenService {
    public String getTokenFromRequest(HttpServletRequest req) {
//        String bearerToken = req.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7, bearerToken.length());
//        }
//        return null;
        String authorizationHeader = req.getHeader("Authorization");
        String token = authorizationHeader.replace("Bearer ", "");
        return token;
    }

    public Long getIdFromToken(String token){
        String secretKey = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecurevalami";
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .parseClaimsJws(token);

        Claims body = claimsJws.getBody();
        System.out.println(body.get("id"));
        return  Long.valueOf((Integer) body.get("id"));
    }

    public Long getIdFromRequestThroughToken(HttpServletRequest req){
        String token = getTokenFromRequest(req);
        return getIdFromToken(token);
    }
}


