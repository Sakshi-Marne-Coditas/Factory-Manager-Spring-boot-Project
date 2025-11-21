package com.FactoryManager.DTO;

import com.FactoryManager.Constants.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderPaymentRequestDto {
    @NotNull(message = "Id cannot be null")
    @NotBlank(message = "Id cannot be blank")
    @Positive(message = "Value must be greater than 0")
    private Long orderId;

    @NotNull(message = "payment method cannot be null")
    @NotBlank(message = "payment method cannot be blank")
    private PaymentMethod paymentMethod;
}