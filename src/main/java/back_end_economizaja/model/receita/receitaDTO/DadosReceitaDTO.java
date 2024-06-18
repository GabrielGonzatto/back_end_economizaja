package back_end_economizaja.model.receita.receitaDTO;

import back_end_economizaja.model.categoria.categoriaDTO.CategoriaReceitaDTO;
import back_end_economizaja.model.parcela.parcelaDTO.ParcelaReceitaDTO;

import java.time.LocalDate;
import java.util.ArrayList;

public record DadosReceitaDTO(String descricao, Double valor, LocalDate data, Boolean recebida, CategoriaReceitaDTO categoria, ArrayList<ParcelaReceitaDTO> parcelas) {
}