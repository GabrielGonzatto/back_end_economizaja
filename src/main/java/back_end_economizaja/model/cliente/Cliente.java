package back_end_economizaja.model.cliente;

import back_end_economizaja.model.categoria.Categoria;
import back_end_economizaja.model.receita.Receita;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    private String primeiro_nome;

    @NotBlank
    private String segundo_nome;

    @NotBlank
    private String cpf;

    @Email
    private String email;

    @NotBlank
    private String senha;

    private ClienteRole role;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Categoria> categorias;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Receita> receitas;

    public Cliente(String primeiro_nome, String segundo_nome, String cpf, String email, String senha, ClienteRole role) {
        this.primeiro_nome = primeiro_nome;
        this.segundo_nome = segundo_nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }
}
