## 고문서 검색기
* #### ppt, doc, docx 등의 자료를 한 눈에 알아보기 위한 검색기
* #### 제목 및 내용을 검색하여 원하는 파일 찾기 기능
* #### 카테고리 선택 및 검색을 통하여 원하는 파일 다운로드 기능

### Process
* #### 파일 업로드 시 DB에 제목, 확장자, s3의 파일 path를 저장
* #### 실제 데이터는 aws s3에 저장

* #### 뷰는 DB에 해당하는 값만 보여줌
* #### 파일 열기 및 검색 시 DB의 값으로 s3에 요청하여 실제 데이터를 가져옴 

#### hosting & DB - cafe24
#### cloud storage - aws s3

<p align="center">
  <img src="https://user-images.githubusercontent.com/77053445/235095561-cc9d5ada-3486-4d7d-817a-7d7fc3ec173d.png">
</p>
