package com.huuquy.request;

import com.huuquy.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restauarntId;
    private Address delivery;
}
