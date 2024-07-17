package back_end_economizaja.model.lancamento.DTO;

import back_end_economizaja.model.categoria.categoriaDTO.MaioresGastosMesHomeDTO;
import back_end_economizaja.model.lancamento.Lancamento;
import back_end_economizaja.model.parcela.Parcela;
import back_end_economizaja.model.parcela.parcelaDTO.DadosParcelaDTO;

import java.util.ArrayList;

public record DadosHome(String nome, Double saldoGeral, Double receitaMensal, Double despesaMensal, ArrayList<ListagemLancamentosPagarReceber> contasAPagar, ArrayList<ListagemLancamentosPagarReceber> contasAPagarAtrasadas, ArrayList<ListagemLancamentosPagarReceber> contasAReceber, ArrayList<ListagemLancamentosPagarReceber> contasAReceberAtrasadas, ArrayList<MaioresGastosMesHomeDTO> maioresGastos) {
}