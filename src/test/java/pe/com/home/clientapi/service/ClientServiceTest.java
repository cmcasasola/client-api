package pe.com.home.clientapi.service;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pe.com.home.clientapi.model.dto.ClientDTO;
import pe.com.home.clientapi.model.dto.KpiClient;
import pe.com.home.clientapi.model.entity.Client;
import pe.com.home.clientapi.repository.ClientRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientServiceTest {

    private ClientService clientService;
    private ClientRepository clientRepository;

    @BeforeAll
    public void init() {
        this.clientRepository = mock(ClientRepository.class);
        this.clientService = new ClientService(this.clientRepository);
    }

    @Test
    public void shouldReturnListOfClientsWhenCallFindAll() {
        List<Client> clientList = getList();
        when(this.clientRepository.findAll()).thenReturn(clientList);

        List<ClientDTO> clientDTOList = this.clientService.findAll();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(clientDTOList.get(0)).isEqualToComparingOnlyGivenFields(clientList.get(0),
                "name", "lastname", "age", "birthDate");
        softAssertions.assertThat(clientDTOList.get(0).getId()).isEqualTo(clientList.get(0).getId().intValue());
        softAssertions.assertThat(clientDTOList.get(1)).isEqualToComparingOnlyGivenFields(clientList.get(1),
                "name", "lastname", "age", "birthDate");
        softAssertions.assertThat(clientDTOList.get(1).getId()).isEqualTo(clientList.get(1).getId().intValue());
        softAssertions.assertThat(clientDTOList.get(2)).isEqualToComparingOnlyGivenFields(clientList.get(2),
                "name", "lastname", "age", "birthDate");
        softAssertions.assertThat(clientDTOList.get(2).getId()).isEqualTo(clientList.get(2).getId().intValue());
        softAssertions.assertAll();
    }

    @Test
    public void shouldReturnAverageAndKpiFromClientAge() {
        when(this.clientRepository.findAll()).thenReturn(getList());

        KpiClient kpiClient = this.clientService.getKpi();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(kpiClient.getAverage()).isEqualTo(41);
        softAssertions.assertThat(kpiClient.getStandardDeviation()).isEqualTo(11.43);
        softAssertions.assertAll();
    }

    @Test
    public void shouldReturnClientDTOWhenSaveClient() {
        Client client = Client.builder().id(1L).name("Cesar").lastname("Casasola").age(31)
                .birthDate(DateUtil.parse("1989-01-23")).build();

        ClientDTO clientDTO = ClientDTO.builder().id(1).name("Cesar").lastname("Casasola").age(31)
                .birthDate(DateUtil.parse("1989-01-23")).build();

        when(this.clientRepository.save(any(Client.class))).thenReturn(client);

        ClientDTO result = this.clientService.save(clientDTO);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result).isEqualToComparingOnlyGivenFields(client, "name", "lastname", "age",
                "birthDate");
        softAssertions.assertThat(result.getId()).isEqualTo(client.getId().intValue());
        softAssertions.assertAll();
    }

    private List<Client> getList() {
        return Arrays.asList(Client.builder().id(1L).name("Cesar").lastname("Casasola").age(31)
                        .birthDate(DateUtil.parse("1989-01-23")).build(),
                Client.builder().id(2L).name("Martin").lastname("Vizcarra").age(57)
                        .birthDate(DateUtil.parse("1963-03-22")).build(),
                Client.builder().id(2L).name("Maria Antonieta").lastname("Alva").age(35).birthDate(DateUtil.parse(
                        "1985-03-07")).build());
    }
}