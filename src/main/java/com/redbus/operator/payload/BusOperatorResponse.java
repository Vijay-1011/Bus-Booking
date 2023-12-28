package com.redbus.operator.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusOperatorResponse {

    private List<BusOperatorDto> dto;
    private int pageNo;
    private int totalPages;
    private Boolean lastPage;
    private int pageSize;

}
