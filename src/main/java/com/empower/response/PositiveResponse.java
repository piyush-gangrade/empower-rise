package com.empower.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositiveResponse extends Response {
    private Object data;

    public PositiveResponse(String message, Object data) {
        super(message, true);
        this.data = data;
    }
}
