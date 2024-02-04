package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Repositories.UserRepository;
import com.b2012202.mxhtknt.Services.JWTService;
import com.b2012202.mxhtknt.Services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {
    private final UserService userService;
    private final UserRepository userRepository;
    @Value("${spring.security.jwt.secret}")
    private String secretKey;

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public Long extractId(String token) {
        String stringID= extractClaim(token, Claims::getId);
        long longID = 0;
        try {
            longID = Long.parseLong(stringID);
            // Thực hiện các thao tác với longNumber
        } catch (NumberFormatException e) {
            // Xử lý ngoại lệ khi chuỗi không thể chuyển đổi thành Long
            System.out.println("Invalid number format");
        }
        return longID;
    }
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public String generateRefreshToken(HashMap<Object, Object> objectObjectHashMap, UserDetails userDetails) {

//        System.out.println("GenerateRefreshToken at < " + LocalDateTime.now() + " > " + token);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        var user = userRepository.findByUsername(userDetails.getUsername()).get();
        String token = Jwts.builder()
                .claim("id", user.getId())
                .claim("authorities", userDetails.getAuthorities())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 8000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        System.out.println("GenerateToken at < " + LocalDateTime.now() + " > " + token);
        return token;
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
