1. NinB (NinB is not Bash) реализует основную логику: инициализация `Environment`, компонент (`Reader`, …, `Executor`) и сам цикл.
  Цикл выглядит примерно так:
  ```
  rawStmt = reader.read();
  quotedStmt = quoteParser.parse(rawStmt);
  lambdaStmt = controlParser.parse(quotedStmt);
  stmt = substitutor.substitute(lambdaStmt);
  executable = commandParser.parse(stmt);
  resultCode = executor.execute(executable);

  if (resultCode…) { … }
  ```

 По сути, тело цикла отражает флоу данных через компоненты (см. Data Flow Diagram).


2. `Environment` хранит словарь переменная-значение. В нашей постановке значение может быть только строкой. `Environment` должен быть доступен для `Substitutor` и `Assignment`.

3. `Reader` считывает пользовательский ввод, выдаёт `RawStmt`.

4. `RawStmt` — просто строка.

5. `QuoteParser` сканирует строку на наличие символов [' - fullQuoting | " - weakQuoting].
Результатом будет `QuotedStmt` -- набор объектов, состоящих из строки и степени её закавыченности (степень закавыченности определяется классом: `RawString`, `WeakQuotedString`, `FullQuotedString`).

6. `ControlParser` сканирует строку на наличие символов ['|' | $ | =], разбивая её по '|', выделяя подстроки для подстановки по '$' и заменяя вхождения '=' объектом `AssignmentOp`.

Результатом будет массив [ `LambdaStmt` ]

7. `LambdaStmt`  — массив подстрок и информация о возможных подстановках — словарь (имя переменной -> индекс элемента массива `lambdaStmt.parts` с её вхождением, индекс первого символа, индекс последнего символа + 1), причём одному ключу может соответствовать множество значений. `LambdaStmt` соответствует одной инструкции (между пайпами).

Есть методы для самой подстановки. Пайплайн работы с `LambdaStmt`: получить набор ключей, найти соответствующие значения через `Environment` (если определённой переменной нет, подставляется пустая строка), передать этот подсловарь в `substitute(...)`, после чего получить набор подстрок с выполненными подстановками через `getParts()`.

8. `Substitutor` — через интерфейс `LambdaStmt` производит подстановку, конвертирует `LambdaStmt` в `Stmt`.

9. `Stmt` — массив подстрок, некоторые из которых могут быть помечены как закавыченные. Закавыченные строки не требуется разбирать `CommandParser`-у.

10. `CommandParser` — разбирает строку, учитывая пробельные символы. Конвертирует `Stmt` в `Executable`, выделяя исполняемую команду и набор агрументов по следующему принципу: если `Stmt` содержит `AssignmentOperator`, порождает `AssignmentCmd`, иначе рассматривает первый терм и, если он соответствует одной из внутренних команд (т.е. занесён в `CmdRegistry`), порождает `BuiltInCmd`, иначе `ExternalCmd`.

11. `Executable` — исполняемая команда и набор аргументов. Если команда читает из stdin, последний аргумент трактуется как пользовательский ввод

12. `Binary` -- корень дерева иерархии исполняемых команд. Каждую команду можно исполнить с набором аргументов через `execute(args)`.

Для однородности определение переменной (x=3) тоже рассматривается как команда, принимающая два аргумента: название переменной и её значение -- и обновляющая `Environment`.

Кроме того, имеется набор встроенных команд (cat, echo, ...). Для добавления новой команды достаточно добавить новый класс и зарегистрировать его в `CmdRegistry`.

Наконец, есть возможность вызвать сторонний бинарник -- `ExternalCmd`

13. `Executor` — исполняет `Executable`, отдаёт `resultCode`, который обрабатывается в цикле NinB.

При исполнении в цикле прогоняет каждое `Executable`, складывая и передавая промежуточные результаты через buffer, отслеживая resultCode.

Если resultCode.exit, можно выходить, иначе можно обработать код `Statement` и пойти в следующую итерацию.

То, что после выполнения [`Executable`] осталось в buffer, является результатом работы.






**Пример:** Предположим, что в окружении есть переменная со значением xy=123. Пусть пользователь ввел строку
 a=3 | echo “$xy” ‘ab’ cd | pwd
Тогда будем иметь следующую последовательность разбора
RawStmt: a=3 | echo “$xy” ‘ab’ cd | pwd

- `QuoteParser`(a=3 | echo “$xy” ‘ab’ cd | pwd) --->
  `QuotedStmt`: [
    RawString(a=3 | echo ),
    WeakQuotedString($xy),
    RawString( ),
    FullQuotedString(ab),
    RawString( cd | pwd)
  ]

Теперь напишем преобразование в LambdaStmt:
[RawString(a=3 | echo ),
WeakQuotedString($xy),
RawString( ),
FullQuotedString(ab),
RawString( cd | pwd)]
—>
[
LambdaStmt([
        RawString(a),
        AssignmentOp(),
        RawString(3 )
]),
LambdaStmt([
        RawString( echo ),
        WeakQuotedString($xy),
        RawString( ),
        FullQuotedString(ab),
        RawString( cd )
]),
LambdaStmt([
        RawString( pwd)
])
]

Поле-словарь только во втором LambdaStmt будет не пуст, так как только в нем есть вхождение $ (т.е. подстановка), при этом
dict[‘xy’] = MultiMap{(1, 0, 3)}
 Почему? Подстановка есть только во втором элементе массива (в WeakQuotedString($xy)), соответственно, 1 на первой позиции в словарном значении соответствует номеру элемента массива (нумерация с нуля), в который нужно подставить значение переменной xy. Рассмотрим строку $xy. Замещение произойдет с 0-ой (символ $) по 2-ю (символ y) позиции, а храним мы как раз начало и конец+1, т.е. 0 и 3

Применяя Substitutor, внутри второго LambdaStmt произойдет замена
WeakQuotedString($xy) -> WeakQuotedString(123)

Остальные LambdaStmt не меняются, так как в них отсутствует подстановка

Отдавая все на вход CommandParser-у, получим:
Stmt([
        RawString(a),
        AssignmentOp(),
        RawString(3 )
]) —> Executable(binary: AssignmentCmd, args: [“3”]),
Stmt([
        RawString( echo ),
        WeakQuotedString(12),
        RawString( ),
        FullQuotedString(ab),
        RawString( cd )
]) -> Executable(binary: Echo, args: [“12”, “ab”, “cd”]),
Stmt([
        RawString( pwd)
]) -> Pwd()
