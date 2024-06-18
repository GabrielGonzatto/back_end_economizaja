package back_end_economizaja.model.parcela;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

    @Query(value = "select * from parcela where id_receita=?1;", nativeQuery = true)
    ArrayList<Parcela> findAllByIdReceita(int id);

    @Query(value = "DELETE FROM parcela WHERE id_receita=?2 and contator=?1;", nativeQuery = true)
    void removeParcelaByIdReceita(int num_ultima_parcela, int idreceita);
}
