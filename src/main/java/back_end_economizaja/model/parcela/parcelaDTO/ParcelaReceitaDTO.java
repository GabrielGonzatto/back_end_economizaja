package back_end_economizaja.model.parcela.parcelaDTO;

import java.time.LocalDate;

public record ParcelaReceitaDTO(Double valor, Boolean paga_recebida, LocalDate data) {
}