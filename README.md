# Eventify

Eventify, kullanıcıların etkinlik oluşturmasına, yönetmesine ve diğer kullanıcıları takip etmesine olanak tanıyan bir Spring Boot tabanlı web uygulamasıdır.

## ✨ Özellikler

- **Kullanıcı Yönetimi:** Kayıt olma, profil güncelleme ve kullanıcı bilgilerini görüntüleme.
- **Etkinlik Yönetimi:** Yeni etkinlikler oluşturma, mevcut etkinlikleri güncelleme ve listeleme.
- **Kategori Sistemi:** Etkinlikleri daha iyi organize etmek için kategoriler oluşturma ve yönetme.
- **Sosyal Etkileşim:** Kullanıcıların birbirini takip etme ve takipçi/takip edilen istatistiklerini görme.
- **RESTful API:** Tüm bu özelliklere erişim sağlayan temiz ve anlaşılır API uç noktaları.

## 🛠️ Kullanılan Teknolojiler

- **Java:** Ana programlama dili.
- **Spring Boot:** Uygulamanın temel çatısı.
- **Spring Data JPA:** Veritabanı işlemleri için.
- **Maven:** Proje yönetimi ve bağımlılıklar için.
- **H2 / PostgreSQL (veya başka bir ilişkisel veritabanı):** Veri depolama için (`application.properties` dosyasında yapılandırılır).

## 🚀 Projeyi Çalıştırma

Projeyi yerel makinenizde çalıştırmak için aşağıdaki adımları izleyin:

1.  **Depoyu Klonlayın:**
    ```bash
    git clone https://github.com/kullanici-adiniz/Eventify.git
    cd Eventify
    ```

2.  **Veritabanını Yapılandırın:**
    `src/main/resources/application.properties` dosyasını açarak kendi veritabanı bilgilerinizi (URL, kullanıcı adı, şifre) girin.

3.  **Projeyi Derleyin ve Çalıştırın:**
    Maven kullanarak uygulamayı başlatın:
    ```bash
    mvn spring-boot:run
    ```

4.  Uygulama varsayılan olarak `http://localhost:8084` adresinde çalışmaya başlayacaktır.

## API Uç Noktaları (Örnekler)

- `GET /api/users`: Tüm kullanıcıları listeler.
- `POST /api/users/register`: Yeni bir kullanıcı kaydı oluşturur.
- `GET /api/events`: Tüm etkinlikleri listeler.
- `POST /api/events`: Yeni bir etkinlik oluşturur.
- `POST /api/follow/{userId}`: Belirtilen kullanıcıyı takip eder.

