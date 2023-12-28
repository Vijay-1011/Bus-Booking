package com.redbus.user.conroller;

import com.redbus.user.payload.BusBookingDto;
import com.redbus.user.payload.PassengerDto;
import com.redbus.user.service.BusBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BusBookingController {

    @Autowired
    private BusBookingService busBookingService;
    @PostMapping(value = "/bookbus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BusBookingDto> bookBus(@RequestParam("busId") String busId,
                                                @RequestBody PassengerDto passengerDto){

        BusBookingDto busBookingDto = busBookingService.bookBus(busId, passengerDto);
        return new ResponseEntity<>(busBookingDto, HttpStatus.OK);

    }
}
