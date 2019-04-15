# MySQL Query

- **Selection 행동을 특정한 조건을 주어서 제한함**

  `SELECT * FROM s_emp WHERE dept_id=110;`

* **Projection 특정한 컬럼만 지정해서 나열함**

  `SELECT name, title, salary FROM s_emp;`

* **직책이 과장이고 salary가 2500이 넘는 사원의 이름, 월급, 직책**

  `SELECT name, salary, title FROM s_emp WHERE title='과장' AND salary > 2000;`

* **사원중에 가장 급여를 낮게 받는 사원 5명의 이름과 직책, 월급을 출력**

  `select name, title, salary from s_emp order by salary limit 0,5;`
  ==> **order by뒤에 올 수 있음, limit의 시작점은 0부터**

* **DISTINCT 중복을 제거, select구문 바로 다음에 사용**

  `select DISTINCT dept_id from s_emp;` > `select DISTINCT title from s_emp;`

* **모든 사원의 연봉을 출력, NULL은 아무 값도 없는 것이 아니고 0을 의미하는 것도 아님**

  `select name, salary, salary * 12+commission_pct from s_emp;` -- x

* **ifnull(comission_pct, 0)을 사용한다**

  `select name, salary, salary * 12+ifnull(commission_pct,0) from s_emp;`

* **Alias 별칭주기**

  ```
  select name, salary, salary * 12+ifnull(commission_pct,0) as AnnualSalary from s_emp;
  select name, salary, salary * 12+ifnull(commission_pct,0) AnnualSalary from s_emp;
  select name, salary, salary\*12+ifnull(commission_pct,0) '일년 연봉' from s_emp;
  ```

* **Alias로 ''나 ""로 묶은 경우 정렬 안됨...**

  `select name, salary, salary\*12+ifnull(commission_pct,0) 일년연봉 from s_emp order by 일년연봉 asc;`

<hr/>

- **상사번호가 제일 높은 순으로**

  `select * from s_emp order by manager_id;`

- **상사가 없는 사원의 이름 검색**

  `select name from s_emp where manager_id is null;`

* **논리 연산자 사용**

  `SELECT name, title, salary FROM s_emp WHERE (title="영업대표이사" OR title="사장") AND salary > 3000;`

* **IN연산자**

  - **부서가 110, 111, 112인 부서인 사원의 이름과 직책, 부서번호 출력**
    ```
    select name, title, dept_id from s_emp where dept_id IN(110, 111, 112);
    select name, title, dept_id from s_emp where dept_id NOT IN(110, 111, 112);
    ```

* **LIKE 연산자(특정한 이름이 들어간 데이터를 검색 : 와일드카드)**

  - **사원의 이름이 '철'자로 끝나는 사원의 이름을 검색**

    `select name from s*emp where name like '%철';`

  - **사원의 이름 중에 심자가 가운데 이름으로 들어간 사원의 이름을 출력**

    `select name from s_emp where name like '*심%';`

* **상사가 없는 사원의 manager_id의 컬럼값을 '없음'으로 치환하고 해당 alias를 CEO로 지정하세요.**
  **사원의 이름과 직책, 상사번호가 출력되도록 하세요.**

  `SELECT name, title, ifnull(convert(manager_id,char), '없음') CEO FROM s_emp;`

* **맨 앞 글자의 아스키 값을 출력**

  `SELECT ASCII(name) FROM s_emp;`

* **단어 대체하기**

  ```
  SELECT CONCAT(name, '의 직책은 ', title) FROM s_emp;

  SELECT INSERT(title, 3, 4, '직') FROM s_emp WHERE title='영업대표이사';

  SELECT REPLACE(title, '대표이사', '직') FROM s_emp WHERE title='영업대표이사';

  SELECT INSTR(title, '영') FROM s_emp WHERE title='영업대표이사';
  ```

<hr/>

- **사원의 입사년도만 출력 ----LEFT**

  `SELECT LEFT(start_date, 4) FROM s_emp;`

* **사원의 이름만 출력(성은 빼고) ----RIGHT**

  `SELECT RIGHT(name, 2) FROM s_emp;`

* **사원의 title의 값이 앞에서 2글자만 출력되도록 ----SUBSTRING**

  `SELECT SUBSTR(title, 1, 2) FROM s_emp;`

* **빈 공간 제거**

  ```
  SELECT LTRIM(' James Gosling is Good ') Good;
  SELECT RTRIM(' James Gosling is Good ') Good;
  SELECT TRIM(' James Gosling is Good ') Good;
  ```

* **가운데 공백 없애기**

  `SELECT REPLACE(' James Gosling is Good ', ' ', '') Good;`

<hr/>

#### 1. title이 '부장'으로 끝나는 사원의 이름과 title을 출력

```
SELECT name, title from s_emp WHERE RIGHT(title, 2) = '부장';
SELECT name, title from s_emp WHERE SUBSTR(title, 3, 2) = '부장';
```

#### 2. 이름이 '철'자로 끝나는 사원의 이름, 메일아이디, 입사일 출력 3가지 방법

**1)SUBSTR 2)LIKE 3)INSTR**

```
select name, mailid, start_date from s_emp WHERE SUBSTR(name, -1, 1) = '철';
select name, mailid, start_date from s_emp WHERE name like '%철';
select name, mailid, start_date from s_emp WHERE INSTR(name, '철') = 3;
```
