package com.abn_amro.recipemanagement.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
@Data
public class ApiResponse<T> {

    @Schema(
            description = "Status code in the response"
    )
    private String statusCode;

    @Schema(
            description = "url in the response"
    )
    private String url;

    @Schema(
            description = "Status message in the response"
    )
    private String statusMsg;

    @Schema(
            description = "Data in the response"
    )
    private T data ;

    public ApiResponse(String statusCode, String url, String statusMsg, T data) {
        this.statusCode = statusCode;
        this.url = url;
        this.statusMsg = statusMsg;
        this.data = data;
    }

    public ApiResponse() {
    }

    public static <T> ApiResponse<T> success(T data, String url, String statusCode, String statusMsg) {
        return new ApiResponse<>(statusCode,url,statusMsg,data);
    }

    public static <T> ApiResponse<T> failure(T data, String url, String statusCode, String statusMsg) {
        return new ApiResponse<>(statusCode,url,statusMsg,data);
    }
}



















































































































