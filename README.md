## GPT-API 브랜치 설명
기능 별로 브랜치를 나눴고, 가장 성능이 좋은 방법을 사용할 예정입니다.

* Feat/#1 => 영어로 번역한 후 gpt에 되묻기 (성능이 좋지 않아 사용 x)
* Feat/#2 => Few shot skill을 이용해서 gpt에 요청할 때 칭찬의 예시도 함께 전송 **main에 Merge 완료**
* Feat/#3 => WebClient 방식으로 외부 api 사용하기 **main에 Merge 완료**
* Feat/#5 => GPT 4.0으로 변경
* Feat/#6 => #5(GPT4.0)로 부터 branch 생성. #2의 Few shot skill을 적용해서 여러 감사일기에도 독립된 칭찬이 나오게 구현
* Feat/#8 => #3(WebClient)로 부터 branch 생성. 비속어 필터링 구현
* Feat/#9 => #8(비속어 필터링)로 부터 branch 생성. AMQP 구현을 위한 RabbitMQ 적용 (구현 잠시 멈춤)
* Feat/#10 => #8(비속어 필터링)로 부터 branch 생성. Postgresql 데이터베이스에 칭찬 저장. **모델 GPT 3.5 최종본**
* Feat/#12 => **모델 GPT 4.0 최종본**
