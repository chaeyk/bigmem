# 이것은
Java 에서 MALLOC_ARENA_MAX 환경 변수값에 따라 process memory 가 어떻게 변화하는지 보려고 만든 간단한 프로그램.

# 실행방법
```bash
$ MALLOC_ARENA_MAX=1 ./gradlew bootRun
```

# 테스트
스레드를 여러개 생성해서 allocSize 크기의 byte array를 count 개 만든다.
```bash
$ curl localhost:8080/alloc/{threads}/{allocSize}/{count}
```