package az.ingress.ms_product.scheduler;

import az.ingress.ms_product.service.abstraction.AdminProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductScheduler {

    private final AdminProductService adminProductService;

    //    @Scheduled(fixedDelayString = "PT1M")
    @Scheduled(cron = "0 0 * * * *")
    @SchedulerLock(name = "deleteAllRejectedProducts", lockAtLeastFor = "PT50M", lockAtMostFor = "PT1H")
    public void deleteAllRejectedProducts() {
        log.info("ActionLog.deleteAllRejectedProducts.info: started thread: {}", Thread.currentThread().getName());
        adminProductService.deleteAllRejectedProducts();
        log.info("ActionLog.deleteAllRejectedProducts.info: ended thread: {}", Thread.currentThread().getName());
    }
}
