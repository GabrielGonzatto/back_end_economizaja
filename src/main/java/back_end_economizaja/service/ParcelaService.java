package back_end_economizaja.service;

import back_end_economizaja.model.parcela.Parcela;
import back_end_economizaja.model.parcela.ParcelaRepository;
import back_end_economizaja.model.receita.Receita;
import back_end_economizaja.model.receita.receitaDTO.EditarReceitaDTO;
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

    public void cadastrarListaDeParcelas(Receita receita){
        Double valor_de_cada_parcela = receita.getValor() / receita.getNumero_de_parcelas();

        for(int i = 0; i < receita.getNumero_de_parcelas(); i++){
            cadastrar(new Parcela(valor_de_cada_parcela, receita.getRecebida(), receita.getData().plusMonths(i + 1), i, true, receita));
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
}
