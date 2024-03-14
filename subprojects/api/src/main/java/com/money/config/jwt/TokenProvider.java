package com.money.config.jwt;

import com.money.exception.auth.InvalidTokenException;
import com.money.exception.auth.TokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

   private static final String AUTHORITIES_KEY = "auth";
   private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
   private final String secret;
   private final long tokenValidityInMilliseconds;
   private Key key;
   private final JwtParser jwtParser;


    public TokenProvider(
      @Value("${app.token.secret}") String secret,
      @Value("${app.token.expiration.access-token}") long tokenValidityInMilliseconds) {
      this.secret = secret;
      this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
      this.jwtParser = Jwts.parserBuilder().setSigningKey(createSignKey(secret)).build();
    }

   @Override
   public void afterPropertiesSet() {
      byte[] keyBytes = Decoders.BASE64.decode(secret);
      this.key = Keys.hmacShaKeyFor(keyBytes);
   }

   public String createToken(Authentication authentication) {
      String authorities = authentication.getAuthorities().stream()
         .map(GrantedAuthority::getAuthority)
         .collect(Collectors.joining(","));

      long now = (new Date()).getTime();
      Date validity = new Date(now + this.tokenValidityInMilliseconds);

      return Jwts.builder()
         .setSubject(authentication.getName())
         .claim(AUTHORITIES_KEY, authorities)
         .signWith(key, SIGNATURE_ALGORITHM)
         .setExpiration(validity)
         .compact();
   }

   public Authentication getAuthentication(String token) {
      Claims claims = Jwts
              .parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token)
              .getBody();

      Collection<? extends GrantedAuthority> authorities =
         Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .toList();

      User principal = new User(claims.getSubject(), "", authorities);

      return new UsernamePasswordAuthenticationToken(principal, token, authorities);
   }

   public boolean validateToken(String token) {
      try {
         Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
         return true;
      } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
         log.info("잘못된 JWT 서명입니다.");
      } catch (ExpiredJwtException e) {
         log.info("만료된 JWT 토큰입니다.");
      } catch (UnsupportedJwtException e) {
         log.info("지원되지 않는 JWT 토큰입니다.");
      } catch (IllegalArgumentException e) {
         log.info("JWT 토큰이 잘못되었습니다.");
      }
      return false;
   }

   public String getPayload(String token) {
      try {
         return jwtParser.parseClaimsJws(token).getBody().getSubject();
      } catch (ExpiredJwtException e) {
         throw new TokenExpiredException();
      } catch (JwtException e) {
         throw new InvalidTokenException();
      }
   }

   private Key createSignKey(String secret) {
      byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secret);

      return new SecretKeySpec(secretKeyBytes, SIGNATURE_ALGORITHM.getJcaName());
   }
}
