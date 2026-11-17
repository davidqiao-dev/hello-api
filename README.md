# hello-api

一个用于学习 Spring Boot 的练习项目：RESTful 员工管理接口，覆盖实体建模、关联关系、DTO、校验、异常处理、分页与查询。

## 技术栈

| 项 | 版本 / 说明 |
|---|---|
| Spring Boot | 4.1.x |
| Spring Framework | 7.0.x |
| Java | 17+ |
| 数据库 | MySQL，库名 `company_db` |
| JDBC 驱动 | `com.mysql:mysql-connector-j:9.7.0` |
| 构建 | Maven Wrapper（Windows PowerShell 用 `.\mvnw.cmd`） |
| Group / 包名 | `com.qiao` / `com.qiao.hello` |

## 快速开始

1. 创建数据库：

   ```sql
   CREATE DATABASE company_db CHARACTER SET utf8mb4;
   ```

2. 在 `application.yml` 中配置数据源：

   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/company_db?useSSL=false&serverTimezone=Asia/Shanghai
       username: root
       password: your_password
     jpa:
       hibernate:
         ddl-auto: none      # schema 以数据库为准，启动不自动建表
       show-sql: true
   ```

3. 建表（示例）：

   ```sql
   CREATE TABLE department (
     id   BIGINT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(100) NOT NULL UNIQUE
   );

   CREATE TABLE employee (
     id            BIGINT PRIMARY KEY AUTO_INCREMENT,
     name          VARCHAR(100) NOT NULL,
     email         VARCHAR(150) NOT NULL UNIQUE,
     department_id BIGINT NOT NULL,
     CONSTRAINT fk_emp_dept FOREIGN KEY (department_id) REFERENCES department(id)
   );
   ```

4. 启动：

   ```
   .\mvnw.cmd spring-boot:run
   ```

   服务默认监听 `http://localhost:8080`。

## 项目结构

```
com.qiao.hello
├── HelloApiApplication.java
├── entity/          # JPA 实体：Employee、Department
├── dto/             # 入站请求 DTO + 出站响应 DTO
├── repository/      # Spring Data JPA 接口
├── service/         # 业务逻辑、事务、DTO 转换
├── controller/      # REST 接口
└── exception/       # 自定义异常 + 全局异常处理器
```

## 设计要点

### 分层职责

- **Controller**：收参、`@Valid` 校验、返回合适的状态码，不写业务。
- **Service**：业务规则、事务边界、实体 ↔ DTO 转换。
- **Repository**：只做数据访问，用派生查询或 JPQL。
- **DTO**：隔离实体。入站用 `XxxRequest`（带校验注解），出站用 `XxxResponse` / `XxxDto`（无校验、由 `from(Entity)` 生成）。

### 实体关联

`Employee` 与 `Department` 是多对一：

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "department_id")
private Department department;
```

- `@JoinColumn` 所在方 = **owning side**，由它维护外键。
- 单值关联默认 **EAGER**，会引发 N+1，实战一律显式写 `LAZY`。
- 返回 DTO、事务内完成转换，可避免 `LazyInitializationException`。
- 派生查询配 `@EntityGraph(attributePaths = "department")` 预抓取关联，消除 N+1。

### 校验分层

| 位置 | 负责 |
|---|---|
| Controller + 入站 DTO | 格式校验（`@NotBlank`、`@Email`、`@NotNull`），`@Valid` 触发 |
| Service | 唯一性、关联是否存在、跨字段、权限（需查库） |
| 数据库 | `UNIQUE` / `NOT NULL` / `FOREIGN KEY` 兜底，并发下唯一可靠 |

`@Email` 不拦截 `null` 和空串，需叠加 `@NotBlank`。

### 分页

- 用 `Pageable` 接收，`page` **从 0 开始**（前端按 1 起传时要 `page - 1`）。
- 必须带 `Sort`，否则翻页顺序不稳定。
- 返回 `Page<T>`（含总数，多一次 count）或 `Slice<T>`（不含总数，省一次 count）。
- 用 `Page.map(EmployeeDto::from)` 转换，保留分页元信息。

## 主要接口

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/api/employees` | 分页列表，支持 `page`、`size`、`sort` |
| GET | `/api/employees/{id}` | 按 ID 查询 |
| GET | `/api/employees/search?name=` | 按姓名模糊搜索 |
| POST | `/api/employees` | 新增员工 |
| PUT | `/api/employees/{id}` | 更新员工 |
| DELETE | `/api/employees/{id}` | 删除员工 |
| PUT | `/api/employees/{id}/department/{deptId}` | 调整所属部门 |

### 示例

```bash
# 分页查询
curl.exe -i "http://localhost:8080/api/employees?page=0&size=10&sort=id,desc"

# 新增
curl.exe -i -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"张三\",\"email\":\"zhangsan@example.com\",\"departmentId\":1}"
```

## 状态码约定

| 场景 | 状态码 |
|---|---|
| 查询成功 | 200 |
| 新增成功 | 201 (+ `Location` 头) |
| 删除成功 | 204 |
| 参数校验失败 | 400 |
| 资源不存在 | 404 |
| 业务冲突（唯一键、重复分配等） | 409 |

统一由 `@RestControllerAdvice` 处理，返回带 `code` / `message` 的错误结构。

## 学习笔记（关键概念备忘）

- **owning side**：有 `@JoinColumn` 的一方拥有关系；反方用 `mappedBy`（值是对方 Java 字段名）只读。
- **默认 fetch**：单值 EAGER、集合 LAZY；`@ManyToOne` EAGER 是 N+1 头号来源。
- **LazyInitializationException**：事务结束 Session 关闭后再读懒加载代理。首选在事务内转 DTO。
- **双向 JSON 递归**：反方加 `@JsonIgnore` 切断回路；返回 DTO 则不会触发。
- **JPQL 关联查询**：`JOIN e.department d` 基于对象关联，取代旧的字符串列过滤。
- **唯一性并发保证**：应用层 `exists` 只做提示，数据库 `UNIQUE` 才是最终保障；`saveAndFlush` 才能当场捕获 `DataIntegrityViolationException`。
- **实例调静态方法**：用 `.` 能编译但有警告，正解是 `类名.方法名`；`::` 是方法引用。

## 备注

- `ddl-auto: none` 时 schema 以数据库为准，列名对不上启动不报错，查询时才抛 `Unknown column`。
- PowerShell 里 `curl` 是 `Invoke-WebRequest` 别名，复杂请求或要看状态码请用 `curl.exe -i`。
