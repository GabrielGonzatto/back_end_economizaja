package back_end_economizaja.controller;

import back_end_economizaja.model.categoria.Categoria;
import back_end_economizaja.model.categoria.categoriaDTO.CadastrarCategoriaDTO;
import back_end_economizaja.model.categoria.categoriaDTO.DadosCategoriaDTO;
import back_end_economizaja.model.categoria.categoriaDTO.EditarCategoriaDTO;
import back_end_economizaja.model.categoria.categoriaDTO.MaioresGastosMesCategoriaDTO;
import back_end_economizaja.service.CategoriaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/categoria")
@CrossOrigin("*")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<CadastrarCategoriaDTO> cadastrar(@RequestBody @Valid CadastrarCategoriaDTO categoria, HttpServletRequest request){
        this.service.cadastrar(categoria, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    @Transactional
    public ResponseEntity<EditarCategoriaDTO> editar(@RequestBody EditarCategoriaDTO categoria){
        this.service.editar(categoria);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/desativar/{id}")
    public ResponseEntity<Categoria> desativar(@PathVariable Long id){
        this.service.desativar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/maioresGastosMesCategorias")
    public ResponseEntity<MaioresGastosMesCategoriaDTO> maioresGastosMesCategorias(HttpServletRequest request){
        return ResponseEntity.ok(this.service.maioresGastosMesCategorias(request));
    }


    @GetMapping("/receita")
    public ResponseEntity<ArrayList<DadosCategoriaDTO>> listarCategoriasDeReceita(HttpServletRequest request){
        return ResponseEntity.ok(this.service.listarCategoriasDeReceita(request));
    }

    @GetMapping("/despesa")
    public ResponseEntity<ArrayList<DadosCategoriaDTO>> listarCategoriasDeDespesa(HttpServletRequest request){
        return ResponseEntity.ok(this.service.listarCategoriasDeDespesa(request));
    }

}
