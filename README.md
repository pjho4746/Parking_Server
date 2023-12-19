
🚗 Turu-Parking
---
## 📍백엔드 팀원 소개
|                               [박지호](https://github.com/pjho4746)                                |                                [이하얀](https://github.com/hayannn)                                |                                 [조예지](https://github.com/CYJhub)                                  |
|:-----------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------:|
|                                       pjho4746@gmail.com                                        |                                      dlgkdis801@naver.com                                       |                                        yejee1018@naver.com                                        | 
|  ![박지호](https://github.com/CYJhub/CYJhub/assets/81136546/42b12c5b-8f9e-4580-984a-fba1ab9ee743)  |  ![이하얀](https://github.com/CYJhub/CYJhub/assets/81136546/bb585674-490c-4460-8dd7-82780017baeb)  |  ![yejee](https://github.com/CYJhub/CYJhub/assets/81136546/5439eca9-5704-4007-a68a-53bf101114ec)  | 


## 📍개요
Turu-Parking은 휴맥스모빌리티의 주차 시설을 손쉽게 찾고 활용할 수 있는 주차장 공유 플랫폼입니다.
서비스는 대중 교통을 이용하거나 차량을 운전하는 사용자들이 주변에 위치한 주차장의 정보를 신속하게 확인하고, 필요한 경우 부가 서비스를 이용할 수 있도록 지원합니다.

---

## 📍주요 기능
| 기능                | 설명                                                            |
|---------------------|-----------------------------------------------------------------|
| 주차장 위치 안내    | Turu-Parking은 사용자가 현재 위치 또는 특정 지역에서 가장 가까운 주차장을 찾을 수 있는 기능을 제공합니다. 지도를 통해 주차장의 위치를 시각적으로 확인할 수 있습니다.   |
| 상세 정보 제공      | 각 주차장에는 주차 요금, 운영 시간 등의 상세 정보가 제공됩니다. 이를 통해 사용자는 필요한 정보를 사전에 파악할 수 있습니다.        |
| 입차 출차 기록 / 이용 내역 및 가격 확인      | 사용자는 Turu-Parking을 통해 주차장 입출차 기록을 쉽게 기록할 수 있습니다. 이를 통해 이용한 주차장에 대한 입차와 출차의 시간을 확인하고, 주차 요금 등을 정확히 파악할 수 있습니다.              |
| 인기도순 주차장 확인 | Turu-Parking은 사용자들의 주변 주차장 검색 기반으로 주차장의 인기도를 확인할 수 있는 기능을 제공합니다. 많은 이용자들이 선택한 주차장이나 자주 이용하는 주차장을 높은 순위로 표시하여 편리하게 선택할 수 있습니다.     |
| 주차장 즐겨찾기      | 사용자는 자주 이용하는 주차장을 즐겨찾기 목록에 추가할 수 있습니다. 이를 통해 향후 더 빠르게 자주 이용하는 주차장을 찾고 예약할 수 있습니다.                  |


## 📍기술 아키텍쳐
<img width="900" alt="KakaoTalk_20231218_192218188" src="https://github.com/humax-sw-team16/Parking_Server/assets/126854628/cd1168b3-036e-4a4f-93dd-e89e75ad7c97">

## 📍사용 기술스택
1. SpringBoot
2. MySQL
3. Redis
3. AWS EC2
4. AWS RDS
5. Docker
6. Nginx
7. GitHub Actions

---

## 📍라이브러리
1. lombok
2. MySQL Connector
3. spring data jpa
4. spring web
5. oauth2 client
6. spring security
7. spring boot test
8. spring security test
9. spring session redis
10. swagger
11. querydsl
12. jjwt

---

## 📍프로젝트 구조
```
└── 🗂 main
    ├── 🗂 java
    │   └── 🗂 com
    │       └── 🗂 humax
    │           └── 🗂 parking
    │               ├── 📑 ParkingApplication.java
    │               ├── 🗂 common
    │               │   └── 🗂 exception 
    │               │       └── 📑 CustomException.java
    │               ├── 🗂 config
    │               │   ├── 📑 CorsConfig.java
    │               │   ├── 📑 JwtFilter.java
    │               │   ├── 📑 LoginSuccessHandler.java
    │               │   ├── 📑 RedisConfig.java
    │               │   ├── 📑 SecurityConfig.java
    │               │   └── 📑 SwaggerConfig.java
    │               ├── 🗂 controller
    │               │   ├── 📑 OauthController.java
    │               │   ├── 📑 ParkingController.java
    │               │   ├── 📑 ParkingMainController.java
    │               │   └── 📑 UserController.java
    │               ├── 🗂 dto
    │               │   ├── 📑 LoginResultDTO.java
    │               │   ├── 📑 ParkingDTO.java
    │               │   ├── 📑 ParkingInfoDTO.java
    │               │   ├── 📑 ParkingUsageDTO.java
    │               │   ├── 📑 SocialUserProfileDTO.java
    │               │   ├── 📑 TimeDTO.java
    │               │   └── 📑 UserLocationDTO.java
    │               ├── 🗂 exception
    │               │   ├── 📑 CustomException.java
    │               │   ├── 📑 ErrorCode.java
    │               │   ├── 📑 ErrorResponse.java
    │               │   ├── 📑 NotFoundException.java
    │               │   └── 📑 ResponseConstant.java
    │               ├── 🗂 model
    │               │   ├── 📑 Bookmark.java
    │               │   ├── 📑 Enter.java
    │               │   ├── 📑 ParkingEntity.java
    │               │   └── 📑 User.java
    │               ├── 🗂 repository
    │               │   ├── 📑 BookmarkRepository.java
    │               │   ├── 📑 EnterRepository.java
    │               │   ├── 📑 OAuthUserRepository.java
    │               │   ├── 📑 ParkingRepository.java
    │               │   └── 📑 UserRepository.java
    │               ├── 🗂 service
    │               │   ├── 🗂 kakao 
    │               │   │   ├── 📑 ExchangeKakaoAccessToken.java
    │               │   │   ├── 📑 ExchangeKakaoAccessTokenImpl.java
    │               │   │   ├── 📑 FetchKakaoUserProfile.java
    │               │   │   ├── 📑 FetchKakaoUserProfileImpl.java
    │               │   │   └── 📑 KakaoLoginService.java
    │               │   ├── 📑 BookmarkService.java
    │               │   ├── 📑 ParkingService.java
    │               └── └── 📑 UserService.java
    └── 🗂 resources
        └── 📑 application.yml      
```
---

## commit message convention
- feat: 새로운 기능 추가
- fix: 버그 수정
- docs: 문서
- style: 포맷팅, 누락된 세미콜론 등
- refactor: 코드 리팩토링
- test: 테스트 관련
- chore: 기타 수정
- build: 빌드 시스템 또는 외부 의존성에 영향을 주는 변경
- remove: 파일을 삭제
