package com.redbus.user.conroller;

import com.redbus.user.payload.BusDto;
import com.redbus.user.service.BusSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/bus")
public class BusSearchController {

    private static final Logger logger = LoggerFactory.getLogger(BusSearchController.class);
    @Autowired
    private BusSearchService busSearchService;


    @GetMapping("/searchbus")
    List<BusDto> searchBus(
            @RequestParam("departureCity") String departureCity,
            @RequestParam("arrivalCity") String arrivalCity,
            @RequestParam("departureDate")
            @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate departureDate
    ){
        logger.info("Received request with departureCity={}, arrivalCity={}, departureDate={}",
                departureCity, arrivalCity, departureDate);

        List<BusDto> availableBuses=busSearchService.searchBus(departureCity,arrivalCity,departureDate);


        logger.info("Returning {} available buses", availableBuses.size());
        return availableBuses;
    }
}
