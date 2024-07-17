package back_end_economizaja.model.lancamento.DTO;

import java.util.ArrayList;

public record DadosRelatorios(ArrayList<ListagemLancamentosPagarReceber> receitas, ArrayList<ListagemLancamentosPagarReceber> despesas) {
}
