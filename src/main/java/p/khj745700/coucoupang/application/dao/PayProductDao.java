package p.khj745700.coucoupang.application.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import p.khj745700.coucoupang.application.domain.payment.Payment;
import p.khj745700.coucoupang.application.domain.payment.product.PayProduct;
import p.khj745700.coucoupang.application.domain.payment.product.PayProductRepository;
import p.khj745700.coucoupang.application.exception.CustomException;
import p.khj745700.coucoupang.application.exception.NotFoundPayProductException;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayProductDao {

    PayProductRepository payProductRepository;

    public PayProduct save(PayProduct payProduct) {
        return payProductRepository.save(payProduct);
    }

    public PayProduct findByIdIfNotExistThrowException(Long id) {
        return payProductRepository.findById(id).orElseThrow(ExceptionHandler.NOT_FOUND.apply(id));
    }

    public List<PayProduct> findByPayment(Payment payment) {
        return payProductRepository.findByPayment(payment);
    }

    @Getter
    private static class ExceptionHandler {

        private static final Function<Long, Supplier<CustomException>> NOT_FOUND;

        static {
            NOT_FOUND = (Long info) -> {
                log.trace("결제상품을 찾을 수 없습니다. pk:{}", info);
                return NotFoundPayProductException::new;
            };
        }

    }

}
