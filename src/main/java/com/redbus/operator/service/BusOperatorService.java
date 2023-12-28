package com.redbus.operator.service;

import com.redbus.operator.payload.BusOperatorDto;
import com.redbus.operator.payload.BusOperatorResponse;

public interface BusOperatorService {
    BusOperatorDto scheduleBus(BusOperatorDto busOperatorDto);

    BusOperatorResponse getAllBusOperatorDetails(int pageNo, int pageSize);
}
