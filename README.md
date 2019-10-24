<h1 align="center">국방통합보안체계 DISS</h1>
<p align="center">
  <a href="https://github.com/kefranabg/readme-md-generator/blob/master/LICENSE">
    <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-yellow.svg" target="_blank" />
  </a>
  <a href="https://github.com/frinyvonnick/gitmoji-changelog">
    <img src="https://img.shields.io/badge/changelog-gitmoji-brightgreen.svg" alt="gitmoji-changelog">
  </a>
  
</p>

> Defense Integrated Security System (MDM & AC System)<br /> 국방통합보안체계는 튼튼한 안보와 이용자의 편의성을 제공합니다.

# ✨ 앱 미리보기

<p align="center">
  <img width="400" align="center" src="https://user-images.githubusercontent.com/19756026/67467297-78d6c300-f683-11e9-806f-854e1b7be899.gif" alt="demo"/>
</p>

# 🚀 프로젝트 내용
## 주요기능

```sh
1. 비콘 활용 특정 구역 내 모바일 단말기 기능통제
2. NFC 모바일 전자공무원증 출입통제
3. 모바일 병사 출타관리
```

## 컴퓨터 구성 / 필수 조건 안내 (Prerequisites)
- Android 5.0 이상을 구동하고 있는 스마트폰이라면 설치하여 사용할 수 있습니다.
## 설치 안내 (Installation Process)
- apk파일을 통한 설치

## 사용법 (Getting Started)
```sh
APK 설치 후 앱 실행
사용자 등록 후 사용
NFC 기능 켠 후 출입통제체계 태그
비콘 영역 진입시 자동 기능차단
```

## 파일 정보 및 목록 (File Manifest)
- Controller Class 
```sh
ACUFragment.java
AuthCompleteActivity.java
AuthingActivity.java
BackgroundService.java
CallListFragment.java
CallStateReceiver.java
FirstAuthActivity.java
IDCardFragment.java
MainActivity.java
MyAdapter.java
PinAuthActivity.java
SampleData.java
SettingFragment.java
SplashActivity.java
```
- 전화 관련 라이브러리
```sh
ITelephony.aidl
ITelephony.java
```
- 레이아웃 파일
```sh
activity_auth_complete.xml
activity_authing.xml
activity_first_auth.xml
activity_main.xml
activity_pin_auth.xml
activity_splash.xml
fragment_acu.xml
fragment_call_list.xml
fragment_idcard.xml
fragment_setting.xml
item_listview.xml
```

## 저작권 및 사용권 정보 (Copyright / End User License)
```sh
MIT Licence
```
<a href="https://github.com/kefranabg/readme-md-generator/blob/master/LICENSE">
    <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-yellow.svg" target="_blank" />
</a>

## 배포자 및 개발자의 연락처 정보 (Contact Information)
신병륜 nj298@kumoh.ac.kr

## 알려진 버그 (Known Issues)
- 안드로이드 EMM API, 삼성 Knox SDK등 MDM관련 도구들은 인증된 조직에서 사용가능하여 완벽 차단이 불가(issue.#10001)

## 문제 발생에 대한 해결책 (Troubleshooting)
- 추후 인증을 통해 SDK 도입 예정(issue.#10001)

## 업데이트 정보 (Change Log)
- 1.0.0

# 🤝 팀원 소개
### 소.헌.
공군 헌병 병장 이동형
<br/>
육군 소프트웨어개발병 일병 신병륜
