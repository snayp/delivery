package com.yandex.practicum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeliveryTest {
    @Test
    void testCalculateCost() {

    }

    @Test
    public void testcalculateCost_DistanceBased() {
        // Проверка стоимости в зависимости от расстояния (без учета хрупкости и загруженности)
        assertEquals(450, Delivery.calculateCost(1, "маленькие", true, "низкая")); // 50 + 100 + 300 = 450
        assertEquals(500, Delivery.calculateCost(5, "маленькие", true, "низкая")); // 100 + 100 + 300 = 500
        assertEquals(600, Delivery.calculateCost(15, "маленькие", true   , "низкая")); // 200 + 100 + 300 = 600
        assertEquals(400, Delivery.calculateCost(50, "маленькие", false, "низкая")); // 300 + 100 = 400
    }

    @Test
    public void testcalculateCost_DimensionsBased() {
        // Проверка стоимости в зависимости от габаритов
        assertEquals(500, Delivery.calculateCost(5, "маленькие", true, "низкая")); // 100 + 100 + 300 = 500
        assertEquals(600, Delivery.calculateCost(5, "большие", true, "низкая")); // 100 + 200 + 300 = 600
    }

    @Test
    public void testcalculateCost_FragileCargo() {
        // Проверка стоимости хрупкого груза
        assertEquals(400, Delivery.calculateCost(25, "большие", false, "низкая")); // 200 + 200 = 400
        assertEquals(700, Delivery.calculateCost(25, "большие", true, "низкая")); // 200 + 200 + 300 = 700

        // Проверка исключения при доставке хрупкого груза >30 км
        assertThrows(IllegalArgumentException.class, () -> {
            Delivery.calculateCost(35, "маленькие", true, "низкая");
        });
    }

    @Test
    public void testcalculateCost_WorkloadCoefficient() {
        // Проверка коэффициента загруженности
        assertEquals(720, Delivery.calculateCost(15, "маленькие", true, "повышенная")); // (200 + 100 + 300) * 1.2 = 720
        assertEquals(840, Delivery.calculateCost(15, "маленькие", true, "высокая")); // (200 + 100 + 300) * 1.4 = 840 → 840
        assertEquals(960, Delivery.calculateCost(15, "маленькие", true, "очень высокая")); // (200 + 100 + 300) * 1.6 = 960 → 800
    }

    @Test
    public void testcalculateCost_MinimumCost() {
        // Проверка минимальной стоимости (400 руб.)
        assertEquals(400, Delivery.calculateCost(1, "маленькие", false, "низкая")); // 50 + 100 = 150 → 400
        assertEquals(400, Delivery.calculateCost(5, "маленькие", false, "повышенная")); // (100 + 100) * 1.2 = 240 → 400
    }

        @Test
    public void testCalculateDeliveryCost_InvalidDistance() {
        // Отрицательное расстояние → IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Delivery.calculateCost(-5, "маленькие", false, "низкая");
        });

        // Нулевое расстояние → IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Delivery.calculateCost(0, "маленькие", false, "низкая");
        });
    }

    @Test
    public void testCalculateDeliveryCost_InvalidDimensions() {
        // Неизвестные габариты → IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Delivery.calculateCost(10, "огромные", false, "низкая");
        });
    }

    @Test
    public void testCalculateDeliveryCost_InvalidWorkload() {
        // Неизвестная загруженность → используется коэффициент 1.0 (по умолчанию)
        assertEquals(400, Delivery.calculateCost(31, "маленькие", false, "неизвестная")); // 300 + 100 = 400
    }
}