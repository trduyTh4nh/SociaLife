<div align='center'>
     <img src='https://github.com/trduyTh4nh/SociaLife/assets/68984861/9d20d1e5-afa2-47b6-848b-a42f88718677' height=512/>
     <br/>
     Đồ án ứng dụng MXH
     <br/>
     <hr>
     <img src='https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white'/>
     <img src='https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white'/>
     <img src='https://img.shields.io/badge/sqlite-%2307405e.svg?style=for-the-badge&logo=sqlite&logoColor=white'/>
     <hr>
</div>

# Cách build và cài đặt
#### Lưu ý khi chạy Ứng dụng trên phiên bản Android quá cũ (Android 7.1 trở về trước):
- Hình ảnh sẽ không được hiển thị đúng cách.
- SQLite có thể ngừng hoạt động.
- SharedPreferences có thể sẽ bị "null".
- **Cho nên:** Hãy chạy ứng dụng trên Android mới hơn (8.0+)
## Cài đặt
- Vào releases và lấy apk về.
## Build
### Sử dụng Terminal
(là cmd đó)\
Cần: [Git](https://git-scm.com), [Android Studio](https://developer.android.com/studio), Android SDK, máy ảo Android và Android Platform Tools (https://developer.android.com/tools/releases/platform-tools). \
Khuyên có: Một điện thoại Android.\
(tất nhiên phải clone project và cd vào rồi)
```
#clone project về
$ git clone https://github.com/trduyTh4nh/SociaLife

#cd vào project
$ cd SociaLife

#build file APK
$gradlew assembleDebug

#build file APK rồi chạy trên máy ảo hoặc thiết bị android đã kết nối (cần adb)
gradlew installDebug
```

### Sử dụng Android Studio
Cần: [Git](https://git-scm.com), [Android Studio](https://developer.android.com/studio) và máy ảo Android.\
Khuyên có: Một điện thoại Android.\
### B1: Clone project về
- Có 2 cách:
  - Cách thứ nhất là tải zip về ở repo.
  - Cách thứ hai là clone về bằng lệnh:
```
$ git clone https://github.com/trduyTh4nh/SociaLife
```
### B2: Mở Android Studio
### B3: Ở project mà bạn đã tải về
- Nếu bạn clone về bằng Terminal thì project nó nằm ở (C:\Users\[tên user của bạn]\SociaLife trên Windows) hoặc (~/SociaLife trên macOS (hoặc /User/[tên người dùng]/SociaLife hoặc Linux /home/[tên người dùng]/SociaLife).
- Còn nếu tải zip về thì tự tìm 🙂.
### B4: Chạy project
- Bấm nút chạy ở android studio (i know you're not that stupid).
# Credits
Xin chân thành cảm ơn dến các Dev đã giúp tạo nên dự án này.
1. Trần Duy Thanh
2. Tiêu Trí Quang
3. Hồ Gia Đường.

Cũng như xin chân thành cảm ơn giảng viên:

- Cô Võ Thị Hồng Tuyết

vì đã hướng dẫn tụi em.
### Best wishes from Quang.
```
     =/\                 /\=
     / \'._   (\_/)   _.'/ \
    / .''._'--(o.o)--'_.''. \
   /.' _/ |`'=/ " \='`| \_ `.\
  /` .' `\;-,'\___/',-;/` '. '\
 /.-'       `\(-V-)/`       `-.\
 `            "   "            `
 ```
 Group 22, signing off.
# Tổng hợp mấy con điểm chiến báo:
1. Báo cáo lần 1: 10
2. Báo cáo lần 2: 9.5
3. Báo cáo CK: 9
