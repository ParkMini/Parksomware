### Java로 만든 랜섬웨어, Parksomware
![](https://velog.velcdn.com/images/parkmini/post/e3e6bd5c-34af-463d-9073-c598f8d2353a/image.png)
[자세한 프로젝트 소개 글은 여기를 클릭해주세요. (Velog)](https://velog.io/@parkmini/Java%EB%A1%9C-%EB%A7%8C%EB%93%A0-%EB%9E%9C%EC%84%AC%EC%9B%A8%EC%96%B4-Parksomware)
#### 프로젝트 소개

Parksomware는 교육적 목적으로 만들어진 랜섬웨어 프로젝트입니다.
이 프로젝트는 랜섬웨어의 작동 원리를 이해하고, 보안 연구 및 교육에 활용하기 위해 개발되었습니다.

**중요**: 이 프로젝트는 실제 환경에서의 사용을 위한 것이 아니며, 랜섬웨어의 피해를 줄이고 이해를 돕기 위한 교육적 목적으로만 사용해야 합니다.

#### 기능 개요

- **암호화 (`Encrypt.java`)**: 파일을 AES 알고리즘을 사용하여 암호화합니다.
- **복호화 (`Decrypt.java`)**: 암호화된 파일을 복호화합니다.
- **시스템 기능 차단 (`ServiceBlock.java`)**: 시스템의 핵심 기능을 차단합니다.
- **바탕화면 변경 (`WallpaperChanger.java`)**: 사용자의 바탕화면을 변경합니다.
- **README 파일 생성 (`CreateReadme.java`)**: 감염 알림을 위한 README 파일을 생성합니다.
- **시스템 재시작 (`SystemReboot.java`)**: 시스템을 재시작하여 변경사항을 적용합니다.

#### 설치 및 실행 방법

1. **Git Repository 클론**:
   ```
   git clone https://github.com/ParkMini/Parksomware
   ```
2. **프로젝트 디렉토리로 이동**:
   ```
   cd Parksomware
   ```
3. **소스 코드 컴파일**:
  - Gradlew를 사용하여 컴파일 합니다.
4. **프로그램 실행**:
  - Java 런타임 환경을 사용하여 프로그램을 실행합니다.
  - 이 프로젝트는 Java 17 환경에서 개발되었습니다.

#### 사용 주의사항

- 이 프로젝트는 교육 및 연구 목적으로만 사용해야 합니다.
- 실제 환경에서의 사용은 법적 책임과 심각한 결과를 초래할 수 있습니다.
- 모든 사용은 안전한 환경(예: 가상 머신)에서 수행해야 합니다.

#### 저자 및 기여자

- [ParkMini](https://github.com/parkmini)
- 이메일: admin@pah.kr

---

**주의**: 이 프로젝트는 실제 랜섬웨어 배포를 위한 것이 아님을 다시 한번 강조합니다.