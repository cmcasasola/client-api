package pe.com.home.clientapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.home.clientapi.model.dto.ClientDTO;
import pe.com.home.clientapi.model.dto.KpiClient;
import pe.com.home.clientapi.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/listclientes")
    public List<ClientDTO> listClients() {
        return this.clientService.findAll();
    }

    @GetMapping("/kpideclientes")
    public KpiClient getKpiClient() {
        return this.clientService.getKpi();
    }

    @PostMapping("/creacliente")
    public ClientDTO create(@RequestBody ClientDTO clientDTO) {
        return this.clientService.save(clientDTO);
    }
}
