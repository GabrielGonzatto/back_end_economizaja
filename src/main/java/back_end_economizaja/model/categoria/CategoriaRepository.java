package back_end_economizaja.model.categoria;


import back_end_economizaja.model.categoria.categoriaDTO.DadosCategoriaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    public ArrayList<Categoria> findAll();

    @Query(value = "select * from categoria where tipo='receita' and ativo=true and id_cliente=?1;", nativeQuery = true)
    ArrayList<Categoria> findCategoriaByReceita(int idcliente);


    @Query(value = "select * from categoria where tipo='despesa' and ativo=true and id_cliente=?1;", nativeQuery = true)
    ArrayList<Categoria> findCategoriaByDespesa(int idcliente);
}
