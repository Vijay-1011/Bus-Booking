package com.redbus.operator.controller;

import com.redbus.operator.payload.BusOperatorDto;
import com.redbus.operator.payload.BusOperatorResponse;
import com.redbus.operator.service.BusOperatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bus_operator")
public class BusOperatorController {

    private BusOperatorService busOperatorService;

    public BusOperatorController(BusOperatorService busOperatorService) {
        this.busOperatorService = busOperatorService;
    }
@PostMapping("/create")
    public ResponseEntity<BusOperatorDto> scheduleBus(@RequestBody BusOperatorDto busOperatorDto){
        BusOperatorDto dto=busOperatorService.scheduleBus(busOperatorDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping
    public BusOperatorResponse getAllBusOperatorDetails(
            @RequestParam(name = "pageNo",required = false,defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize",required = false,defaultValue = "5") int pageSize

    ){
       BusOperatorResponse response= busOperatorService.getAllBusOperatorDetails(pageNo,pageSize);
       return response;
    }
}
