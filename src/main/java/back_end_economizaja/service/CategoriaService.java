package back_end_economizaja.service;

import back_end_economizaja.model.categoria.Categoria;
import back_end_economizaja.model.categoria.CategoriaRepository;
import back_end_economizaja.model.categoria.categoriaDTO.CadastrarCategoriaDTO;
import back_end_economizaja.model.categoria.categoriaDTO.DadosCategoriaDTO;
import back_end_economizaja.model.categoria.categoriaDTO.EditarCategoriaDTO;
import back_end_economizaja.model.cliente.Cliente;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ClienteService service;

    public void cadastrar(CadastrarCategoriaDTO categoria){
        Cliente c = this.service.buscaCliente(1L);

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

    public ArrayList<DadosCategoriaDTO> listarCategoriasDeReceita(){
        //Cliente c = this.service.buscaCliente(id);

        ArrayList<Categoria> categorias = this.repository.findCategoriaByReceita(/*c.getId().intValue()*/);
        ArrayList<DadosCategoriaDTO> categoriaDTOS = new ArrayList<>();

        for (Categoria categoria : categorias){
            categoriaDTOS.add(new DadosCategoriaDTO(categoria.getId(), categoria.getNome()));
        }

        return categoriaDTOS;
    }

    public ArrayList<DadosCategoriaDTO> listarCategoriasDeDespesa(/*Long id*/){
        //Cliente c = this.service.buscaCliente(id);

        ArrayList<Categoria> categorias = this.repository.findCategoriaByDespesa(/*c.getId().intValue()*/);
        ArrayList<DadosCategoriaDTO> categoriaDTOS = new ArrayList<>();

        for (Categoria categoria : categorias){
            categoriaDTOS.add(new DadosCategoriaDTO(categoria.getId(), categoria.getNome()));
        }

        return categoriaDTOS;
    }

    public ArrayList<Categoria> listarTudoCategoriaTeste(){
        return this.repository.findAll();
    }

}
