package back_end_economizaja.model.lancamento.DTO;

import java.time.LocalDate;

public record ListagemHomePagarReceber(Long id, String descricao, String tipo, LocalDate data, Double valor, Boolean paga_recebida) {
}
