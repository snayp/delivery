package com.yandex.practicum;

public class Delivery {

    public static int calculateCost(int distance, String dimensions, boolean isFragile, String workload) {
        // Проверка на невозможность доставки хрупкого груза
        if (isFragile && distance > 30) {
            throw new IllegalArgumentException("Хрупкие грузы нельзя возить на расстояние более 30 км");
        }
        // Валидация расстояния
        if (distance <= 0) {
            throw new IllegalArgumentException("Расстояние должно быть положительным числом");
        }

        // Валидация габаритов
        if (!dimensions.equalsIgnoreCase("маленькие") && !dimensions.equalsIgnoreCase("большие")) {
            throw new IllegalArgumentException("Недопустимые габариты груза");
        }

        int cost = 0;

        // Расчет стоимости по расстоянию
        if (distance > 30) {
            cost += 300;
        } else if (distance > 10) {
            cost += 200;
        } else if (distance > 2) {
            cost += 100;
        } else {
            cost += 50;
        }

        // Расчет стоимости по габаритам
        if (dimensions.equalsIgnoreCase("большие")) {
            cost += 200;
        } else if (dimensions.equalsIgnoreCase("маленькие")) {
            cost += 100;
        }

        // Добавление стоимости за хрупкость
        if (isFragile) {
            cost += 300;
        }

        // Применение коэффициента загруженности
        double workloadCoefficient = 1.0;
        switch (workload.toLowerCase()) {
            case "очень высокая":
                workloadCoefficient = 1.6;
                break;
            case "высокая":
                workloadCoefficient = 1.4;
                break;
            case "повышенная":
                workloadCoefficient = 1.2;
                break;
        }

        cost = (int) Math.ceil(cost * workloadCoefficient);

        // Проверка на минимальную стоимость
        if (cost < 400) {
            cost = 400;
        }

        return cost;
    }
}
