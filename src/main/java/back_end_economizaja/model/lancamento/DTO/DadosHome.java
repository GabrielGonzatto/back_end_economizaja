package back_end_economizaja.model.lancamento.DTO;

import back_end_economizaja.model.categoria.categoriaDTO.MaioresGastosMesHomeDTO;
import back_end_economizaja.model.lancamento.Lancamento;
import back_end_economizaja.model.parcela.Parcela;
import back_end_economizaja.model.parcela.parcelaDTO.DadosParcelaDTO;

import java.util.ArrayList;

public record DadosHome(String nome, Double saldoGeral, Double receitaMensal, Double despesaMensal, ArrayList<ListagemHomePagarReceber> contasAPagar, ArrayList<ListagemHomePagarReceber> contasAPagarAtrasadas, ArrayList<ListagemHomePagarReceber> contasAReceber, ArrayList<ListagemHomePagarReceber> contasAReceberAtrasadas, ArrayList<MaioresGastosMesHomeDTO> maioresGastos) {
}