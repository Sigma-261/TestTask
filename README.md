# TestTask
Реализовал те функции, которые прописаны в ТЗ, то есть avg, max и values. Старался придерживаться требование. Использовал библиотеку Gson для парсинга json файла. Также, использовал библиотеку Stream для работы со списком объектов, так как данная библиотека позволяет быстро и просто взаимодействовать со списками. 

Для того, чтобы проверить работу программы нужно ввести в консоли данную строку:
```
java -jar TestTaskProj-1.0-SNAPSHOT-jar-with-dependencies.jar values testData.json
```
Вместо values может быть avg или max. Вместо имени файла может быть абсолютный путь до этого файла.

Пример выполнения программы:
```
The function for finding unique value [host] is called
values: [192.168.11.9, 192.168.10.8]
```
Вывод: Познакомился с библиотекой для парсинга json файлов. Познакомился с билдером maven и как создавать jar и fat jar. Узнал, как просто можно взаимодействовать с листами без циклов.