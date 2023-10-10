# 클린 아키텍처(헥사고날 아키텍처) 예제 구현(멀티모듈)

- [우아한 기술 블로그 'Spring Boot Kotlin Multi Module로 구성해보는 헥사고날 아키텍처'](https://techblog.woowahan.com/12720/) 를 참고하였음
- 위 포스팅에서는 모듈을 Bootstrap Hexagon, Application Hexagon, Domain Hexagon, Framework Hexagon 으로 구분하고 있지만
  이 레포지토리의 예제에서는 api, application, domain, infrastructure 으로 모듈을 구분하고 있음(역할은 동일하고 명칭만 다름)
- 위 포스팅에 각 모듈의 패키지 구조가 나와있는데, 이 레포지토리의 예제에서는 패키지 구조를 포스팅과 다르게 구성하고 있음
  [만들면서 배우는 클린 아키텍처](https://www.yes24.com/Product/Goods/105138479?pid=123487&cosemkid=go16373101893711165) 를 주로 참고함
- MapStruct 사용은 [이 포스팅](https://medium.com/naver-cloud-platform/%EA%B8%B0%EC%88%A0-%EC%BB%A8%ED%85%90%EC%B8%A0-%EB%AC%B8%EC%9E%90-%EC%95%8C%EB%A6%BC-%EB%B0%9C%EC%86%A1-%EC%84%9C%EB%B9%84%EC%8A%A4-sens%EC%9D%98-mapstruct-%EC%A0%81%EC%9A%A9%EA%B8%B0-8fd2bc2bc33b)
  을 참고했으며 해당 예제는 간단한 예제만 적용된 코드임

## 각 모듈 역할 설명

### api
- 프로그램의 기능을 사용하기 위한 시작점
- application 에서 정의한 usecase 를 call 하게 된다.
- 멀티모듈에서 실제로 서버로 띄우게 되는 어플리케이션이다.
- domain, application, infrastructure 모두에 대한 의존을 갖는다.
```groovy
dependencies {
    implementation project(':saving-domain')
    implementation project(':saving-application')

    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'javax.persistence:javax.persistence-api:2.2'
    implementation 'mysql:mysql-connector-java:8.0.33'

    implementation 'org.mapstruct:mapstruct:1.5.3.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.3.Final'

    testImplementation 'org.springframework.boot:spring-boot-starter-jdbc'
    runtimeOnly 'com.h2database:h2:1.4.200'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

### application
- port(in, out) 를 구현한 모듈
- 시스템이 가지는 기능/사례(usecase)를 선언 및 구현
- 유일하게 domain 에 대해서만 의존성을 가진다. domain 이 제일 중심부에 위치하고 이를 감싸는 모듈로 의존성이 계속 안쪽으로 향한다는 것을 인지하자.
```groovy
dependencies {
    implementation project(':saving-domain')
    implementation 'org.springframework:spring-context:6.0.12'
}
```
- 엄격하게 application 도 기술에 침투당하지 않아야 하는데 위 우아한 형제들 포스팅에서도 그렇고 '만들면서 배우는 클린 아키텍처'에서도 그렇고
  스프링을 사용한다고 가정하고 usecase의 구현체를 Bean 으로 선언하고 사용한다.
- 이걸 굳이 엄격하게 기술에서 자유롭게 분리하려면 커스텀한 어노테이션을 따로 설정하든가 하고 바깥에서 이 어노테이션을 탐지해서 Bean 으로 등록하게 하든가 해야할 것 같다.(Spring 을 쓴다면)
- 그 외 jvm 프레임워크를 쓴다면 거기에 맞게 구현해야한다. 어떤 식으로든 리플렉션이 사용 될 것이다.
- 결국 근본적인 질문을 해보자. '스프링 말고 딴 거 쓸 가능성이 0.1% 라도 있는가?' 만약 이 질문에 대해서 가능성이 0라고 말하게 된다면 스프링에 의존하는 타협을 해도 괜찮다고 본다. 
  사실 application 이 원칙적으로 기술에 침투 당하지 않는게 맞지만 이것의 근본 목적은 결국 기술 선택의 자유도를 보장하는 것인데 어차피 무조건 스프링 쓸 것이면 이런 타협은 괜찮다고 생각한다.
- 처음에 좀 헷갈렸던 부분인데 port 가 out 이라고 해서 데이터의 흐름의 방향이 무조건 아웃은 아니다. 이건 web -> 시스템 -> infrastructure 의 방향이 out 이니까 out 이라 간주된다.
  실제로 out port 를 통해서 데이터는 시스템으로 로드되기도 한다. 방향만 보면 이건 in 인데 out 이라고 분류하고 있어서 좀 헷갈렸다.
- 헥사고날 아키텍처를 '소리치는 아키텍처'로 말할 수 있는 가장 큰 이유 중 하나가 usecase 가 아닐까 싶다. 레이어드 아키텍처에서는 뚱뚱한(책에서 표현으로는 '넓은') 서비스 로직을 일일이
  봐야 어떤 기능들이 있는지 알 수 있는데, 헥사고날 아키텍처에서는 그냥 패키지 구조만 봐도 어떤 기능들을 수행할 수 있는지 usecase 들을 보면 알 수 있다.
- 나는 일부러 port 들을 CQRS 를 적용해서 어떤 usecase 가 조회성이고 데이터를 변경시키는지도 알 수 있도록 패키지를 구분했다.
- application 모듈에서 확실하게 짚고 넘어갈 것은 application 모듈은 데이터베이스가 어떤 것인지, 외부에서 시스템을 가동하기 위한 기술은 무엇인지 아무것도 모르고 알 필요도 없고 알아서도 안된다는 것이다.

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
- domain, application 에 의존성을 가진다. 계속해서 강조하지만 의존의 방향이 안쪽을 향한다.
```groovy
dependencies {
    implementation project(':saving-domain')
    implementation project(':saving-application')

    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'javax.persistence:javax.persistence-api:2.2'
    implementation 'mysql:mysql-connector-java:8.0.33'

    implementation 'org.mapstruct:mapstruct:1.5.3.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.3.Final'

    testImplementation 'org.springframework.boot:spring-boot-starter-jdbc'
    runtimeOnly 'com.h2database:h2:1.4.200'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```
- 시스템이 사용할 기술을 다루는 모듈이다. 어떠한 비즈니스 규칙도 포함되어선 안된다.
- application 모듈에서 선언한 out port 의 구현체이다
- '우아한 기술 블로그' 에 따르면 infrastructure 는 기술별로 여러 개의 모듈로 쪼개어져 관리 될 수 있다고 한다.
  예를 들어서 fistkim-bank-mysql-infrastructure, fistkim-bank-redis-infrastructure, fistkim-bank-aws-s3-infrastructure 등

### 질문 사항

- [ ] 배민 기술 블로그에 보면 domain 에 종속성 하나도 없는데 테스트 하기 위한 종속성이 없어서 테스트가 안된다. 어떻게 하지?
- [ ] MapStruct 사용 위해서 필요한 필드는 setter 정의를 해줘야하고 일부러 은닉해둔 필드들에 대해서도 전부 Getter 를 제공해서 캡슐화가 약해진다.
  특히 Setter 가 노출되는게 크다. 라이브러리 사용을 위해서 코드 자체가 변경되는 것이 맞는 것인지 잘 모르겠다. 
- [ ] 인프라 모듈 쪼개어 진다는 것이 내가 이해한 저것이 맞는지
- [ ] 클린 아키텍처를 떠나서 모듈 구성을 어떤식으로? 회사에서 내가 했던거랑 지난번 피드백때 받은 외부, 내부, 배치 등. 레포 구성 등 
- [ ] 배민 포스팅에 인프라 모듈에서 '각 Framework Hexagon은 각 기술의 이름을 딴 config class를 포함하며, config class는 그 기술이 사용할 component들을 bean으로 등록할 수 있게 scan 영역을 격리'라고 하는데
  모듈을 분리하면 애초에 그래들에서 임포트 하냐 마냐만으로 제어가 가능한데 굳이 저렇게 해야하는가
- [ ] 인프라 모듈에는 그 어떤 도메인 규칙이 포함이 되지 않아야 하는 것이 맞는가
- [ ] usecase 의 구현체와 엔티티 객체 간에 비즈니스 규칙 분할 비율? 엔티티 내에 비즈니스 규칙 100% 로 최대한 담기게 하는 것이 좋을까
- [ ] 응답 모델은 컨트롤러 가지고 있는 모듈에 선언해주는 것이 맞을까요, 그렇다면 매퍼를 인프라에 하나 api에 하나?
- [ ] 멀티모듈이라서 매퍼가 두 개가 나오게 되는듯
- [ ] 