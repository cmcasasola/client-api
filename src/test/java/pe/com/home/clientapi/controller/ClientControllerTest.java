package pe.com.home.clientapi.controller;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pe.com.home.clientapi.model.dto.ClientDTO;
import pe.com.home.clientapi.model.dto.KpiClient;
import pe.com.home.clientapi.service.ClientService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientControllerTest {
    private ClientController clientController;
    private ClientService clientService;

    @BeforeAll
    public void init() {
        this.clientService = mock(ClientService.class);
        this.clientController = new ClientController(this.clientService);
    }

    @Test
    public void shouldListClientsSuccessfully() {
        List<ClientDTO> clientDTOList = getList();
        when(this.clientService.findAll()).thenReturn(clientDTOList);

        List<ClientDTO> listResult = this.clientController.listClients();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(listResult.get(0)).isEqualToComparingFieldByField(clientDTOList.get(0));
        softAssertions.assertThat(listResult.get(1)).isEqualToComparingFieldByField(clientDTOList.get(1));
        softAssertions.assertThat(listResult.get(2)).isEqualToComparingFieldByField(clientDTOList.get(2));
        softAssertions.assertAll();
    }

    @Test
    public void shouldReturnKpiClientSuccessfully() {
        KpiClient kpiClient = KpiClient.builder().average(34).standardDeviation(12.25).build();

        when(this.clientService.getKpi()).thenReturn(kpiClient);

        KpiClient kpiClientResult = this.clientController.getKpiClient();

        Assertions.assertThat(kpiClient).isEqualTo(kpiClientResult);
    }

    @Test
    public void shouldSaveClientSuccessfully() {
        ClientDTO clientDTO = ClientDTO.builder().id(1).name("Cesar").lastname("Casasola").age(31)
                .birthDate(DateUtil.parse("1989-01-23")).deadDate(DateUtil.parse("2100-01-01")).build();

        when(this.clientService.save(any(ClientDTO.class))).thenReturn(clientDTO);

        ClientDTO result = this.clientController.create(clientDTO);

        Assertions.assertThat(result).isEqualTo(clientDTO);
    }

    private List<ClientDTO> getList() {
        return Arrays.asList(ClientDTO.builder().id(1).name("Cesar").lastname("Casasola").age(31)
                        .birthDate(DateUtil.parse("1989-01-23")).deadDate(DateUtil.parse("2100-01-01")).build(),
                ClientDTO.builder().id(2).name("Martin").lastname("Vizcarra").age(57)
                        .birthDate(DateUtil.parse("1963-03-22")).deadDate(DateUtil.parse("2100-01-01")).build(),
                ClientDTO.builder().id(3).name("Maria Antonieta").lastname("Alva").age(35).birthDate(DateUtil.parse(
                        "1985-03-07")).deadDate(DateUtil.parse("2100-01-01")).build());
    }
}