package back_end_economizaja.model.lancamento.DTO;
import back_end_economizaja.model.categoria.categoriaDTO.DadosCategoriaDTO;
import java.time.LocalDate;

public record DadosLancamentoDTO(Long id, String tipo, String descricao, Double valor, LocalDate data, Boolean paga_recebida, Boolean fixa, Boolean parcelada, Integer numero_de_parcela, DadosCategoriaDTO categoriaDTO) {
}

/*
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "lancamento")
    @JsonIgnore
    private List<Parcela> parcelas;
*/