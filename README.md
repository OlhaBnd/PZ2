# Практична робота 2

### Ідея алгоритму:

Написана програма виконує обчислення добутків пар елементів з масиву чисел, використовуючи паралельне виконання на кількох потоках. Це дозволяє прискорити обчислення за рахунок використання можливостей процесорів в багатопотоках.

Програма складається з двох основних частин:

1. **Основний клас `Main`**: генерує масив чисел, розбиває його на частини, передає ці частини в окремі потоки для обчислення добутків, а після завершення роботи потоків виводить результат.
2. **Клас обчислювача добутку пар `PairwiseCalculate`**: реалізує логіку обчислення добутків пар елементів масиву.

**Використання класу `Future`**: У коді для відстежування та керування потоками(`Callable`) використовуються масиви ф'ючерів.

```java
List<Future<ArrayList<Integer>>> futures = new ArrayList<>();

for (PairwiseCalculate pairwiseCalculate : pairwiseCalculateList) {
    futures.add(es.submit(pairwiseCalculate));
}
```

**Створення та ділення вхідних масивів**: Для легшого та доцільнішого підзоду були реалізовані методи для генерації та ділення масивів.
```java
public static ArrayList<Integer> generateArray();

public static ArrayList<ArrayList<Integer>> split(ArrayList<Integer> inputArr, int partsAmount);
```
**Відмітки часу записую мілісекундами у змінних**
```java
// Запуск таймер для відрахунку часу
long startTimer = System.currentTimeMillis();

// Зупинка таймеру після очікування усіх Futures
long endTimer = System.currentTimeMillis();
double elapsedTime = (endTimer - startTimer) / 1000.0;
```

> При не парних розмірах масивів виклик `PairwiseCalculate` відкидає останній елемент в масиві і не записує його