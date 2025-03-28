package com.order.coffeeshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueuePositionResponse {
    private int position;
    private int estimatedWaitTime;

    public QueuePositionResponse(int position, int estimatedWaitTime) {
        this.position = position;
        this.estimatedWaitTime = estimatedWaitTime;
    }

}