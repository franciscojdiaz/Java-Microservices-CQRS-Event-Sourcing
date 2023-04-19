package com.banking.account_cmd.api.dto;

import com.banking.account_common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAccountResponse extends BaseResponse {

    private String id;

    public OpenAccountResponse(String message, String id) {
        super(message);
        this.id = id;
    }

}
