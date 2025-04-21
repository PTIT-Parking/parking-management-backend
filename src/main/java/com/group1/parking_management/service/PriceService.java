package com.group1.parking_management.service;

import java.util.List;

import com.group1.parking_management.dto.request.ChangePriceRequest;
import com.group1.parking_management.dto.response.PriceResponse;

public interface PriceService {
    public List<PriceResponse> getAllPrice();

    public PriceResponse updatePrice(String priceId, ChangePriceRequest request);
}
