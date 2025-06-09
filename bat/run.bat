@echo off
echo Compilando...
javac -cp "lib/*" -d out src\sistemacadastroacademia\model\*.java src\sistemacadastroacademia\controller\*.java src\sistemacadastroacademia\view\*.java src\sistemacadastroacademia\util\*.java src\sistemacadastroacademia\main\*.java

if %errorlevel% neq 0 (
    echo Erro na compilação. Corrija os erros acima.
    pause
    exit /b
)

echo Iniciando o sistema...
java -cp "lib/*;out" sistemacadastroacademia.main.App
pause
