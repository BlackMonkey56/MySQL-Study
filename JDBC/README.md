# JDBC

## 1. 데이터베이스 객체

    VIEW, TABLE, INDEX SYNONYM(데이터베이스 객체에 대한 별칭을 부여한 객체)

## 2. JDBC 4단계 과정

    1. Driver Loading...
    2. DB서버와 연결
    3. PreparedStatement 생성
    4. Query문을 실행

```
Connection conn = DriverManager.getConnection(`Driver경로`);
PreparedStatement ps = conn.prepareStatement(`쿼리문`);

        ps.executeUpdate(); => int를 반환, 변경한 행의 갯수 (DML에 대한 처리결과)
        ps.executeQuery(); => ResultSet을 반환, 쿼리 결과로서의 행들을 반환
```

**Tip)** Java Home의 **jre \ lib \ ext**에 *jar*파일을 넣으면 자동으로 인식함!
