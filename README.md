# 클린 아키텍처(헥사고날 아키텍처) 예제 구현

- [우아한 기술 블로그 'Spring Boot Kotlin Multi Module로 구성해보는 헥사고날 아키텍처'](https://techblog.woowahan.com/12720/) 를 참고하였음
- 위 포스팅에서는 모듈을 Bootstrap Hexagon, Application Hexagon, Domain Hexagon, Framework Hexagon 으로 구분하고 있지만
  이 레포지토리의 예제에서는 api, application, domain, infrastructure 으로 모듈을 구분하고 있음(역할은 동일하고 명칭만 다름)

## 각 모듈 역할 설명

### api
- 

### application
-

### domain
- 핵심 도메인 로직을 담당하는 모듈
- 완전한 POJO 로 이루어져 있음
- 위 포스팅에서는 어떠한 의존성도 설정하지 않는다고 하지만, 나는 테스트를 위해서 예제 코드에서는 아래와 같이 일부 의존성을 설정함
  (위 포스팅에서는 아무런 의존성 없이 테스트 코드를 어떻게 작성했다는 것인지 모르겠음)
```groovy
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.9.2'
    testImplementation 'org.assertj:assertj-core:3.24.2'
}
```
- 유스케이스도 모르고, 기술과 관련(프레임워크 종류, 데이터 베이스 종류 등)해서도 아는 것이 하나도 없는 모듈(알아서도 안되고 알 필요도 없다)
- 이 모듈은 다른 모듈에 의존하지 않음. domain 은 클린아키텍처 구조상 가장 중심(내핵)에 위치한다. domain 은 외부로 어떠한 의존도 없이 독립적으로 존재해야 한다.
- 의존이 없으니 일단 domain 모듈을 제일 먼저 생성해서 구현할 수 있다. 데이터 베이스 뭘 쓸지, 인증은 어떻게 할지, 통신은 어떻게 할지 등 그러한 고민 없이 일단 핵심적인 도메인 규칙을 구현할 수 있다.

### infrastructure
-

### 질문 사항

- [ ] 배민 기술 블로그에 보면 domain 에 종속성 하나도 없는데 테스트 하기 위한 종속성이 없어서 테스트가 안된다. 어떻게 하지?
