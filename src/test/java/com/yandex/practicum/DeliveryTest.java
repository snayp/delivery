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
        assertEquals(400, Delivery.calculateCost(1, "маленькие", false, "низкая")); // 50 + 100 = 150 → 400 (мин.)
        assertEquals(200, Delivery.calculateCost(5, "маленькие", false, "низкая")); // 100 + 100 = 200 → 400 (мин.)
        assertEquals(300, Delivery.calculateCost(15, "маленькие", false, "низкая")); // 200 + 100 = 300 → 400 (мин.)
        assertEquals(500, Delivery.calculateCost(50, "маленькие", false, "низкая")); // 300 + 100 = 400 → 400 (мин.)
    }

    @Test
    public void testcalculateCost_DimensionsBased() {
        // Проверка стоимости в зависимости от габаритов
        assertEquals(400, Delivery.calculateCost(5, "маленькие", false, "низкая")); // 100 + 100 = 200 → 400 (мин.)
        assertEquals(400, Delivery.calculateCost(5, "большие", false, "низкая")); // 100 + 200 = 300 → 400 (мин.)
    }

    @Test
    public void testcalculateCost_FragileCargo() {
        // Проверка стоимости хрупкого груза
        assertEquals(700, Delivery.calculateCost(5, "маленькие", true, "низкая")); // 100 + 100 + 300 = 500 → 500
        assertEquals(900, Delivery.calculateCost(15, "большие", true, "низкая")); // 200 + 200 + 300 = 700 → 700

        // Проверка исключения при доставке хрупкого груза >30 км
        assertThrows(IllegalArgumentException.class, () -> {
            Delivery.calculateCost(35, "маленькие", true, "низкая");
        });
    }

    @Test
    public void testcalculateCost_WorkloadCoefficient() {
        // Проверка коэффициента загруженности
        assertEquals(240, Delivery.calculateCost(5, "маленькие", false, "повышенная")); // (100 + 100) * 1.2 = 240 → 400 (мин.)
        assertEquals(560, Delivery.calculateCost(15, "маленькие", true, "высокая")); // (200 + 100 + 300) * 1.4 = 840 → 840
        assertEquals(960, Delivery.calculateCost(50, "большие", false, "очень высокая")); // (300 + 200) * 1.6 = 800 → 800
    }

    @Test
    public void testcalculateCost_MinimumCost() {
        // Проверка минимальной стоимости (400 руб.)
        assertEquals(400, Delivery.calculateCost(1, "маленькие", false, "низкая")); // 50 + 100 = 150 → 400
        assertEquals(400, Delivery.calculateCost(5, "маленькие", false, "повышенная")); // (100 + 100) * 1.2 = 240 → 400
    }
}