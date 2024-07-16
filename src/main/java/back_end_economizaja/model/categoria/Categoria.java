package back_end_economizaja.model.categoria;

import back_end_economizaja.model.cliente.Cliente;
import back_end_economizaja.model.lancamento.Lancamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String tipo;

    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Lancamento> lancamentos;

    public Categoria(Long id) {
        this.id = id;
    }

    public Categoria(String nome, String tipo, Boolean ativo, Cliente cliente) {
        this.nome = nome;
        this.tipo = tipo;
        this.ativo = ativo;
        this.cliente = cliente;
    }
}
