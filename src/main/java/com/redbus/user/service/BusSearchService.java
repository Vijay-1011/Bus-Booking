package com.redbus.user.service;

import com.redbus.operator.entity.BusOperator;
import com.redbus.operator.repository.BusOperatorRepository;
import com.redbus.user.payload.BusDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusSearchService {

    @Autowired
    private ModelMapper modelMapper;



    @Autowired
    private BusOperatorRepository busOperatorRepository;



    public List<BusDto> searchBus(String departureCity, String arrivalCity, LocalDate departureDate){

        List<BusOperator> buses = busOperatorRepository.findByDepartureCityAndArrivalCityAndDepartureDate(departureCity, arrivalCity, departureDate);

        List<BusDto> dto = buses.stream().map(bus -> mapToDto(bus)).collect(Collectors.toList());
        return dto;

    }

    BusDto mapToDto(BusOperator busOperator){
        BusDto dto = modelMapper.map(busOperator, BusDto.class);
        return dto;
    }
    BusOperator mapToEntity(BusDto busDto){
        BusOperator busOperator = modelMapper.map(busDto, BusOperator.class);
        return busOperator;
    }
}
