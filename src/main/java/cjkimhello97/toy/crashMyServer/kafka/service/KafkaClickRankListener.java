package cjkimhello97.toy.crashMyServer.kafka.service;

import cjkimhello97.toy.crashMyServer.click.dto.KafkaClickRankRequest;
import cjkimhello97.toy.crashMyServer.kafka.domain.ProcessedKafkaRequest;
import cjkimhello97.toy.crashMyServer.kafka.repository.ProcessedKafkaRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaClickRankListener {

    private final KafkaRequestSender kafkaRequestSender;
    private final ProcessedKafkaRequestRepository processedKafkaRequestRepository;

    @Transactional
    @KafkaListener(
            groupId = "clickRankListener",
            topics = "click-rank",
            containerFactory = "kafkaClickRankRequestContainerFactory"
    )
    public void listenClickRankTopic(
            KafkaClickRankRequest request,
            Acknowledgment acknowledgment
    ) {
        // 이미 처리한 적이 있는 메시지라면 예외 발생
        String uuid = request.getUuid();
        if (processedKafkaRequestRepository.existsByUuid(uuid)) {
            acknowledgment.acknowledge();
            return;
        }
        // 처리한 적이 없던 메시지이므로 처리
        kafkaRequestSender.convertAndSend("/sub/click-rank", request);
        // 처리했다면 처리했음을 기록(저장)
        processedKafkaRequestRepository.save(new ProcessedKafkaRequest(uuid));
        // 처리 및 저장했다면 커밋
        acknowledgment.acknowledge();

    }
}
