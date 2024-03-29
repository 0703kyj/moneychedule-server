package com.money.system.config.jwt;

import com.money.system.exception.InvalidTokenException;
import com.money.system.exception.TokenExpiredException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

   private static final String AUTHORITIES_KEY = "auth";
   private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
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

   public String createToken(Long memberId) {
      Date now = new Date();
      Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);

      return Jwts.builder()
              .setSubject(String.valueOf(memberId))
              .setIssuedAt(now)
              .setExpiration(validity)
              .signWith(key, SIGNATURE_ALGORITHM)
              .compact();
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

      return Keys.hmacShaKeyFor(secretKeyBytes);
   }
}
