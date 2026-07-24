@echo off
chcp 65001 > nul
echo =================================================================
echo     BIÊN DỊCH VÀ KHỞI CHẠY PHẦN MỀM QUẢN LÝ SINH VIÊN (CODEMAP)
echo =================================================================
if not exist "bin" mkdir bin
echo Dang bien dich ma nguon Java...
javac -encoding UTF-8 -d bin src/com/studentmanagement/model/*.java src/com/studentmanagement/exception/*.java src/com/studentmanagement/util/*.java src/com/studentmanagement/repository/*.java src/com/studentmanagement/service/*.java src/com/studentmanagement/view/*.java src/com/studentmanagement/Main.java

if %ERRORLEVEL% NEQ 0 (
    echo [LOI] Bien dich that bai! Vui long kiem tra lai ma nguon.
    pause
    exit /b %ERRORLEVEL%
)

echo Bien dich thanh cong! Dang khoi chay ung dung...
java -cp bin com.studentmanagement.Main
pause
