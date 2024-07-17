package back_end_economizaja.model.lancamento.DTO;

import back_end_economizaja.model.lancamento.Lancamento;
import back_end_economizaja.model.parcela.Parcela;

import java.time.LocalDate;
import java.util.ArrayList;

public record DataLancamentosConsultaDTO(String dataLancamentoConsulta, LocalDate dataInicial, LocalDate dataFinal) {

    public ArrayList<ListagemLancamentosPagarReceber> tranformaDTO (ArrayList<Lancamento> lancamentos, ArrayList<Parcela> parcelas) {
        ArrayList<ListagemLancamentosPagarReceber> lista = new ArrayList<>();

        for (Lancamento lancamento : lancamentos) {
            lista.add(new ListagemLancamentosPagarReceber(lancamento.getId(), lancamento.getDescricao(), "lancamento", lancamento.getData(), lancamento.getValor(), lancamento.getPaga_recebida()));
        }

        for (Parcela parcela : parcelas) {
            lista.add(new ListagemLancamentosPagarReceber(parcela.getId(), parcela.getLancamento().getDescricao(), "parcela", parcela.getData(), parcela.getValor(), parcela.getPaga_recebida()));
        }

        return lista;
    }
}
