package back_end_economizaja.model.parcela;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

    @Query(value = "select * from parcela where id_receita=?1;", nativeQuery = true)
    ArrayList<Parcela> findAllByIdReceita (int id);

    @Query(value = "DELETE FROM parcela WHERE id_receita=?2 and contator=?1;", nativeQuery = true)
    void removeParcelaByIdReceita (int num_ultima_parcela, int idreceita);

    @Query(value = "SELECT p.* FROM parcela p INNER JOIN lancamento l ON p.id_lancamento = l.id WHERE l.id_cliente = ?1 AND l.ativo = true AND p.paga_recebida = true;", nativeQuery = true)
    ArrayList<Parcela> findAllParcelasPagas(int id_cliente);

    @Query(value = "SELECT p.* FROM parcela p INNER JOIN lancamento l ON p.id_lancamento = l.id WHERE l.id_cliente = ?1 AND l.ativo = true AND EXTRACT(MONTH FROM p.data) = ?2 AND EXTRACT(YEAR FROM p.data) = ?3 ORDER BY p.data", nativeQuery = true)
    ArrayList<Parcela> findAllParcelasDoMes(int id_cliente, int mes, int ano);

    @Query(value = "SELECT p.* FROM parcela p INNER JOIN lancamento l ON p.id_lancamento = l.id WHERE l.id_cliente = ?1 AND l.ativo = true AND p.data < TO_DATE(?2, 'YYYY-MM-DD') ORDER BY p.data;", nativeQuery = true)
    ArrayList<Parcela> findAllParcelasAtrasadas(int id_cliente, String data);
}
