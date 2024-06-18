package back_end_economizaja.service;

import back_end_economizaja.model.cliente.Cliente;
import back_end_economizaja.model.receita.Receita;
import back_end_economizaja.model.receita.ReceitaRepository;
import back_end_economizaja.model.receita.receitaDTO.CadastrarReceitaDTO;
import back_end_economizaja.model.receita.receitaDTO.EditarReceitaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReceitaService {
    @Autowired
    private ReceitaRepository repository;

    @Autowired
    private ParcelaService parcelaService;

    @Autowired
    private ClienteService clienteService;

    public void cadastrar(CadastrarReceitaDTO r){
        Cliente cliente = this.clienteService.buscaCliente(r.cliente().getId());

        Receita receita = new Receita(r.descricao(), r.valor(), r.data(), r.recebida(), r.fixa(), r.parcelada(), r.numero_de_parcelas(), true, r.categoria(), cliente);
        this.repository.save(receita);

        if (receita.getParcelada() == true){
            this.parcelaService.cadastrarListaDeParcelas(receita);
        }

        else if (receita.getFixa() == true){
            if (receita.getRecebida() == true){
                Receita novaReceita = new Receita(r.descricao(), r.valor(), r.data().plusMonths(1), r.recebida(), r.fixa(), r.parcelada(), r.numero_de_parcelas(), true, r.categoria(), cliente);

                this.repository.save(novaReceita);
            }
        }
    }

    public void editar(EditarReceitaDTO receita){

    }

    public void desativar(Long id){
        Receita receita = this.repository.getReferenceById(id);

        if (receita != null){
            if (receita.getParcelada() == true) {
                receita.setAtivo(false);
                this.repository.save(receita);

                this.parcelaService.desativarParcelasReceitas(receita.getId());
            }

            else if (receita.getFixa()) {
                ArrayList<Receita> receitas = this.repository.findReceitaByName(receita.getDescricao(), receita.getCliente().getId().intValue());

                for (Receita r : receitas) {
                    Receita receitaDesativar = this.repository.getReferenceById(r.getId());
                    receitaDesativar.setAtivo(false);
                    this.repository.save(receitaDesativar);
                }

            }
        }
    }

    public void receberReceita (Long id){
        Receita receita = this.repository.getReferenceById(id);

        if (receita != null){
            if (receita.getParcelada() == true) {
                this.parcelaService.receberParcelasReceitas(receita.getId());
            }

            receita.setRecebida(true);
            this.repository.save(receita);
        }
    }

    public ArrayList<Receita> listarTudoReceitaTeste(){
        return this.repository.findAll();
    }

}
