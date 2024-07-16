package back_end_economizaja.model.parcela;

import back_end_economizaja.model.lancamento.Lancamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "parcela")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parcela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Double valor;

    private Boolean paga_recebida;

    private LocalDate data;

    private Integer contador;

    private Boolean ativo;


    @ManyToOne
    @JoinColumn(name = "id_lancamento")
    private Lancamento lancamento;

    public Parcela(Double valor, Boolean paga_recebida, LocalDate data, Integer contador, Boolean ativo, Lancamento lancamento) {
        this.valor = valor;
        this.paga_recebida = paga_recebida;
        this.data = data;
        this.contador = contador;
        this.ativo = ativo;
        this.lancamento = lancamento;
    }
}
