package com.group1.parking_management.service;

import com.group1.parking_management.dto.response.RevenueStatisticsResponse;
import com.group1.parking_management.dto.response.TrafficStatisticResponse;

public interface StatisticService {
    public RevenueStatisticsResponse getMonthlyRevenue(int month, int year);

    public TrafficStatisticResponse getMonthlyTraffic(int month, int year);
}
