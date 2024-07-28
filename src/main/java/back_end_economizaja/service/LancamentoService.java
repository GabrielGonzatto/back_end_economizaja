package back_end_economizaja.service;

import back_end_economizaja.infra.security.TokenService;
import back_end_economizaja.model.categoria.Categoria;
import back_end_economizaja.model.categoria.categoriaDTO.MaioresGastosMesHomeDTO;
import back_end_economizaja.model.cliente.ClienteRepository;
import back_end_economizaja.model.lancamento.DTO.*;
import back_end_economizaja.model.lancamento.Lancamento;
import back_end_economizaja.model.lancamento.LancamentoRepository;
import back_end_economizaja.model.parcela.Parcela;
import back_end_economizaja.model.parcela.ParcelaRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private ParcelaRepository parcelaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ParcelaService parcelaService;

    public void cadastrar (CadastrarLancamentoDTO lancamentoDTO, HttpServletRequest request) {
        Lancamento lancamento = new Lancamento(lancamentoDTO.tipo(), lancamentoDTO.descricao(), lancamentoDTO.valor(), lancamentoDTO.data(), lancamentoDTO.paga_recebida(), lancamentoDTO.fixa(), lancamentoDTO.parcelada(), lancamentoDTO.numero_de_parcelas(), true, new Categoria((long) lancamentoDTO.id_categoria()), this.clienteService.geraCliente(this.tokenService.recuperarIdDoToken(request)));
        Long id = this.repository.save(lancamento).getId();
        System.out.println(id);
        if (lancamentoDTO.fixa() == true) {
            if (lancamentoDTO.paga_recebida() == true) {
                Lancamento novoLancamento = new Lancamento(lancamentoDTO.tipo(), lancamentoDTO.descricao(), lancamentoDTO.valor(), lancamentoDTO.data().plusMonths(1), false, lancamentoDTO.fixa(), lancamentoDTO.parcelada(), lancamentoDTO.numero_de_parcelas(), true, new Categoria((long) lancamentoDTO.id_categoria()), this.clienteService.geraCliente(this.tokenService.recuperarIdDoToken(request)));
                this.repository.save(novoLancamento);
            }

        }

        else if (lancamentoDTO.parcelada() == true) {
            this.parcelaService.cadastrarListaDeParcelas(lancamentoDTO, id);
        }
    }

    public void deletar (ListagemLancamentosPagarReceber lancamento) {
        if (lancamento.tipo().equals("lancamento")) {
            Lancamento l = this.repository.getReferenceById(lancamento.id());
            l.setAtivo(false);

            this.repository.save(l);
        }

        if (lancamento.tipo().equals("parcela")) {
            Parcela p = this.parcelaRepository.getReferenceById(lancamento.id());
            p.setAtivo(false);

            this.parcelaRepository.save(p);
        }
    }

    public DadosRelatorios dadosRelatorios (DataLancamentosConsultaDTO dataLancamento, HttpServletRequest request) {
        LocalDate dataDeHoje = LocalDate.now();

        ArrayList<Lancamento> lancamentos = new ArrayList<>();
        ArrayList<Parcela> parcelas = new ArrayList<>();

        ArrayList<ListagemLancamentosPagarReceber> receitas = new ArrayList<>();
        ArrayList<ListagemLancamentosPagarReceber> despesas = new ArrayList<>();

        if (dataLancamento.dataLancamentoConsulta().equals("hoje")) {
            lancamentos = this.repository.findAllLancamentosNaoParceladosDeHoje(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getDayOfMonth(), dataDeHoje.getMonthValue(), dataDeHoje.getYear());
            parcelas = this.parcelaRepository.findAllParcelasDeHoje(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getDayOfMonth(), dataDeHoje.getMonthValue(), dataDeHoje.getYear());
        }
        if (dataLancamento.dataLancamentoConsulta().equals("mes")) {
            lancamentos = this.repository.findAllLancamentosNaoParceladosDoMes(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getMonthValue(), dataDeHoje.getYear());
            parcelas = this.parcelaRepository.findAllParcelasDoMes(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getMonthValue(), dataDeHoje.getYear());
        }
        if (dataLancamento.dataLancamentoConsulta().equals("ano")) {
            lancamentos = this.repository.findAllLancamentosNaoParceladosDoAno(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getYear());
            parcelas = this.parcelaRepository.findAllParcelasDoAno(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getYear());
        }
        if (dataLancamento.dataLancamentoConsulta().equals("dataEspecifica")) {
            lancamentos = this.repository.findAllLancamentosNaoParceladosEntreDatas(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataLancamento.dataInicial(), dataLancamento.dataFinal());
            parcelas = this.parcelaRepository.findAllLancamentosNaoParceladosEntreDatas(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataLancamento.dataInicial(), dataLancamento.dataFinal());

        }

        for (Lancamento lancamento : lancamentos) {
            if (lancamento.getTipo().equals("receita")) {
                receitas.add(new ListagemLancamentosPagarReceber(lancamento.getId(), lancamento.getDescricao(), "lancamento", lancamento.getData(), lancamento.getValor(), lancamento.getPaga_recebida()));
            }
            if (lancamento.getTipo().equals("despesa")) {
                despesas.add(new ListagemLancamentosPagarReceber(lancamento.getId(), lancamento.getDescricao(), "lancamento", lancamento.getData(), lancamento.getValor(), lancamento.getPaga_recebida()));
            }
        }

        for (Parcela parcela : parcelas) {
            if (parcela.getLancamento().getTipo().equals("receita")) {
                receitas.add(new ListagemLancamentosPagarReceber(parcela.getId(), parcela.getLancamento().getDescricao(), "parcela", parcela.getData(), parcela.getValor(), parcela.getPaga_recebida()));
            }
            if (parcela.getLancamento().getTipo().equals("despesa")) {
                despesas.add(new ListagemLancamentosPagarReceber(parcela.getId(), parcela.getLancamento().getDescricao(), "parcela", parcela.getData(), parcela.getValor(), parcela.getPaga_recebida()));
            }
        }

        return new DadosRelatorios(ordenarPorData(receitas), ordenarPorData(despesas));
    }

    public DadosHome dadosHome(HttpServletRequest request) {
        LocalDate dataDeHoje = LocalDate.now();
        LocalDate dataAtrasados = LocalDate.of(dataDeHoje.getYear(), dataDeHoje.getMonth(), 1);
        String primeiro_nome = "";

        ArrayList<Lancamento> lancamentosDoMes = new ArrayList<>();
        ArrayList<Lancamento> lancamentosAtrasados = new ArrayList<>();
        ArrayList<Lancamento> lancamentosPagos =  new ArrayList<>();

        ArrayList<Parcela> parcelasDoMes =  new ArrayList<>();
        ArrayList<Parcela> parcelasAtrasadas =  new ArrayList<>();
        ArrayList<Parcela> parcelasPagas =  new ArrayList<>();


        lancamentosDoMes = this.repository.findAllLancamentosNaoParceladosDoMes(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getMonthValue(), dataDeHoje.getYear());
        lancamentosAtrasados = this.repository.findAllLancamentosNaoParceladosDoAtrasados(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataAtrasados);
        lancamentosPagos = this.repository.findAllLancamentosNaoParceladosPagos(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)));

        parcelasDoMes = this.parcelaRepository.findAllParcelasDoMes(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getMonthValue(), dataDeHoje.getYear());
        parcelasAtrasadas = this.parcelaRepository.findAllParcelasAtrasadas(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), String.valueOf(dataAtrasados));
        parcelasPagas = this.parcelaRepository.findAllParcelasPagas(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)));

        ArrayList<ListagemLancamentosPagarReceber> contasAPagar = new ArrayList<>();
        ArrayList<ListagemLancamentosPagarReceber> contasAPagarAtrasadas = new ArrayList<>();

        ArrayList<ListagemLancamentosPagarReceber> contasAReceber = new ArrayList<>();
        ArrayList<ListagemLancamentosPagarReceber> contasAReceberAtrasadas = new ArrayList<>();

        primeiro_nome = this.clienteRepository.findNameClienteById(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)));

        Double saldoGeral = 0.0;
        Double saldoGeralReceita = 0.0;
        Double saldoGeralDespesa = 0.0;

        Double receitaMensal = 0.0;
        Double despesaMensal = 0.0;

        // calcula receita e despesa geral pagas
        if (lancamentosPagos != null) {
            for (Lancamento lancamento : lancamentosPagos) {
                if (lancamento.getTipo().equals("receita")) {
                    saldoGeralReceita = saldoGeralReceita + lancamento.getValor();
                }

                if (lancamento.getTipo().equals("despesa")) {
                    saldoGeralDespesa = saldoGeralDespesa + lancamento.getValor();
                }
            }
        }

        // calcula parcelas geral pagas
        if (parcelasDoMes != null) {
            for (Parcela parcela : parcelasPagas) {
                if (parcela.getLancamento().getTipo().equals("receita")) {
                    saldoGeralReceita = saldoGeralReceita + parcela.getValor();
                }

                if (parcela.getLancamento().getTipo().equals("despesa")) {
                    saldoGeralDespesa = saldoGeralDespesa + parcela.getValor();
                }
            }
        }

        // calcula receita e despesa mensal de lançamentos do mês
        if (lancamentosDoMes != null) {
            for (Lancamento lancamento : lancamentosDoMes) {
                if (lancamento.getTipo().equals("receita")) {
                    receitaMensal = receitaMensal + lancamento.getValor();
                }

                if (lancamento.getTipo().equals("despesa")) {
                    despesaMensal = despesaMensal + lancamento.getValor();
                }
            }
        }

        // calcula receita e despesa mensal de parcelas do mês
        if (parcelasDoMes != null) {
            for (Parcela parcela : parcelasDoMes) {
                if (parcela.getLancamento().getTipo().equals("receita")) {
                    receitaMensal = receitaMensal + parcela.getValor();
                }

                if (parcela.getLancamento().getTipo().equals("despesa")) {
                    despesaMensal = despesaMensal + parcela.getValor();
                }
            }
        }

        saldoGeral = saldoGeralReceita - saldoGeralDespesa;

        for (Lancamento lancamento : lancamentosDoMes) {
            if (lancamento.getTipo().equals("receita") && lancamento.getPaga_recebida() == false) {
                contasAReceber.add(new ListagemLancamentosPagarReceber(lancamento.getId(), lancamento.getDescricao(), "lancamento", lancamento.getData(), lancamento.getValor(), lancamento.getPaga_recebida()));
            }

            if (lancamento.getTipo().equals("despesa") && lancamento.getPaga_recebida() == false) {
                contasAPagar.add(new ListagemLancamentosPagarReceber(lancamento.getId(), lancamento.getDescricao(), "lancamento", lancamento.getData(), lancamento.getValor(), lancamento.getPaga_recebida()));
            }


        }

        for (Lancamento lancamento : lancamentosAtrasados) {
            if (lancamento.getTipo().equals("receita")  && lancamento.getPaga_recebida() == false) {
                contasAReceberAtrasadas.add(new ListagemLancamentosPagarReceber(lancamento.getId(), lancamento.getDescricao(), "lancamento", lancamento.getData(), lancamento.getValor(), lancamento.getPaga_recebida()));
            }

            if (lancamento.getTipo().equals("despesa")  && lancamento.getPaga_recebida() == false) {
                contasAPagarAtrasadas.add(new ListagemLancamentosPagarReceber(lancamento.getId(), lancamento.getDescricao(), "lancamento", lancamento.getData(), lancamento.getValor(), lancamento.getPaga_recebida()));
            }
        }

        for (Parcela parcela : parcelasDoMes) {
            if (parcela.getLancamento().getTipo().equals("receita") && parcela.getLancamento().getPaga_recebida() == false) {
                contasAReceber.add(new ListagemLancamentosPagarReceber(parcela.getId(), parcela.getLancamento().getDescricao(), "parcela", parcela.getData(), parcela.getValor(), parcela.getPaga_recebida()));
            }
            if (parcela.getLancamento().getTipo().equals("despesa") && parcela.getLancamento().getPaga_recebida() == false) {
                contasAPagar.add(new ListagemLancamentosPagarReceber(parcela.getId(), parcela.getLancamento().getDescricao(), "parcela", parcela.getData(), parcela.getValor(), parcela.getPaga_recebida()));
            }
        }

        for (Parcela parcela : parcelasAtrasadas) {
            if (parcela.getLancamento().getTipo().equals("receita") && parcela.getLancamento().getPaga_recebida() == false) {
                contasAReceberAtrasadas.add(new ListagemLancamentosPagarReceber(parcela.getId(), parcela.getLancamento().getDescricao(), "parcela", parcela.getData(), parcela.getValor(), parcela.getPaga_recebida()));
            }
            if (parcela.getLancamento().getTipo().equals("despesa") && parcela.getLancamento().getPaga_recebida() == false) {
                contasAPagarAtrasadas.add(new ListagemLancamentosPagarReceber(parcela.getId(), parcela.getLancamento().getDescricao(), "parcela", parcela.getData(), parcela.getValor(), parcela.getPaga_recebida()));
            }
        }

        return new DadosHome(primeiro_nome, arruma_casas_valor(saldoGeral, 2), arruma_casas_valor(receitaMensal, 2), arruma_casas_valor(despesaMensal, 2), ordenarPorData(contasAPagar), ordenarPorData(contasAPagarAtrasadas), ordenarPorData(contasAReceber), ordenarPorData(contasAReceberAtrasadas), calculaMaioresGastosMesHomeCategoria(lancamentosDoMes, parcelasDoMes, "despesa"));
    }

    public ArrayList<ListagemLancamentosPagarReceber> getLancamentosData (DataLancamentosConsultaDTO dataLancamento, HttpServletRequest request) {
        LocalDate dataDeHoje = LocalDate.now();

        ArrayList<Lancamento> lancamentos = new ArrayList<>();
        ArrayList<Parcela> parcelas = new ArrayList<>();

        if (dataLancamento.dataLancamentoConsulta().equals("hoje")) {
            lancamentos = this.repository.findAllLancamentosNaoParceladosDeHoje(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getDayOfMonth(), dataDeHoje.getMonthValue(), dataDeHoje.getYear());
            parcelas = this.parcelaRepository.findAllParcelasDeHoje(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getDayOfMonth(), dataDeHoje.getMonthValue(), dataDeHoje.getYear());
        }
        if (dataLancamento.dataLancamentoConsulta().equals("mes")) {
            lancamentos = this.repository.findAllLancamentosNaoParceladosDoMes(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getMonthValue(), dataDeHoje.getYear());
            parcelas = this.parcelaRepository.findAllParcelasDoMes(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getMonthValue(), dataDeHoje.getYear());
        }
        if (dataLancamento.dataLancamentoConsulta().equals("ano")) {
            lancamentos = this.repository.findAllLancamentosNaoParceladosDoAno(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getYear());
            parcelas = this.parcelaRepository.findAllParcelasDoAno(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataDeHoje.getYear());
        }
        if (dataLancamento.dataLancamentoConsulta().equals("dataEspecifica")) {
            lancamentos = this.repository.findAllLancamentosNaoParceladosEntreDatas(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataLancamento.dataInicial(), dataLancamento.dataFinal());
            parcelas = this.parcelaRepository.findAllLancamentosNaoParceladosEntreDatas(Integer.parseInt(this.tokenService.recuperarIdDoToken(request)), dataLancamento.dataInicial(), dataLancamento.dataFinal());

        }

        return ordenarPorData(dataLancamento.tranformaDTO(lancamentos, parcelas));
    }

    public void pagarDespagarLancamento(ListagemLancamentosPagarReceber item) {
        if (item.tipo().equals("lancamento")) {
            Lancamento lancamento = this.repository.getReferenceById(item.id());

            if (lancamento.getPaga_recebida() == true) {
                lancamento.setPaga_recebida(false);
            } else {
                lancamento.setPaga_recebida(true);
                if (lancamento.getFixa()) {
                    this.repository.save(new Lancamento(lancamento.getTipo(), lancamento.getDescricao(), lancamento.getValor(), lancamento.getData().plusMonths(1), false, true, lancamento.getParcelada(), lancamento.getNumero_de_parcelas(), lancamento.getAtivo(), lancamento.getCategoria(), lancamento.getCliente()));
                }
            }

            this.repository.save(lancamento);
        }

        if (item.tipo().equals("parcela")) {
            Parcela parcela = this.parcelaRepository.getReferenceById(item.id());

            if (parcela.getPaga_recebida() == true) {
                parcela.setPaga_recebida(false);
            } else {
                parcela.setPaga_recebida(true);
            }
            this.parcelaRepository.save(parcela);
        }

    }

    public Double arruma_casas_valor(double value, int casas) {
        if (casas < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(casas, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    public ArrayList<MaioresGastosMesHomeDTO> calculaMaioresGastosMesHomeCategoria (ArrayList<Lancamento> lancamentosDoMes, ArrayList<Parcela> parcelasDoMes, String tipo) {
        ArrayList<MaioresGastosMesHomeDTO> maioresGastosMesHomeDTO = new ArrayList<MaioresGastosMesHomeDTO>();
        ArrayList<String> categorias = getCategoriasMes(lancamentosDoMes, parcelasDoMes, tipo);

        Double valorTotalMes = getValorTotalMes(lancamentosDoMes, parcelasDoMes, tipo);
        Double valor = 0.0;

        for (String categoria : categorias) {
            for (Lancamento lancamento : lancamentosDoMes) {
                if (lancamento.getCategoria().getTipo().equals(tipo)) {
                    if (categoria.equals(lancamento.getCategoria().getNome())) {
                        valor += lancamento.getValor();
                    }
                }
            }

            for (Parcela parcela : parcelasDoMes) {
                if (parcela.getLancamento().getCategoria().getTipo().equals(tipo)) {
                    if (categoria.equals(parcela.getLancamento().getCategoria().getNome())) {
                        valor += parcela.getValor();
                    }
                }
            }

            maioresGastosMesHomeDTO.add(new MaioresGastosMesHomeDTO(categoria, valor, arruma_casas_valor(((valor / valorTotalMes) * 100), 2)));
            valor = 0.0;
        }

        return maioresGastosMesHomeDTO;
    }

    private Double getValorTotalMes(ArrayList<Lancamento> lancamentosDoMes, ArrayList<Parcela> parcelasDoMes, String tipo) {
        Double valorTotal = 0.0;

        for (Lancamento lancamento : lancamentosDoMes) {
            if (lancamento.getCategoria().getTipo().equals(tipo)) {
                valorTotal += lancamento.getValor();
            }
        }

        for (Parcela parcela : parcelasDoMes) {
            if (parcela.getLancamento().getCategoria().getTipo().equals(tipo)) {
                valorTotal += parcela.getValor();
            }
        }
        return valorTotal;
    }

    private ArrayList<String> getCategoriasMes(ArrayList<Lancamento> lancamentosDoMes, ArrayList<Parcela> parcelasDoMes, String tipo) {
        Set<String> categorias = new HashSet<>();

        for (Lancamento lancamento : lancamentosDoMes) {
            if (lancamento.getCategoria().getTipo().equals(tipo)) {
                categorias.add(lancamento.getCategoria().getNome());
            }
        }

        for (Parcela parcela : parcelasDoMes) {
            if (parcela.getLancamento().getCategoria().getTipo().equals(tipo)) {
                categorias.add(parcela.getLancamento().getCategoria().getNome());
            }
        }

        return new ArrayList<>(categorias);
    }

    private ArrayList<ListagemLancamentosPagarReceber> ordenarPorData (ArrayList<ListagemLancamentosPagarReceber> lista) {
        Collections.sort(lista, new Comparator<ListagemLancamentosPagarReceber>() {
            @Override
            public int compare(ListagemLancamentosPagarReceber o1, ListagemLancamentosPagarReceber o2) {
                return o1.data().compareTo(o2.data());
            }
        });
        return lista;
    }

}