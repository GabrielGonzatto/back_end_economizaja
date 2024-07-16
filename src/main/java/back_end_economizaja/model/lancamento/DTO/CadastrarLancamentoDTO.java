package back_end_economizaja.model.lancamento.DTO;

import java.time.LocalDate;

public record CadastrarLancamentoDTO(String tipo, String descricao, Double valor, LocalDate data, Boolean paga_recebida, Boolean fixa, Boolean parcelada, int numero_de_parcelas, int id_categoria) {
}
