package back_end_economizaja.model.parcela.parcelaDTO;

import back_end_economizaja.model.lancamento.DTO.DadosLancamentoDTO;

import java.time.LocalDate;

public record DadosParcelaDTO(Long id, Double valor, Boolean paga_recebida, LocalDate data, Integer contador, DadosLancamentoDTO lancamento) {
}