# 🌙 Project Name : Somnium

## 📌 설명  
**Somnium**은 사용자가 꾼 **꿈의 내용을 글로 작성하면**, Open API인 **DALL·E 모델**을 통해 그 꿈에 어울리는 이미지를 자동 생성해주는 **꿈 일기 앱**입니다.  
생성된 이미지를 배경으로 일기를 작성하며, 자신만의 몽환적인 꿈 일기장을 만들어 갈 수 있습니다.

---

## ✨ 주요 기능

### 🎨 AI 이미지 생성 (DALL·E)
- 사용자가 입력한 꿈 내용을 바탕으로 OpenAI의 **DALL·E 모델을 통해 이미지 생성**
- 생성된 이미지를 기반으로 꿈 일기를 작성할 수 있습니다.
  
### 📖 꿈 일기 작성/수정/삭제 기능  
- **텍스트 + 이미지 조합**으로 일기 작성  
- 작성된 꿈 일기는 목록에서 확인 및 편집 가능  

### 📑 꿈 일기 리스트 및 상세 보기
- RecyclerView를 통해 **꿈 일기 목록 표시**
- 각 일기를 클릭하면 **상세 보기 화면으로 이동**, 이미지와 내용을 확인할 수 있습니다.
- Glide 라이브러리를 이용한 **이미지 로딩 최적화**

---

## 🛠️ 기술 스택

- **언어 (Languages)**: Kotlin, XML  
- **개발 환경**: Android Studio  
- **라이브러리 (Libraries)**:  
  - **Room** - 꿈 일기 로컬 DB 저장  
  - **Retrofit** - Open API (DALL·E) 연동  
  - **Glide** - 이미지 로딩 최적화  
  - **Coroutines** - 비동기 처리 (IO → Main 스레드 전환 등)  
  - **RecyclerView** - 꿈 일기 리스트 구현  

---

## 🔄 앱 구조 및 흐름

```text
[입력] 꿈 내용 입력
   ↓
[API 요청] → DALL·E로 이미지 생성 요청 (Retrofit + 비동기 처리)
   ↓
[결과] 생성된 이미지 수신 후 Glide로 이미지 표시
   ↓
[저장] Room을 통해 이미지 URL + 텍스트 저장
   ↓
[목록] RecyclerView로 꿈 일기 리스트 표시
   ↓
[열람] 항목 클릭 시 ReadDiaryActivity로 이동하여 상세 정보 표시
```

---

## 🔍 핵심 기술 설명

### 📌 Room
- **로컬 데이터 저장** 을 위해 사용
- 일기 데이터 (제목, 내용, 이미지 URL, 날짜 등) 를 Entity로 구성
- IO 스레드에서 처리하여 UI 지연 방지

### 📌 Retrofit
- OpenAI DALL·E API 연동을 위해 사용
- 이미지 요청 → 응답까지 **비동기 처리**로 원활하게 진행
- 싱글톤 패턴으로 API 인스턴스 관리

### 📌 Glide
- DALL·E로부터 받은 이미지 URL을 **효율적으로 로딩**

### 📌 Coroutine
- `lifecycleScope.launch(Dispatchers.IO)`를 통해 Room과 Retrofit 작업 처리
- `withContext(Dispatchers.Main)`을 이용해 UI 업데이트

---

## ❗ 아쉬운 점
- DALL·E API 사용 시 **한글로 작성한 프롬프트는 이미지가 정확하게 생성되지 않는 경우가 많았고**,
영어로 작성하더라도 **기대한 이미지와 다르게 생성되는 경우**가 있어 사용자 만족도가 다소 떨어질 수 있었습니다.
- 향후에는 이미지 품질과 정확도를 높이기 위해 다른 이미지 생성 모델(예: SDXL, Gemini Vision 등)을 고려하거나,
프롬프트 전처리 및 번역 최적화를 적용해볼 수 있을 것 같습니다.


<br> <br> <br>
## 실행 화면 (Screenshots & GIFs)

<h3>메인 화면</h3>
img src="screenshot/home.png" width="220"/>


<h3>이미지 생성 기능</h3>

<table>
  <tr>
    <td><img src="screenshot/makeimg.png" width="220"/></td>
    <td><img src="screenshot/makeimg2.png" width="220"/></td>
    <td><img src="screenshot/makeimg3.png" width="220"/></td>
  </tr>
</table>

img src="screenshot/home.png" width="220"/>


<br>
<h3>⏳ 타이머 실행 예시 기본 모드(25분 공부 + 5분 휴식), 커스텀 모드</h3>

<table>
  <tr>
    <th>기본 모드</th>
    <th>커스텀 모드</th>
  </tr>
  <tr>
    <td><img src="screenshot/gifbasic.gif" width="225"/></td>
    <td><img src="screenshot/gifcustom.gif" width="225"/></td>
  </tr>
</table>
※ 실행 흐름을 확인할 수 있도록, 실제 앱 실행 장면을 녹화하고 필요 없는 부분을 잘라내어 GIF로 편집했습니다. <br>실제 사용 흐름을 간단하게 보여주기 위한 참고 영상입니다.
<br> <br>

### 📊 공부 통계 화면
<img src="screenshot/statistics.gif" alt="App Demo" width="250">



