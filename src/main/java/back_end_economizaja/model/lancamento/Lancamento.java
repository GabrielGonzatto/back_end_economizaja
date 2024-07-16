package back_end_economizaja.model.lancamento;

import back_end_economizaja.model.categoria.Categoria;
import back_end_economizaja.model.cliente.Cliente;
import back_end_economizaja.model.parcela.Parcela;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "lancamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String tipo;

    private String descricao;

    private Double valor;

    private LocalDate data;

    private Boolean paga_recebida;

    private Boolean fixa;

    private Boolean parcelada;

    private Integer numero_de_parcelas;

    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "lancamento")
    @JsonIgnore
    private List<Parcela> parcelas;

    public Lancamento(String tipo, String descricao, Double valor, LocalDate data, Boolean paga_recebida, Boolean fixa, Boolean parcelada, Integer numero_de_parcelas, Boolean ativo, Categoria categoria, Cliente cliente) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.paga_recebida = paga_recebida;
        this.fixa = fixa;
        this.parcelada = parcelada;
        this.numero_de_parcelas = numero_de_parcelas;
        this.ativo = ativo;
        this.categoria = categoria;
        this.cliente = cliente;
    }
}
