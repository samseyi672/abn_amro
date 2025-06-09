package com.abn_amro.usermanagment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private boolean hasError;
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

    public ApiResponse(String statusCode, String url, String statusMsg, T data,boolean hasError) {
        this.statusCode = statusCode;
        this.url = url;
        this.statusMsg = statusMsg;
        this.data = data;
        this.hasError=hasError;
    }

    public ApiResponse() {
    }

    public static <T> ApiResponse<T> success(T data, String url, String statusCode, String statusMsg,boolean hasError) {
        return new ApiResponse<>(statusCode,url,statusMsg,data,hasError);
    }

    public static <T> ApiResponse<T> failure(T data, String url, String statusCode, String statusMsg,boolean hasError) {
        return new ApiResponse<>(statusCode,url,statusMsg,data,hasError);
    }
}





