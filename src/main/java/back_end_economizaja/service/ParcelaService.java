package back_end_economizaja.service;

import back_end_economizaja.model.categoria.Categoria;
import back_end_economizaja.model.categoria.categoriaDTO.EditarCategoriaDTO;
import back_end_economizaja.model.lancamento.DTO.CadastrarLancamentoDTO;
import back_end_economizaja.model.lancamento.Lancamento;
import back_end_economizaja.model.parcela.Parcela;
import back_end_economizaja.model.parcela.ParcelaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ParcelaService {
    @Autowired
    private ParcelaRepository repository;

    public void cadastrar(Parcela parcela){
        this.repository.save(parcela);
    }

    public void cadastrarListaDeParcelas(CadastrarLancamentoDTO lancamentoDTO, Long id){
        Double valor_de_cada_parcela = lancamentoDTO.valor() / lancamentoDTO.numero_de_parcelas();

        Lancamento lancamento = new Lancamento();
        lancamento.setId(id);

        for(int i = 0; i < lancamentoDTO.numero_de_parcelas(); i++){
            cadastrar(new Parcela(valor_de_cada_parcela, lancamentoDTO.paga_recebida(), lancamentoDTO.data().plusMonths(i), i, true, lancamento));
        }
    }

    public void desativarParcelasReceitas(Long id){
        ArrayList<Parcela> parcelas = this.repository.findAllByIdReceita(id.intValue());

        for (Parcela parcela : parcelas){
            Parcela p = this.repository.getReferenceById(parcela.getId());
            p.setAtivo(false);
            this.repository.save(p);
        }
    }

    public void receberParcelasReceitas(Long id){
        ArrayList<Parcela> parcelas = this.repository.findAllByIdReceita(id.intValue());

        for (Parcela parcela : parcelas){
            Parcela p = this.repository.getReferenceById(parcela.getId());
            p.setPaga_recebida(true);
            this.repository.save(p);
        }
    }

    public void pagarDespagarParcela(Long id){
        Parcela parcela = this.repository.getReferenceById(id);

        if (parcela.getPaga_recebida() == true) {
            parcela.setPaga_recebida(false);
        } else {
            parcela.setPaga_recebida(true);
        }
        this.repository.save(parcela);
    }
}
