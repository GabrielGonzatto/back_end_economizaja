package back_end_economizaja.service;

import back_end_economizaja.infra.security.TokenService;
import back_end_economizaja.model.categoria.Categoria;
import back_end_economizaja.model.categoria.CategoriaRepository;
import back_end_economizaja.model.categoria.categoriaDTO.*;
import back_end_economizaja.model.cliente.Cliente;
import back_end_economizaja.model.lancamento.Lancamento;
import back_end_economizaja.model.lancamento.LancamentoRepository;
import back_end_economizaja.model.parcela.Parcela;
import back_end_economizaja.model.parcela.ParcelaRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private ParcelaRepository parcelaRepository;

    @Autowired
    private LancamentoService lancamentoService;

    public void cadastrar(CadastrarCategoriaDTO categoria, HttpServletRequest request){
        Cliente c = this.clienteService.geraCliente(this.tokenService.recuperarIdDoToken(request));

        this.repository.save(new Categoria(categoria.nome(), categoria.tipo(), true, c));
    }

    public void editar(EditarCategoriaDTO categoria){
        Categoria c = this.repository.getReferenceById(categoria.id());
        c.setNome(categoria.nome());
        this.repository.save(c);
    }

    public void desativar(Long id){
        Categoria c = this.repository.getReferenceById(id);
        c.setAtivo(false);
        this.repository.save(c);
    }

    public MaioresGastosMesCategoriaDTO maioresGastosMesCategorias (HttpServletRequest request) {
        LocalDate dataDeHoje = LocalDate.now();

        ArrayList<Lancamento> lancamentosDoMes = new ArrayList<>();
        ArrayList<Parcela> parcelasDoMes =  new ArrayList<>();

        lancamentosDoMes = this.lancamentoRepository.findAllLancamentosNaoParceladosDoMes(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getMonthValue(), dataDeHoje.getYear());
        parcelasDoMes = this.parcelaRepository.findAllParcelasDoMes(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getMonthValue(), dataDeHoje.getYear());

        ArrayList<MaioresGastosMesHomeDTO> maioresGastosMesHomeReceita = this.lancamentoService.calculaMaioresGastosMesHomeCategoria(lancamentosDoMes, parcelasDoMes, "receita");
        ArrayList<MaioresGastosMesHomeDTO> maioresGastosMesHomeDespesa = this.lancamentoService.calculaMaioresGastosMesHomeCategoria(lancamentosDoMes, parcelasDoMes, "despesa");

        return new MaioresGastosMesCategoriaDTO(maioresGastosMesHomeReceita, maioresGastosMesHomeDespesa);
    }


    public ArrayList<DadosCategoriaDTO> listarCategoriasDeReceita(HttpServletRequest request){
        String id = this.tokenService.recuperarIdDoToken(request);
        ArrayList<Categoria> categorias = this.repository.findCategoriaByReceita(Integer.valueOf(id));
        ArrayList<DadosCategoriaDTO> categoriaDTOS = new ArrayList<>();

        for (Categoria categoria : categorias){
            categoriaDTOS.add(new DadosCategoriaDTO(categoria.getId(), categoria.getNome()));
        }

        return categoriaDTOS;
    }

    public ArrayList<DadosCategoriaDTO> listarCategoriasDeDespesa(HttpServletRequest request){
        String id = this.tokenService.recuperarIdDoToken(request);

        ArrayList<Categoria> categorias = this.repository.findCategoriaByDespesa(Integer.valueOf(id));
        ArrayList<DadosCategoriaDTO> categoriaDTOS = new ArrayList<>();

        for (Categoria categoria : categorias){
            categoriaDTOS.add(new DadosCategoriaDTO(categoria.getId(), categoria.getNome()));
        }

        return categoriaDTOS;
    }

}
