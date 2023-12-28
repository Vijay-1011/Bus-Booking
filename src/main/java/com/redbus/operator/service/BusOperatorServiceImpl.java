package com.redbus.operator.service;

import com.redbus.operator.entity.BusOperator;
import com.redbus.operator.entity.TicketCost;
import com.redbus.operator.payload.BusOperatorDto;
import com.redbus.operator.payload.BusOperatorResponse;
import com.redbus.operator.repository.BusOperatorRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BusOperatorServiceImpl implements BusOperatorService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BusOperatorRepository busOperatorRepository;


    @Override
    @Transactional
    public BusOperatorDto scheduleBus(BusOperatorDto busOperatorDto) {
        BusOperator busOperator = mapToEntity(busOperatorDto);

        TicketCost ticketCost=new TicketCost();
        ticketCost.setTicketId(busOperatorDto.getTicketCost().getTicketId());
        ticketCost.setTicketPrice(busOperatorDto.getTicketCost().getTicketPrice());
        ticketCost.setCode(busOperatorDto.getTicketCost().getCode());
        ticketCost.setDiscountPrice(busOperatorDto.getTicketCost().getDiscountPrice());


        String busId = UUID.randomUUID().toString();
        busOperator.setBusId(busId);
        ticketCost.setBusOperator(busOperator);
        busOperator.setTicketCost(ticketCost);
        BusOperator savedBusOperator = busOperatorRepository.save(busOperator);




        BusOperatorDto dto = mapToDto(savedBusOperator);
        return dto;

    }

    @Override
    public BusOperatorResponse getAllBusOperatorDetails(int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNo, pageSize);
        Page<BusOperator> busOperatorsPage = busOperatorRepository.findAll(pageable);
        List<BusOperator> busOperatorDetails= busOperatorsPage.getContent();
        List<BusOperatorDto> dto = busOperatorDetails.stream().map(busOperator -> mapToDto(busOperator)).collect(Collectors.toList());

        BusOperatorResponse response=new BusOperatorResponse();
        response.setDto(dto);
        response.setPageNo(busOperatorsPage.getNumber());
        response.setPageSize(busOperatorsPage.getSize());
        response.setTotalPages(busOperatorsPage.getTotalPages());
        response.setLastPage(busOperatorsPage.isLast());
        return response;

    }

    public BusOperator mapToEntity(BusOperatorDto busOperatorDto){
        BusOperator busOperator = modelMapper.map(busOperatorDto, BusOperator.class);
        return busOperator;
    }
    public BusOperatorDto mapToDto(BusOperator busOperator){
        BusOperatorDto busOperatorDto = modelMapper.map(busOperator, BusOperatorDto.class);
        return busOperatorDto;
    }
}
