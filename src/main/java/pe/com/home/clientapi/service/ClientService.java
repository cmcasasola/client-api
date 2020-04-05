package pe.com.home.clientapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.home.clientapi.Util;
import pe.com.home.clientapi.model.dto.ClientDTO;
import pe.com.home.clientapi.model.dto.KpiClient;
import pe.com.home.clientapi.model.entity.Client;
import pe.com.home.clientapi.repository.ClientRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<ClientDTO> findAll() {
        return StreamSupport.stream(this.clientRepository.findAll().spliterator(), false)
                .map(t -> ClientDTO.builder().id(t.getId().intValue()).name(t.getName()).lastname(t.getLastname())
                        .age(t.getAge()).birthDate(
                                t.getBirthDate()).deadDate(Util.getDeadDate(t)).build())
                .collect(Collectors.toList());
    }

    public KpiClient getKpi() {
        Spliterator<Client> spliterator = this.clientRepository.findAll().spliterator();

        List<Integer> listAge = StreamSupport.stream(spliterator, false).map(Client::getAge)
                .collect(Collectors.toList());
        DoubleSummaryStatistics summaryStatistics = listAge.stream().mapToDouble(Integer::doubleValue).summaryStatistics();

        Double sumDeviation = 0.0;
        double average = summaryStatistics.getAverage();

        for (Integer age : listAge) {
            sumDeviation = sumDeviation + Math.pow(age - average, 2);
        }

        BigDecimal bd = new BigDecimal(Math.sqrt(sumDeviation / listAge.size()));
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return KpiClient.builder().average(average).standardDeviation(bd.doubleValue()).build();
    }

    public ClientDTO save(ClientDTO clientDTO) {
        Client client = Client.builder().name(clientDTO.getName()).lastname(clientDTO.getLastname())
                .age(clientDTO.getAge()).birthDate(clientDTO.getBirthDate()).build();

        Client savedClient = this.clientRepository.save(client);

        return ClientDTO.builder().id(savedClient.getId().intValue()).name(savedClient.getName())
                .lastname(savedClient.getLastname()).age(savedClient.getAge()).birthDate(savedClient.getBirthDate())
                .build();
    }
}
