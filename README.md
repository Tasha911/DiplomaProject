Для запуска тестов требуется установить:
- Java 22
- Docker compose

Чтобы запустить тесты, нужно выполнить следующие команды:
- для режима mysql ```./gradlew runTests -Pdb.url=jdbc:mysql://localhost:3306/app?allowMultiQueries=true```
- для режима postgresql ```./gradlew runTests -Pdb.url=jdbc:postgresql://localhost:5432/app```

Для генерации отчета нужно выполнить следующую команду: ```./gradlew allureServe```
