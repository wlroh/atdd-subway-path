package atdd.station.service;

import atdd.station.entity.StationEntity;
import atdd.station.entity.StationListEntity;
import atdd.station.repository.StationRepository;
import atdd.station.usecase.StationDTO;
import atdd.station.usecase.StationListDTO;
import atdd.station.usecase.StationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService implements StationUseCase {

  @Autowired
  StationRepository stationRepository;

  private final StationModelMapper stationModelMapper = new StationModelMapper();

  @Override
  public StationDTO addStation(StationDTO stationDTO) {
    StationEntity stationEntity = stationRepository.save(
        stationModelMapper.DTOToEntity(stationDTO)
    ).get();
    return stationModelMapper.EntityToDTO(stationEntity);
  }

  @Override
  public StationListDTO getAllStation() {
    return stationModelMapper.ListEntityToDTO(
        new StationListEntity(stationRepository.findAll())
    );
  }

  @Override
  public StationDTO getStation(StationDTO stationDTO) {
    return stationModelMapper.EntityToDTO(
        stationRepository.getByname(
            stationDTO.getName()
        ).get()
    );
  }

  @Override
  public void removeStation(StationDTO stationDTO) {
    StationEntity target = stationRepository.getByname(stationDTO.getName()).get();
    stationRepository.removeByid(
        target.getId()
    );
  }
}