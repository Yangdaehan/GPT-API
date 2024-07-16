## Feat/#1 브랜치 설명
영어로 번역한 후 GPT에 **되묻기** 구현

1. 감사일기를 받으면 영어로 번역해달라고 GPT에 요청
  https://github.com/Yangdaehan/GPT-API/blob/1a76bb7d0e978e276176e0647dc162c08a80dbf8/src/main/java/org/sopt/gptapi/service/ChatService.java#L35-L36


2. 번역문과 함께 칭찬 피드백 요청
   https://github.com/Yangdaehan/GPT-API/blob/1a76bb7d0e978e276176e0647dc162c08a80dbf8/src/main/java/org/sopt/gptapi/service/ChatService.java#L37-L39


-> 칭찬 성능이 개선되지 않고 호출이 두번 진행되므로 효율이 좋지 않다.
