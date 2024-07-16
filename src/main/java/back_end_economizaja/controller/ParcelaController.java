package back_end_economizaja.controller;

import back_end_economizaja.model.parcela.Parcela;
import back_end_economizaja.service.ParcelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/parcela")
public class ParcelaController {

    @Autowired
    private ParcelaService service;

    @GetMapping("/pagarDespagarParcela/{id}")
    @Transactional
    public ResponseEntity<Parcela> pagarDespagarParcela(@PathVariable Long id){
        this.service.pagarDespagarParcela(id);
        return ResponseEntity.ok().build();
    }
}
