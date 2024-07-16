package back_end_economizaja.infra.security;

import back_end_economizaja.model.cliente.DTO.RespostaLogin;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;
import java.util.Map;

@Service
public class TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    public RespostaLogin gerarToken (Long id) {
        var tempo_agora = Instant.now();
        var expiracao_token = 7200L;

        var claims = JwtClaimsSet.builder()
                .issuer("EconomizaJá")
                .subject(id.toString())
                .issuedAt(tempo_agora)
                .expiresAt(tempo_agora.plusSeconds(expiracao_token))
                .build();


        var token_jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new RespostaLogin(token_jwt, expiracao_token);
    }

    public String recuperarIdDoToken (HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " do início
            Jwt jwt = jwtDecoder.decode(token);
            return jwt.getSubject(); // Extrai a claim "subject" (ID)
        } else {
            throw new IllegalArgumentException("Token JWT não encontrado no cabeçalho Authorization");
        }
    }

}
