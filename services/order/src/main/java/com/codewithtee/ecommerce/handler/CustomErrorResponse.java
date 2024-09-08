package com.codewithtee.ecommerce.handler;

import java.util.Map;

public record CustomErrorResponse(
        Map<String, String> errors
) {

}
