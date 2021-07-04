# Huawei Coding Marathon 
## Proje Özeti: 
  * Kullanılan HMS kitleri
    * LocationKit:5.1.0.301 ( Hibrit konumlama ve geofence özelliği)
    * Maps Kit:5.0.1.300
  * Uygulama amacı 
    * Uygulamanın amacı kullanıcının isteği doğrultusunda coğrafi çitler oluşturarak bunları cihazın local database'inde saklamak ve kullanıcı tekrar o noktaya geri girdiğinde bu coğrafi çitin tetiklenerek kullanıcıya bildirim göndermesini sağlamak.
    * Günlük hayatta çözdüğü sorun ise eğer çok unutkan birisiyseniz size mekanlarla ile ilgili hatırlatıcı eklemenizde yardımcı olacaktır. Mesela Şehirler arası otobüs terminaline gittiğimde para çek, ya da bu tarafatan bir daha geçersem şuraya uğramayı unutma gibi bildirimleri zamanı ve yeri gelince hatırlatacaktır.
## Projeyi indirmek
  * Sağ üst köşede bulunan clone yazısının altından isterseniz git clone ile ya da isterseniz zip dosyasını indirerek projeyi indirebilirsiniz
  * Cihaza yükleme yapmak isterseniz app klasörü altında bulunan release klasöründe bir apk mevcuttur.
## Projeyi Çalıştırmak
  Projeyi çalıştırmadan önce proje içinden bulunan bir kütüphadenen ötürü Android Studioya eklemeniz gereken bir plugin mevcuttur eğer bu plugin'i indirmezseniz compile error alma ihtimaliniz oldukça yüksektir bu yüzden Android studio için *Lombok* pluginini indirmeniz gerekir. Lombok Pluginini Android Studio Plugin marketplacete bulabilirsiniz.
## Yararlanılan kütüphaneler
  * Lombok:1.18.20 -> Lombok aslında hepimizin yazmaktan artık çok sıkıldığı getter, setter, toString vs gibi methodları otomatikmen oluşturmak için yazılmış bir library, Bu getter ve setterları compile zamanında class a eklediği için Java class'ımız IDE de oldukça sade görünüyor, Kısacası Boilerplate kodunu iş mantığından ayırmaya yarayan güzel bir kütüphane
  *  RoomDB:2.3.0 -> RoomDB Android cihazlarda varolan SQLite üzerine bir encapsulation getiriyor, Aslında kendisi bir nevi ORM, Fazla SQL yazmadan iş yapmamızı ve Uygulamamızda bulunan data classlarımızı kolaylıkla SQL'e kaydetmemize olanak sağlıyor MVVM mimarisini destekliyor ve seperations of Concerns kuralına dikkat etmemizi sağlıyor.
