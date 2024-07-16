package back_end_economizaja.model.lancamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.ArrayList;

public interface LancamentoRepository  extends JpaRepository<Lancamento, Long> {
    @Query(value = "SELECT * FROM lancamento WHERE id_cliente=?1 AND parcelada=false AND ativo = true AND EXTRACT(MONTH FROM data)=?2 AND EXTRACT(YEAR FROM data)=?3 ORDER BY data", nativeQuery = true)
    ArrayList<Lancamento> findAllLancamentosNaoParceladosDoMes(int id_cliente, int mes, int ano);

    @Query(value = "SELECT * FROM lancamento WHERE id_cliente=?1 AND parcelada=false AND ativo = true and paga_recebida = true ORDER BY data;", nativeQuery = true)
    ArrayList<Lancamento> findAllLancamentosNaoParceladosPagos(int id_cliente);

    @Query(value = "SELECT * FROM lancamento WHERE id_cliente=?1 AND parcelada=false AND ativo = true AND data < ?2 ORDER BY data;", nativeQuery = true)
    ArrayList<Lancamento> findAllLancamentosNaoParceladosDoAtrasados(int id_cliente, LocalDate data);
}

    /*@Query(value = "SELECT * FROM lancamento WHERE id_cliente=?1 AND parcelada=false AND ativo = true AND data < TO_DATE(?2, 'YYYY-MM-DD');", nativeQuery = true)
    ArrayList<Lancamento> findAllLancamentosNaoParceladosDoAtrasados(int id_cliente, LocalDate data);*/