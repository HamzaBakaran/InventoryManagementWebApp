package ba.edu.ibu.inventorymanagementwebapp.rest.controller;

import ba.edu.ibu.inventorymanagementwebapp.core.model.ProductQuantityChange;
import ba.edu.ibu.inventorymanagementwebapp.core.service.ProductService;
import ba.edu.ibu.inventorymanagementwebapp.core.service.ProductQuantityChangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ProductService productService;
    private final ProductQuantityChangeService productQuantityChangeService;

    public ReportController(ProductService productService, ProductQuantityChangeService productQuantityChangeService) {
        this.productService = productService;
        this.productQuantityChangeService = productQuantityChangeService;
    }

    @GetMapping("/sales")
    public ResponseEntity<List<ProductQuantityChange>> getSalesReport(
            @RequestParam Long categoryId,
            @RequestParam(required = false) String date) {
        // Parse the date parameter
        LocalDate reportDate = (date != null) ? LocalDate.parse(date) : LocalDate.now();
        LocalDateTime startOfDay = reportDate.atStartOfDay();
        LocalDateTime endOfDay = reportDate.atTime(LocalTime.MAX);

        // Fetch sales report for the category and date range
        List<ProductQuantityChange> report = productService.getSalesReport(categoryId, startOfDay, endOfDay);

        return ResponseEntity.ok(report);
    }

    @GetMapping("/sales/summary")
    public ResponseEntity<List<Object[]>> getSalesSummaryByCategory() {
        // Fetch the total items sold grouped by category
        List<Object[]> summary = productQuantityChangeService.getTotalItemsSoldByCategory();

        return ResponseEntity.ok(summary);
    }
}
