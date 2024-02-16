Git 커밋 메시지 컨벤션에는 다양한 종류가 있지만, 일반적으로 다음과 같이 작성합니다.

```shell
<type>: <subject>

<body>

<footer>
```

```
여기서 <type>, <subject>, <body>, <footer>는 각각 다음과 같은 내용을 담습니다.

<type>: 커밋의 종류를 나타내는 단어로, 다음 중 하나를 사용합니다.

feat: 새로운 기능 추가
fix: 버그 수정
docs: 문서 수정
style: 코드 스타일 변경 (포맷 변경 등)
refactor: 코드 리팩토링 (기능 변경 없음)
test: 테스트 코드 작성 또는 수정
chore: 빌드 프로세스나 도구, 라이브러리 등의 변경
<subject>: 커밋의 간단한 요약을 나타내는 문장입니다. 50자 이내로 작성하며, 첫 글자는 대문자로 씁니다.

<body>: 커밋의 자세한 내용을 나타내는 문장입니다. 필요한 경우 작성합니다.

<footer>: 커밋과 관련된 이슈 번호 등을 나타내는 문장입니다. 필요한 경우 작성합니다.
```
```shell
fix: Change port number from 8088 to 10000

Change the port number used by the application from 8088 to 10000.
This change was necessary to avoid a conflict with another service running on the system.

Related to issue #123
```
이와 같이 커밋 메시지를 작성하면, 커밋의 내용과 의도를 명확하게 전달할 수 있습니다.