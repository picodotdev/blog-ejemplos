package io.github.picodotdev.blogbitix.asyncapicloudevents;

import java.math.BigDecimal;

public record EventPayload(String orderId, String customerId, BigDecimal total) {
}
