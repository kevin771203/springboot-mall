電商後端系統（Shopping Backend System）

一個以 Spring Boot 為核心、具備高併發處理與社交登入功能的電商後端專案。支援完整的會員、商品與訂單流程，並考慮系統安全性與測試覆蓋率，模擬真實商業應用場景。

技術架構與核心功能

1.後端框架：

(1)使用 Spring Boot 建構專案，採 標準 MVC 架構（Controller-Service-Repository）

(2)資料庫採用 MySQL
	
2.API 設計：

RESTful API 實作，涵蓋 User、Product、Order 的 CRUD 功能
	
3.ID 生成策略：

使用 Hutool 提供的 Snowflake 演算法 定義全域唯一的 ID 規則

4.訂單高併發處理：

結合 Redis 快取機制 控制商品庫存與選購量，避免超賣與提升效能

5.認證與授權：

(1)整合 Spring Security 實作角色分級（Admin / Normal）

(2)支援 OAuth 2.0 第三方社交登入（Google、Facebook、GitHub、LINE）

(3)透過 Filter 紀錄使用者登入行為，增加監控與可追蹤性

6.系統安全性：

實作 CORS 跨域請求設定 與 CSRF 攻擊防禦

7.測試覆蓋：

使用 BDD（Behavior-Driven Development）風格 撰寫 單元測試與整合測試
	
8.CI/CD：

使用 GitHub Actions 自動化流程實作 CI/CD

