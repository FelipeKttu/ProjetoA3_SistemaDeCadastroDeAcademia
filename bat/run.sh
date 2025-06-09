#!/bin/bash
echo "Compilando os arquivos Java..."
javac -cp "lib/*" -d out src/sistemacadastroacademia/model/*.java src/sistemacadastroacademia/controller/*.java src/sistemacadastroacademia/view/*.java src/sistemacadastroacademia/util/*.java src/sistemacadastroacademia/main/*.java

if [ $? -ne 0 ]; then
  echo "Erro na compilação. Corrija os erros acima."
  exit 1
fi

echo "Executando o sistema..."
java -cp "lib/*:out" sistemacadastroacademia.main.App
